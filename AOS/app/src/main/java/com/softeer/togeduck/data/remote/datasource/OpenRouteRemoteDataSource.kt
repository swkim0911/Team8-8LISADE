package com.softeer.togeduck.data.remote.datasource.home.open_route

import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import com.softeer.togeduck.data.remote.service.OpenRouteService
import javax.inject.Inject

class OpenRouteRemoteDataSource @Inject constructor(
    private val openRouteService:OpenRouteService,
) {
    suspend fun getRegionList():RegionListResponse{
        return openRouteService.getRegionList()
    }

}
