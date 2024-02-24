package com.softeer.togeduck.data.mapper

import com.softeer.togeduck.data.dto.response.article_detail.ArticleDetailResponse
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel

fun ArticleDetailResponse.toArticleDetailModel(): ArticleDetailModel {
    return ArticleDetailModel(
        id = id,
        category = category,
        content = content,
        location = location,
        paths = paths,
        startedAt = startedAt,
        title = title,
    )

}