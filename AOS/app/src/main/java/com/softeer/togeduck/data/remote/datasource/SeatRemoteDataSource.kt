package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.response.home.seat.SeatPaymentResponse
import com.softeer.togeduck.data.dto.response.home.seat.SeatsInfoResponse
import com.softeer.togeduck.data.remote.service.SeatService
import javax.inject.Inject

class SeatRemoteDataSource @Inject constructor(private val seatService: SeatService) {
    suspend fun getSeatsInfo(
        routeId: Int,
    ): SeatsInfoResponse {
        return seatService.getSeatsInfo(routeId)
    }

    suspend fun getSeatPayment(
        routeId: Int,
    ): SeatPaymentResponse {
        return seatService.getSeatPayment(routeId)
    }
}