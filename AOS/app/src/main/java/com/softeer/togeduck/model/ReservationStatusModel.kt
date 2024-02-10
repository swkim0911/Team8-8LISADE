package com.softeer.togeduck.model

data class ReservationStatusModel(
    val festivalName: String,
    val festivalImg: String,
    val date: String,
    val departureTime: String,
    val place: String,
    val cost: Int,
    val formattedCost: String,
    val recruitStatus: Boolean,
    val recruitPhrase: String,
)