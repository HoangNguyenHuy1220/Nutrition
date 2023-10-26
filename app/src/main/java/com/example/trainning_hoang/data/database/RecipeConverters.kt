package com.example.trainning_hoang.data.database

import androidx.room.TypeConverter
import com.example.trainning_hoang.data.database.model.Ingredient
import com.example.trainning_hoang.data.database.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeConverters {

    @TypeConverter
    fun toString(dataList: List<String>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun fromString(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(dataList: List<Ingredient>): String {
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun stringToDataList(data: String): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return Gson().toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return Gson().fromJson(data, listType)
    }
}