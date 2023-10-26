package com.example.trainning_hoang.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val amount: Double,
    val consistency: String?,
    val image: String?,
    val name: String,
    val original: String,
    val unit: String
): Parcelable