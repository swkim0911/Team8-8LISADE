package com.softeer.togeduck.data.dto.response.home


data class Banner(
    val city: String,
    val id: Int,
    val location: String,
    val startedAt: String,
    val thumbnailPath: String,
    val title: String,
)
data class HomePopularArticleResponse(
    val banner: List<Banner>,
    val totalSize: Int,
)