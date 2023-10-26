package com.example.trainning_hoang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainning_hoang.data.DataStoreRepository
import com.example.trainning_hoang.util.Constant.API_KEY
import com.example.trainning_hoang.util.Constant.DEFAULT_DIET_TYPE
import com.example.trainning_hoang.util.Constant.DEFAULT_MEAL_TYPE
import com.example.trainning_hoang.util.Constant.DEFAULT_RECIPES_NUMBER
import com.example.trainning_hoang.util.Constant.QUERY_ADD_RECIPE_INFORMATION
import com.example.trainning_hoang.util.Constant.QUERY_API_KEY
import com.example.trainning_hoang.util.Constant.QUERY_DIET
import com.example.trainning_hoang.util.Constant.QUERY_FILL_INGREDIENTS
import com.example.trainning_hoang.util.Constant.QUERY_NUMBER
import com.example.trainning_hoang.util.Constant.QUERY_SEARCH
import com.example.trainning_hoang.util.Constant.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    private val _mealAndDietType = dataStoreRepository.readMealAndDietType()
    val mealAndDietType
        get() = _mealAndDietType

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(meal: String?, diet: String?): Map<String, String> {
        if (meal == null || diet == null) {
            viewModelScope.launch {
                _mealAndDietType.collect {
                    mealType = it.selectedMealType
                    dietType = it.selectedDietType
                }
            }
        } else {
            mealType = meal
            dietType = diet
        }

        val queries = HashMap<String, String>()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQueries(searchQuery: String): Map<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}