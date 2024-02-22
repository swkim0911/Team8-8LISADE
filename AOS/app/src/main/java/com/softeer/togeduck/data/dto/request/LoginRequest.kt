package com.softeer.togeduck.data.dto.request

import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name = "userId")
    val userId: String,
    @Json(name = "password")
    val password: String,
)