package com.softeer.togeduck.data.dto.response.home.seat


data class SeatsInfoResponse(
    val totalSeats: Int,
    val row: Int,
    val col: Int,
    val backSeats: Int,
    val reservedSeats: Int,
    val sourceCity: String,
    val source: String,
    val destinationCity: String,
    val destination: String,
    val price: Int,
    val seats: List<SeatStatusResponse>,
)

data class SeatStatusResponse(
    val id: Int,
    val seatNo: Int,
    val status: SeatStatus,
)

enum class SeatStatus(val isAvailable: Boolean) {
    AVAILABLE(true), RESERVATION(false)
}