package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.request.home.open_route.MakeRouteRequest
import com.softeer.togeduck.data.dto.response.open_route.MakeRouteResponse
import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OpenRouteService {
    @GET("locations")
    suspend fun getRegionList(): RegionListResponse

    @POST("/festivals/{festival_id}/routes")
    suspend fun requestMakeRoute(
        @Path("festival_id") festivalId: Int,
        @Body makeRouteRequest: MakeRouteRequest
    ): MakeRouteResponse
}