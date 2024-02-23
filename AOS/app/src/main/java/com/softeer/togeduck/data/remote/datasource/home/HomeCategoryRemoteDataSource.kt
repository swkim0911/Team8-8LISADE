package com.softeer.togeduck.data.remote.datasource.home

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.remote.service.HomeService
import retrofit2.Response
import javax.inject.Inject

class HomeCategoryRemoteDataSource @Inject constructor(
    private val homeService: HomeService,
) {
    suspend fun getCategory(
    ): HomeCategoryResponse {
        return homeService.getCategory()
    }
}