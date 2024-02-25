package com.softeer.togeduck.data.remote.service

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.GET
import retrofit2.http.Path


interface ArticleDetailService {
    @GET("festivals/{id}")
    suspend fun getArticleDetail(@Path("id") id:String): ArticleDetailResponse

}