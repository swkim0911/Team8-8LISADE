package com.softeer.togeduck.data.repository

import com.softeer.myapplication.data.network.Retrofit
import com.softeer.togeduck.data.network.api.LoginApiService
import com.softeer.togeduck.data.network.model.BaseResponse
import com.softeer.togeduck.data.network.model.LoginRequest
import retrofit2.Response

class UserRepository {
    private val retrofit = Retrofit.getInstance().create(LoginApiService::class.java)

    suspend fun login(loginRequest: LoginRequest): Response<BaseResponse<String>> {
        return retrofit.login(loginRequest)
    }
}