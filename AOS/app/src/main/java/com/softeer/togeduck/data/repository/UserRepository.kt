package com.softeer.togeduck.data.repository

import com.softeer.myapplication.data.network.Retrofit
import com.softeer.togeduck.data.remote.service.LoginService
import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.request.LoginRequest
import retrofit2.Response

class UserRepository {
    private val retrofit = Retrofit.getInstance().create(LoginService::class.java)

    suspend fun login(loginRequest: LoginRequest): Response<BaseResponse<String>> {
        return retrofit.login(loginRequest)
    }
}