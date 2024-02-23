package com.softeer.togeduck.data.repository

import android.util.Log
import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.dto.response.home.HomePopularArticleResponse
import com.softeer.togeduck.data.mapper.toHomeCategoryModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
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

//    suspend fun getPopularArticle():Result<List<HomePopularArticleResponse>>{
//        return kotlin.runCatching {
//            homeRemoteDataSource.getPopularArticle()
//        }
//    }

}