package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toRegionListModel
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

}