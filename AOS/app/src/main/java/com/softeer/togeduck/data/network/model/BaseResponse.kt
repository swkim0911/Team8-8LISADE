package com.softeer.togeduck.data.network.model

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val result: T
)