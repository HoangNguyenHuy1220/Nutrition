package com.example.trainning_hoang.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.trainning_hoang.R
import com.example.trainning_hoang.data.Repository
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.data.database.model.FoodRecipe
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.data.database.model.Result
import com.example.trainning_hoang.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** RETROFIT */
    private val _recipeResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val recipeResponse: LiveData<NetworkResult<FoodRecipe>>
        get() = _recipeResponse

    private val _searchResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val searchRespone: LiveData<NetworkResult<FoodRecipe>>
        get() = _searchResponse

    /** DATABASE */
    private val _localRecipes = repository.local.getLocalRecipes().asLiveData()
    val localRecipes: LiveData<List<Result>>
        get() = _localRecipes

    private val _favoriteRecipes = repository.local.getFavoriteRecipes().asLiveData()
    val favoriteRecipes: LiveData<List<FavoriteRecipe>>
        get() = _favoriteRecipes

    private val _mealPlanRecipes = repository.local.getMealPlanRecipes().asLiveData()
    val mealPlanRecipes: LiveData<List<MealPlanRecipe>>
        get() = _mealPlanRecipes

    /** Home recipes */
    fun getRecipes(queries: Map<String, String>) {
        _recipeResponse.value = NetworkResult.Loading()
        viewModelScope.launch {
            if (hasInternetConnection()) {
                val response = repository.remote.getRecipes(queries)
                _recipeResponse.value = handleRecipeResponse(response)
                val recipeList = _recipeResponse.value!!.data?.results
                if (!recipeList.isNullOrEmpty()) {
                    repository.local.insertRecipes(recipeList)
                }
            } else {
                _recipeResponse.value = NetworkResult.Error(
                    getApplication<Application>().getString(
                        R.string.no_internet_connection
                    )
                )
            }
        }
    }

    private fun handleRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body())
        } else {
            NetworkResult.Error(response.message())
        }
    }

    fun searchRecipes(queries: Map<String, String>) {
        _searchResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            viewModelScope.launch {
                val response = repository.remote.querySearch(queries)
                _searchResponse.value = handleSearchRecipeResponse(response)
            }
        } else {
            _searchResponse.value = NetworkResult.Error(
                getApplication<Application>().getString(
                    R.string.no_internet_connection
                )
            )
        }
    }

    private fun handleSearchRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return if (response.isSuccessful) {
            NetworkResult.Success(response.body())
        } else {
            NetworkResult.Error(response.message())
        }
    }

    /** Favorite recipes */
    fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipe(favoriteRecipe)
        }

    fun insertRecipeList(recipeList: List<FavoriteRecipe>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipeList(recipeList)
        }

    fun deleteFavoriteRecipe(favoriteRecipe: FavoriteRecipe) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoriteRecipe)
        }

    /** Meal plan recipes */
    fun insertMealPlanRecipes(mealPlanRecipes: List<MealPlanRecipe>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMealPlanRecipe(mealPlanRecipes)
        }

    fun deleteMealPlanRecipe(mealPlanRecipe: MealPlanRecipe) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteMealPlanRecipe(mealPlanRecipe)
        }

    /** Handle network connection */
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}