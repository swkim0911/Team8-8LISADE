package com.softeer.togeduck.data.dto.response.home

data class Category(
    val id: Int,
    val name: String,
    val image: String,
)

data class HomeCategoryResponse(
    val numberOfCategories: Int,
    val categories: List<Category>,
)