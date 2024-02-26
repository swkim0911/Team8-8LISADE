package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.request.home.open_route.MakeRouteRequest
import com.softeer.togeduck.data.mapper.toMakeRouteModel
import com.softeer.togeduck.data.mapper.toRegionListModel
import com.softeer.togeduck.data.model.home.open_route.MakeRouteModel
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.data.remote.datasource.home.open_route.OpenRouteRemoteDataSource
import javax.inject.Inject

class OpenRouteRepository @Inject constructor(
    private val openRouteRemoteDataSource: OpenRouteRemoteDataSource
) {
    suspend fun getRegionList():Result<List<RegionListModel>>{
        return kotlin.runCatching {
            openRouteRemoteDataSource.getRegionList().toRegionListModel()
        }
    }

    suspend fun requestMakeRoute(
        festivalId:Int,
        makeRouteRequest: MakeRouteRequest
    ):Result<MakeRouteModel>{
        return kotlin.runCatching {
            openRouteRemoteDataSource.requestMakeRoute(festivalId,makeRouteRequest).toMakeRouteModel()
        }
    }
}