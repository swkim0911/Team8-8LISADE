package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatusDetailResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.MobileTicketResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReserveStatusService {
    @GET("users/routes")
    suspend fun getReserveStatusList(
        @Query("page") page: Int, @Query("size") size: Int
    ): ReserveStatusResponse

    @GET("users/routes/{route_id}")
    suspend fun getReserveStatusDetail(
        @Path("route_id") routeId: Int
    ): ReserveStatusDetailResponse

    @GET("user/route/{route_id}/ticket")
    suspend fun getMobileTicket(
        @Path("route_id") routeId: Int
    ): MobileTicketResponse
}