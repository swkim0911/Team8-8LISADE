package com.softeer.togeduck.ui.reservation_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReservationStatusDetailViewModel : ViewModel() {
    private var _driverDispatch = MutableLiveData<Boolean>(true)
    val driverDispatch: LiveData<Boolean> = _driverDispatch
}