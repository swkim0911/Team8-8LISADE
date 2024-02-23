package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.local.datasource.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FcmTokenRepository @Inject constructor (private val userDataStore: UserDataStore) {
    suspend fun storeFcmToken(fcmToken: String) {
        userDataStore.storeFcmToken(fcmToken)
    }

    fun getFcmToken(): Flow<String?> {
        return userDataStore.getFcmToken
    }
}