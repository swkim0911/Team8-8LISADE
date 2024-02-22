package com.softeer.togeduck.data.model.home.open_route

import com.softeer.togeduck.data.model.home.open_route.RegionDetailModel

data class RegionListModel(
    val region : String,
    val detailList: List<RegionDetailModel>
)
