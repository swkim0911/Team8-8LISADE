package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.home.seat.SeatsInfoResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeatService {
    @GET("routes/{route_id}/seats")
    suspend fun getSeatsInfo(@Path("route_id") routeId: Int): SeatsInfoResponse
}