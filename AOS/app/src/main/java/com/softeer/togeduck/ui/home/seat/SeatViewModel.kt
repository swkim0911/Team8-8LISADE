package com.softeer.togeduck.ui.home.seat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeatViewModel : ViewModel() {
    private var selectedSeatNum: Int = -1

    private var _selectedSeatCnt = MutableLiveData<Int>(0)
    val selectedSeatCnt: LiveData<Int> get() = _selectedSeatCnt

    fun init(seatNum: Int) {
        selectedSeatNum = seatNum
    }
}