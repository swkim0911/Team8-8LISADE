package com.softeer.togeduck.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        // 세션 ID를 저장할 때 사용할 Preferences Key
        private val USER_SESSION_ID = stringPreferencesKey("USER_SESSION_ID")
    }

    // 세션 ID를 저장하는 함수
    suspend fun storeUser(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[USER_SESSION_ID] = sessionId
        }
    }
}
