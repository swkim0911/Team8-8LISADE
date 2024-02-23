package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.BaseResponse
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("category")
    suspend fun getCategory():HomeCategoryResponse

    @GET("festivals/best")
    suspend fun getPopularArticle():Response<HomePopularArticleResponse>
}