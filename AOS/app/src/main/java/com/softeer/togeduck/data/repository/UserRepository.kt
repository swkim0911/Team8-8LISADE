package com.softeer.togeduck.data.repository

import com.softeer.myapplication.data.network.Retrofit
import com.softeer.togeduck.data.network.api.LoginApiService
import com.softeer.togeduck.data.network.model.LoginRequest

class UserRepository {
    private val retrofit = Retrofit.getInstance().create(LoginApiService::class.java)

    suspend fun login(loginRequest: LoginRequest) = retrofit.login(loginRequest)
}