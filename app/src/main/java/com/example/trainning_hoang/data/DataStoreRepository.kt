package com.example.trainning_hoang.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.trainning_hoang.util.Constant.DEFAULT_DIET_TYPE
import com.example.trainning_hoang.util.Constant.DEFAULT_MEAL_TYPE
import com.example.trainning_hoang.util.Constant.DIET_TYPE_KEY_PREFERENCE
import com.example.trainning_hoang.util.Constant.DIET_TYPE_PREFERENCE
import com.example.trainning_hoang.util.Constant.MEAL_TYPE_KEY_PREFERENCE
import com.example.trainning_hoang.util.Constant.MEAL_TYPE_PREFERENCE
import com.example.trainning_hoang.util.Constant.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object PreferenceKey {
        val selectedMealType = stringPreferencesKey(MEAL_TYPE_PREFERENCE)
        val selectedMealTypeKey = intPreferencesKey(MEAL_TYPE_KEY_PREFERENCE)
        val selectedDietType = stringPreferencesKey(DIET_TYPE_PREFERENCE)
        val selectedDietTypeKey = intPreferencesKey(DIET_TYPE_KEY_PREFERENCE)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
         context.dataStore.edit { preferences ->
            preferences[PreferenceKey.selectedMealType] = mealType
            preferences[PreferenceKey.selectedMealTypeKey] = mealTypeId
            preferences[PreferenceKey.selectedDietType] = dietType
            preferences[PreferenceKey.selectedDietTypeKey] = dietTypeId
        }
    }

    fun readMealAndDietType() = context.dataStore.data.map { preferences ->
        MealAndDietType(
            preferences[PreferenceKey.selectedMealType] ?: DEFAULT_MEAL_TYPE,
            preferences[PreferenceKey.selectedMealTypeKey] ?: 0,
            preferences[PreferenceKey.selectedDietType] ?: DEFAULT_DIET_TYPE,
            preferences[PreferenceKey.selectedDietTypeKey] ?: 0
        )
    }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeKey: Int,
    val selectedDietType: String,
    val selectedDietTypeKey: Int
)