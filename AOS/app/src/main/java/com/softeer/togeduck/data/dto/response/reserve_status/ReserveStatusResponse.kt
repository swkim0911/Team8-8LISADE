package com.softeer.togeduck.data.dto.response.reserve_status

import com.squareup.moshi.Json

data class ReserveStatusResponse(
    @Json(name = "content")
    val content: List<ReserveStatusItemResponse>
)

data class ReserveStatusItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "startedAt")
    val startedAt: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "stationName")
    val stationName: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "totalSeats")
    val totalSeats: Int,
    @Json(name = "reservatedSeats")
    val reservatedSeats: Int,
    @Json(name = "imagePath")
    val imagePath: String,
    @Json(name = "status")
    val status: String,
)