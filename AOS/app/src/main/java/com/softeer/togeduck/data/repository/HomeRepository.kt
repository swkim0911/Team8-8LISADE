package com.softeer.togeduck.data.repository

import com.softeer.togeduck.data.dto.response.home.HomeCategoryResponse
import com.softeer.togeduck.data.remote.datasource.home.HomeCategoryRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeCategoryRemoteDataSource: HomeCategoryRemoteDataSource,
) {

    suspend fun getCategory():Result<HomeCategoryResponse>{
        return kotlin.runCatching {
            homeCategoryRemoteDataSource.getCategory()
        }
    }

//    suspend fun getPopularArticle():Response<HomePopularArticleResponse>{
//    }

}