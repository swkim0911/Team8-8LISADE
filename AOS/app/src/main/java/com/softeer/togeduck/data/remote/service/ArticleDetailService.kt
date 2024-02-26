package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.dto.response.article_detail.ArticleRouteResponse
import com.softeer.togeduck.data.dto.response.article_detail.RouteDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ArticleDetailService {
    @GET("festivals/{id}")
    suspend fun getArticleDetail(@Path("id") id: String): ArticleDetailResponse

    @GET("festivals/{id}/routes")
    suspend fun getArticleRoute(
        @Path("id") id: String,
        @QueryMap queryParameters: Map<String, String>,
    ): ArticleRouteResponse

    @GET("/festivals/{festival_id}/routes/{route_id}")
    suspend fun getRouteDetail(
        @Path("festival_id") festivalId: Int,
        @Path("route_id") routeId: Int
    ): RouteDetailResponse

    @GET("/festivals/{festival_id}/images")
    suspend fun getFestivalImg(
        @Path("festival_id") festivalId: Int
    ): String
}