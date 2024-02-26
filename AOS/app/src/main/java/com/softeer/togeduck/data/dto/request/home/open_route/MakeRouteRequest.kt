package com.softeer.togeduck.data.dto.request.home.open_route

data class MakeRouteRequest(
    val stationId: Int,
    val busId: Int,
    val distance: Int,
    val expectedTime: Int
)
