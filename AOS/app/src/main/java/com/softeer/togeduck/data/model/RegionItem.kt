package com.softeer.togeduck.data.model

sealed class RegionItem {
    data class Header(val regionName: String) : RegionItem()
    data class Detail(val detail: RegionDetailModel) : RegionItem()
}