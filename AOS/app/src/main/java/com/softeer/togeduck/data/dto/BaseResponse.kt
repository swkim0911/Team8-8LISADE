package com.softeer.togeduck.data.dto

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val result: T
)