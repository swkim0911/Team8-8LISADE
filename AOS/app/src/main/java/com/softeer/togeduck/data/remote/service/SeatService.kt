package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.home.seat.SeatPaymentResponse
import com.softeer.togeduck.data.dto.response.home.seat.SeatsInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SeatService {
    @GET("routes/{route_id}/seats")
    suspend fun getSeatsInfo(@Path("route_id") routeId: Int): SeatsInfoResponse

    @GET("routes/{route_id}/payment")
    suspend fun getSeatPayment(@Path("route_id") routeId: Int): SeatPaymentResponse
}