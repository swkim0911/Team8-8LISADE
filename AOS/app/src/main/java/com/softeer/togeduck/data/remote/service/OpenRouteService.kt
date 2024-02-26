package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.open_route.RegionListResponse
import retrofit2.http.GET

interface OpenRouteService {
    @GET("locations")
    suspend fun getRegionList(): RegionListResponse


}