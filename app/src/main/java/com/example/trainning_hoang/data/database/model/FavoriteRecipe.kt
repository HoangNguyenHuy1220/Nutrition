package com.example.trainning_hoang.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val result: Result
)
