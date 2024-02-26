package com.softeer.togeduck.data.model.home.article_detail

data class RouteDetailModel(
    val arrivalAt: String,
    val cost: String,
    val destination: String,
    val expectedAt: String,
    val id: Int,
    val reservedSeats: Int,
    val source: String,
    val startedAt: String,
    val totalSeats: Int
)
