package com.softeer.togeduck.data.dto.response.reserve_status

import com.squareup.moshi.Json

data class ReserveStatusResponse(
    @Json(name = "content")
    val reserveStatusList: Map<String, List<ReserveStatusItemResponse>>
)
