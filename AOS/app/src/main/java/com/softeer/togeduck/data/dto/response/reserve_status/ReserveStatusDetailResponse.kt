package com.softeer.togeduck.data.dto.response.reserve_status

import com.squareup.moshi.Json

data class ReserveStatusDetailResponse(
    @Json(name = "festivalId")
    val festivalId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "seatNo")
    val seatNo: Int,
    @Json(name = "expectedDuration")
    val expectedDuration: String,
    @Json(name = "status")
    val status: ReserveStatus,
    @Json(name = "totalSeats")
    val totalSeats: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "imagePath")
    val imagePath: String,
    @Json(name = "startedAt")
    val startedAt: String,
    @Json(name = "arrivedAt")
    val arrivedAt: String,
    @Json(name = "stationName")
    val stationName: String,
    @Json(name = "stationCity")
    val stationCity: String,
    @Json(name = "festivalLocation")
    val festivalLocation: String,
    @Json(name = "festivalCity")
    val festivalCity: String,
    @Json(name = "driverInfo")
    val driverInfo: DriverInfo,
)

enum class ReserveStatus {
    RECRUIT, RECRUIT_COMPLETE, OPERATION_CONFIRM, OPERATION_COMPLETE,
}

data class DriverInfo(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "company")
    val company: String,
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "carNumber")
    val carNumber: String,
)