package com.example.trainning_hoang.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: @RawValue List<Ingredient>,
    val glutenFree: Boolean,
    val image: String?,
    val readyInMinutes: Int,
    val sourceUrl: String?,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean
): Parcelable