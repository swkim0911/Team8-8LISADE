package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusDetailResponse
import com.softeer.togeduck.data.dto.response.reserve_status.ReserveStatusResponse
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ReserveStatusRepository @Inject constructor(
    private val reserveStatusRemoteDataSource: ReserveStatusRemoteDataSource,
) {
    suspend fun getReserveStatusList(
        page: Int,
        size: Int,
    ): Response<ReserveStatusResponse> {
        return reserveStatusRemoteDataSource.getReserveStatusList(page, size)
    }

    suspend fun getReserveStatusDetail(
        routeId: Int,
    ): Response<ReserveStatusDetailResponse> {
        return reserveStatusRemoteDataSource.getReserveStatusDetail(routeId)
    }
}
