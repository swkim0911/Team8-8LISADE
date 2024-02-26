package com.softeer.togeduck.data.remote.datasource.home.open_route

import com.softeer.togeduck.data.dto.request.home.open_route.MakeRouteRequest
import com.softeer.togeduck.data.dto.response.open_route.MakeRouteResponse
import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import com.softeer.togeduck.data.remote.service.OpenRouteService
import retrofit2.http.Body
import javax.inject.Inject

class OpenRouteRemoteDataSource @Inject constructor(
    private val openRouteService: OpenRouteService,
) {
    suspend fun getRegionList(): RegionListResponse {
        return openRouteService.getRegionList()
    }

    suspend fun requestMakeRoute(
        festivalId: Int,
        @Body makeRouteRequest: MakeRouteRequest

    ): MakeRouteResponse {
        return openRouteService.requestMakeRoute(festivalId, makeRouteRequest)
    }

}
