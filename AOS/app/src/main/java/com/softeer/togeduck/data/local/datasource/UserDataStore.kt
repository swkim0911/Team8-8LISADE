package com.softeer.togeduck.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")

@Singleton
class UserDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        // 세션 ID를 저장할 때 사용할 Preferences Key
        private val USER_SESSION_ID = stringPreferencesKey("user_session_id")
    }

    // 세션 ID를 저장하는 함수
    suspend fun storeUser(sessionId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SESSION_ID] = sessionId
        }
    }

    val getUserSessionId: Flow<String?> = context.dataStore.data.map { sessionId ->
        sessionId[USER_SESSION_ID]
    }
}
