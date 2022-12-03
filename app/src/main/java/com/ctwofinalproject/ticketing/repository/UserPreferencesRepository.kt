package com.ctwofinalproject.ticketing.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ctwofinalproject.ticketing.UserDataProto
import com.ctwofinalproject.ticketing.proto.UserPreferencesSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

private val Context.userPreferencesStore: DataStore<UserDataProto> by dataStore(
    fileName = "userData",
    serializer = UserPreferencesSerializer
)

class UserPreferencesRepository (private val context: Context) {

    suspend fun saveData(firstname: String,lastname : String,gender : String,email : String,phone : String,birthdate : String,pictures : String,token : String, isLogin: Boolean) {
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setFirstname(firstname).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setLastname(lastname).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setGender(gender).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setEmail(email).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setPhone(phone).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setPictures(pictures).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setBirthdate(birthdate).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setToken(token).build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setIsLogin(isLogin).build()
        }
    }

    suspend fun deleteData() {
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearFirstname().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearLastname().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearGender().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearEmail().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearPhone().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearPictures().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearBirthdate().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearToken().build()
        }
        context.userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().clearIsLogin().build()
        }
    }

    val readProto: Flow<UserDataProto> = context.userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("tag", "Error reading sort order preferences.", exception)
                emit(UserDataProto.getDefaultInstance())
            } else {
                throw exception
            }
        }

}