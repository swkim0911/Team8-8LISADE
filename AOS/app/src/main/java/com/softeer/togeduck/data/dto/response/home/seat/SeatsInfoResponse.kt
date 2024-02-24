package com.softeer.togeduck.data.dto.response.home.seat

import com.squareup.moshi.Json

data class SeatsInfoResponse(
    @Json(name = "numberOfSeats")
    val numberOfSeats: Int,
    @Json(name = "row")
    val row: Int,
    @Json(name = "column")
    val column: Int,
    @Json(name = "backSeat")
    val backSeat: Int,
    @Json(name = "totalSeats")
    val totalSeats: Int,
    @Json(name = "reservedSeats")
    val reservedSeats: Int,
    @Json(name = "sourceCity")
    val sourceCity: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "destinationCity")
    val destinationCity: String,
    @Json(name = "destination")
    val destination: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "seats")
    val seats: List<SeatStatusResponse>,
)

data class SeatStatusResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "seatNo")
    val seatNo: String,
    @Json(name = "status")
    val status: String,
)