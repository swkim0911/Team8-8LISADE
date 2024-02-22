package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReserveStatusDetailViewModel : ViewModel() {
    private var _driverDispatch = MutableLiveData<Boolean>(true)
    val driverDispatch: LiveData<Boolean> = _driverDispatch
}