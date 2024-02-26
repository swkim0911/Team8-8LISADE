package com.softeer.togeduck.data.dto.response.article_detail

data class RouteDetailResponse(
    val arrivalAt: String,
    val cost: Int,
    val destination: String,
    val expectedAt: String,
    val id: Int,
    val reservedSeats: Int,
    val source: String,
    val startedAt: String,
    val totalSeats: Int
)