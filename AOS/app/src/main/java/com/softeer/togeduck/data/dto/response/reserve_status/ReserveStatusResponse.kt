package com.softeer.togeduck.data.dto.response.reserve_status

import com.softeer.togeduck.data.dto.response.reserve_status.reserve_detail.ReserveStatus
import com.squareup.moshi.Json

data class ReserveStatusResponse(
    @Json(name = "content")
    val content: List<ReserveStatusItemResponse>,
    @Json(name = "pageable")
    val pageable: Pageable,
    @Json(name = "first")
    val first: Boolean,
    @Json(name = "last")
    val last: Boolean,
    @Json(name = "size")
    val size: Int,
    @Json(name = "number")
    val number: Int,
    @Json(name = "sort")
    val sort: Sort,
    @Json(name = "numberOfElements")
    val numberOfElements: Int,
    @Json(name = "empty")
    val empty: Boolean,
)

data class ReserveStatusItemResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "startedAt")
    val startedAt: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "stationName")
    val stationName: String,
    @Json(name = "price")
    val price: Int,
    @Json(name = "status")
    val status: ReserveStatus,
    @Json(name = "totalSeats")
    val totalSeats: Int,
    @Json(name = "reservedSeats")
    val reservedSeats: Int,
    @Json(name = "imagePath")
    val imagePath: String,

    )

data class Pageable(
    @Json(name = "pageNumber")
    val pageNumber: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "sort")
    val sort: Sort,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "paged")
    val paged: Boolean,
    @Json(name = "unpaged")
    val unpaged: Boolean,
)

data class Sort(
    @Json(name = "empty")
    val empty: Boolean,
    @Json(name = "unsorted")
    val unsorted: Boolean,
    @Json(name = "sorted")
    val sorted: Boolean,
)