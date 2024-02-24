package com.softeer.togeduck.data.repository

import android.util.Log
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import com.softeer.togeduck.data.mapper.toHomeArticleModel
import com.softeer.togeduck.data.mapper.toHomeCategoryModel
import com.softeer.togeduck.data.mapper.toPopularArticleModel
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.model.home.main.PopularArticleModel
import com.softeer.togeduck.data.remote.datasource.home.HomeRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource,
) {

    suspend fun getCategory():Result<List<HomeCategoryModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getCategory().toHomeCategoryModel()
        }.onFailure {

        }
    }

    suspend fun getPopularArticle():Result<List<PopularArticleModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getPopularArticle().toPopularArticleModel()
        }
    }

    suspend fun getCategoryFestival(params: Map<String, String>):Result<List<HomeArticleModel>>{
        return kotlin.runCatching {
            homeRemoteDataSource.getCategoryFestival(params).toHomeArticleModel()
        }
    }

}