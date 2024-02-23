package com.softeer.togeduck.data.dto.response.home


data class Banner(
    val id: Int,
    val path: String
)
data class HomePopularArticleResponse(
    val banner: List<Banner>,
    val totalSize: Int
)