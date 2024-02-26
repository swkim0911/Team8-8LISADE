package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.mapper.toArticleDetailModel
import com.softeer.togeduck.data.mapper.toRouteDetailModel
import com.softeer.togeduck.data.mapper.toRouteListModel
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.data.model.home.article_detail.RouteDetailModel
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

    suspend fun getArticleRoute(
        id:String,
        params: Map<String, String>
    ): Result<List<RouteListModel>>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getArticleRoute(id,params).toRouteListModel()
        }
    }

    suspend fun getRouteDetail(
        festivalId:Int,
        routeId:Int
    ):Result<RouteDetailModel>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getRouteDetail(festivalId,routeId).toRouteDetailModel()
        }
    }

    suspend fun getFestivalImg(
        festivalId: Int
    ):Result<String>{
        return kotlin.runCatching {
            articleDetailRemoteDataSource.getFestivalImg(festivalId)
        }
    }


}