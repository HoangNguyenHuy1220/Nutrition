package com.example.trainning_hoang.data

import com.example.trainning_hoang.data.network.FoodRecipeApi
import com.example.trainning_hoang.data.database.model.FoodRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        withContext(Dispatchers.IO) {
            foodRecipeApi.getRecipes(queries)
        }

    suspend fun querySearch(queries: Map<String, String>): Response<FoodRecipe> =
        withContext(Dispatchers.IO) {
            foodRecipeApi.querySearch(queries)
        }
}