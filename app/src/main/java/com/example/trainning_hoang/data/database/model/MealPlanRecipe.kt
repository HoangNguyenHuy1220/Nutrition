package com.example.trainning_hoang.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MealPlanRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val result: Result,
    val planDays: List<String>
)
