package com.softeer.togeduck.data.model.reserve_status.reserve_detail

import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatus

data class ReserveStatusDetailModel(
    val festivalId: Int,
    val title: String,
    val seatNo: Int,
    val expectedDuration: String,
    val status: ReserveStatus,
    val recruitStatus: String,
    val price: Int,
    val imagePath: String,
    val departureTime: String,
    val arrivalTime: String,
    val departurePlace: String,
    val departureCity: String,
    val arrivalPlace: String,
    val arrivalCity: String,
    val driverInfo: DriverInfoModel,
)

data class DriverInfoModel(
    val id: Int,
    val name: String,
    val company: String,
    val phoneNumber: String,
    val carNumber: String,
)