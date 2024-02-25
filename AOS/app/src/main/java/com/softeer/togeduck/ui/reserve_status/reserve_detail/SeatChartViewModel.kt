package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SeatChartViewModel : ViewModel() {
    private var routeId: Int = 0

    fun setRouteId(value: Int) {
        routeId = value
    }

}