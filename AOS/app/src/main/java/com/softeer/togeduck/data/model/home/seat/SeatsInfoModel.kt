package com.softeer.togeduck.data.model.home.seat

data class SeatsInfoModel(
    val seatsCntPerRow: Int,
    val totalRows: Int,
    val backSeatsCnt: Int,
    val totalSeats: Int,
    val reservedSeats: Int,
    val departurePlace: String,
    val arrivalPlace: String,
    val price: Int,
    val seats: List<SeatStatusModel>,
)

data class SeatStatusModel(
    val id: Int,
    val seatNo: String,
    val status: String,
)