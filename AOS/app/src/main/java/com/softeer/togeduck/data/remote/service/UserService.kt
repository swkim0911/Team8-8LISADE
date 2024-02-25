package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.request.FcmTokenRequest
import com.softeer.togeduck.data.dto.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("users/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<String>

    @POST("fcm")
    suspend fun sendFcmToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): Response<String>
}