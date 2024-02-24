package com.softeer.togeduck.data.remote.datasource.home.article_detail

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.remote.service.ArticleDetailService
import javax.inject.Inject

class ArticleDetailRemoteDataSource @Inject constructor(
    private val articleDetailService: ArticleDetailService,
) {
    suspend fun getArticleDetail(
        id:String,
    ): ArticleDetailResponse {
        return articleDetailService.getArticleDetail(id)
    }
}