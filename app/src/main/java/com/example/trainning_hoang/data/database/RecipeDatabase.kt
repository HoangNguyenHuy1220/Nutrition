package com.example.trainning_hoang.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainning_hoang.data.database.model.FavoriteRecipe
import com.example.trainning_hoang.data.database.model.MealPlanRecipe
import com.example.trainning_hoang.data.database.model.Result

@Database(
    entities = [
        Result::class,
        FavoriteRecipe::class,
        MealPlanRecipe::class
    ], version = 1, exportSchema = false
)
@TypeConverters(RecipeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}