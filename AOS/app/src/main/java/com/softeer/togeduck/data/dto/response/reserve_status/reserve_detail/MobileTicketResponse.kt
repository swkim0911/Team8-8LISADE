package com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail

import com.squareup.moshi.Json

data class MobileTicketResponse(
    @Json(name = "title")
    val title: String,
    @Json(name = "startedAt")
    val startedAt: String,
    @Json(name = "sourceCity")
    val sourceCity: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "destinationCity")
    val destinationCity: String,
    @Json(name = "destination")
    val destination: String,
    @Json(name = "departureAt")
    val departureAt: String,
    @Json(name = "arrivalAt")
    val arrivalAt: String,
    @Json(name = "seatNo")
    val seatNo: String,
)
