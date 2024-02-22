package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.request.LoginRequest
import com.softeer.togeduck.data.local.datasource.UserDataStore
import com.softeer.togeduck.data.remote.datasource.LoginRemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val userDataStore: UserDataStore
) {
    suspend fun login(loginRequest: LoginRequest): Response<BaseResponse<String>> {
        return loginRemoteDataSource.login(loginRequest)
    }

    suspend fun storeUser(sessionId: String) {
        userDataStore.storeUser(sessionId)
    }

    fun getUserSessionId(): Flow<String?> {
        return userDataStore.getUserSessionId
    }

}