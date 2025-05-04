package com.example.petcare_app.datastore

import androidx.datastore.preferences.core.stringPreferencesKey
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore private constructor(private val context: Context) {
    private val Context.dataStore by preferencesDataStore("user_prefs")
    private val TOKEN_KEY = stringPreferencesKey("user_token")
    private val NAME_USER = stringPreferencesKey("user_name")
    private val ID_USER = intPreferencesKey("user_id")

    // Singleton instance
    companion object {
        @Volatile
        private var INSTANCE: TokenDataStore? = null

        fun getInstance(context: Context): TokenDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenDataStore(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun saveUserInfo(token: String, name: String, id: Int) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[NAME_USER] = name
            prefs[ID_USER] = id
        }
    }

    suspend fun clearUserInfo() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(NAME_USER)
        }
    }

    val getToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    val getName: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[NAME_USER] }

    val getId: Flow<Int?> = context.dataStore.data
        .map { prefs -> prefs[ID_USER] }
}

