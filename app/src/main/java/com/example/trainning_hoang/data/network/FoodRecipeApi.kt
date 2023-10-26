package com.example.trainning_hoang.data.network

import com.example.trainning_hoang.data.database.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipeApi {
    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun querySearch(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>
}