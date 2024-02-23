package com.softeer.togeduck.data.dto.request.home

data class CategoryFestivalRequest(
    val category: Int? = null,
    val filter: String? = null,
    val page: Int? = null,
    val size: Int? = null,
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "category" to category?.toString(),
            "filter" to filter,
            "page" to page?.toString(),
            "size" to size?.toString(),
        ).filterValues { it != null }.mapValues { it.value!! }
    }
}
