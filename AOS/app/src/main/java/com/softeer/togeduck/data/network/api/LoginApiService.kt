package com.softeer.togeduck.data.network.api

import com.softeer.togeduck.data.network.model.BaseResponse
import com.softeer.togeduck.data.network.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): BaseResponse<String>
}