package com.softeer.togeduck.data.model.reserve_status

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.RecruitStatus

data class ReserveStatusModel(
    val reserveStatus: List<ReserveStatusItemModel>
)

data class ReserveStatusItemModel(
    val id: Int,
    val festivalName: String,
    val festivalImg: String,
    val departureTime: String,
    val departurePlace: String,
    val festivalPlace: String,
    val cost: Int,
    val formattedCost: String,
    val recruitStatus: RecruitStatus,
    val recruitPhrase: String,
)