package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toReserveStatusDetailModel
import com.softeer.togeduck.data.mapper.toReserveStatusModel
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.ReserveStatusDetailModel
import com.softeer.togeduck.data.remote.datasource.ReserveStatusRemoteDataSource
import javax.inject.Inject

class ReserveStatusRepository @Inject constructor(
    private val reserveStatusRemoteDataSource: ReserveStatusRemoteDataSource,
) {
    suspend fun getReserveStatusList(
        page: Int,
        size: Int,
    ): Result<ReserveStatusModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getReserveStatusList(page, size).body()!!
                .toReserveStatusModel()
        }
    }

    suspend fun getReserveStatusDetail(
        routeId: Int,
    ): Result<ReserveStatusDetailModel> {
        return kotlin.runCatching {
            reserveStatusRemoteDataSource.getReserveStatusDetail(routeId).body()!!
                .toReserveStatusDetailModel()
        }
    }
}
