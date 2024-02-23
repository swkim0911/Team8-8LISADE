package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.request.LoginRequest
import com.softeer.togeduck.data.remote.service.LoginService
import retrofit2.Response
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginService: LoginService
) {
    suspend fun login(
        loginRequest: LoginRequest
    ): Response<String> {
        return loginService.login(loginRequest)
    }
}