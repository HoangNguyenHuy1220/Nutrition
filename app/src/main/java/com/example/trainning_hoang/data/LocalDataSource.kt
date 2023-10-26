package com.example.trainning_hoang.data

import com.example.trainning_hoang.data.database.RecipeDao
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.data.database.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {
    /** Catching */
    suspend fun insertRecipes(recipes: List<Result>) = withContext(Dispatchers.IO) {
        recipeDao.insertRecipes(recipes)
    }

    fun getLocalRecipes() = recipeDao.getLocalRecipes()

    /** Save favorite recipes */
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) =
        recipeDao.insertFavoriteRecipe(favoriteRecipe)

    suspend fun insertRecipeList(recipeList: List<FavoriteRecipe>) =
        recipeDao.insertRecipeList(recipeList)

    suspend fun deleteFavoriteRecipe(favoriteRecipe: FavoriteRecipe) =
        recipeDao.deleteFavoriteRecipe(favoriteRecipe)

    fun getFavoriteRecipes() = recipeDao.getFavoriteRecipes()

    /** Save meal plan recipes */
    suspend fun insertMealPlanRecipe(mealPlanRecipes: List<MealPlanRecipe>) =
        recipeDao.insertMealPlanRecipes(mealPlanRecipes)

    suspend fun deleteMealPlanRecipe(mealPlanRecipe: MealPlanRecipe) =
        recipeDao.deleteMealPlanRecipe(mealPlanRecipe)

    fun getMealPlanRecipes() = recipeDao.getMealPlanRecipes()
}