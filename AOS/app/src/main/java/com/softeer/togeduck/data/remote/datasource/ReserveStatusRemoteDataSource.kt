package com.softeer.togeduck.data.remote.datasource

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatusDetailResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import com.softeer.togeduck.data.remote.service.ReserveStatusService
import retrofit2.Response
import javax.inject.Inject

class ReserveStatusRemoteDataSource @Inject constructor(
    private val reserveStatusService: ReserveStatusService
) {
    suspend fun getReserveStatusList(
        page: Int,
        size: Int,
    ): Response<ReserveStatusResponse> {
        return reserveStatusService.getReserveStatusList(page, size)
    }

    suspend fun getReserveStatusDetail(
        routeId: Int,
    ): Response<ReserveStatusDetailResponse> {
        return reserveStatusService.getReserveStatusDetail(routeId)
    }
}