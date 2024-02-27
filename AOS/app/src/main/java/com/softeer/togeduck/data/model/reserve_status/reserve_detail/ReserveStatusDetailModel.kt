package com.softeer.togeduck.data.model.reserve_status.reserve_detail

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.RecruitStatus

data class ReserveStatusDetailModel(
    val festivalId: Int,
    val title: String,
    val seatNo: Int,
    val expectedDuration: String,
    val status: RecruitStatus,
    val recruitStatus: String,
    val price: Int,
    val formattedPrice: String,
    val imagePath: String,
    val departureTime: String,
    val arrivalTime: String,
    val departurePlace: String,
    val arrivalPlace: String,
    val driverDispatched: Boolean,
    val driverInfo: DriverInfoModel,
)

data class DriverInfoModel(
    val id: Int,
    val name: String,
    val company: String,
    val phoneNumber: String,
    val carNumber: String,
    val imagePath: String,
)