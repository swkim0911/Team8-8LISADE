package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.mapper.toArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.remote.datasource.home.article_detail.ArticleDetailRemoteDataSource
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(
    private val articleDetailRemoteDataSource: ArticleDetailRemoteDataSource,
) {

    suspend fun getFestivalDetail(
        id:String,
    ):Result<ArticleDetailModel>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getArticleDetail(id).toArticleDetailModel()
        }
    }
}