package com.example.trainning_hoang.data.database

import androidx.room.*
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.data.database.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeList(recipeList: List<FavoriteRecipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlanRecipes(recipes: List<MealPlanRecipe>)

    @Query("SELECT * FROM Result")
    fun getLocalRecipes(): Flow<List<Result>>

    @Query("SELECT * FROM FavoriteRecipe")
    fun getFavoriteRecipes(): Flow<List<FavoriteRecipe>>

    @Query("SELECT * FROM MealPlanRecipe")
    fun getMealPlanRecipes(): Flow<List<MealPlanRecipe>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteRecipe: FavoriteRecipe)

    @Delete
    suspend fun deleteMealPlanRecipe(mealPlanRecipe: MealPlanRecipe)
}