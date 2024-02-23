package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReserveStatusDetailViewModel : ViewModel() {
    private var _driverDispatch = MutableLiveData<Boolean>(true)
    val driverDispatch: LiveData<Boolean> = _driverDispatch

    private var _routeId = MutableLiveData<Int>()
    val routeId: LiveData<Int> = _routeId

    fun setRouteId(routeId: Int) {
        _routeId.value = routeId
    }

    fun loadReserveStatusDetailData() {
    }


}