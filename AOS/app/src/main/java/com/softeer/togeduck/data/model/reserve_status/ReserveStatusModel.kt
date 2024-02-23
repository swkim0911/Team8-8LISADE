package com.softeer.togeduck.data.model.reserve_status

data class ReserveStatusModel(
    val id: Int,
    val festivalName: String,
    val festivalImg: String,
    val departureTime: String,
    val departurePlace: String,
    val festivalPlace: String,
    val cost: Int,
    val formattedCost: String,
    val recruitStatus: Boolean,
    val recruitPhrase: String,
)