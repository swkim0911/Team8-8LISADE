package com.softeer.togeduck.ui.home.seat

import androidx.lifecycle.ViewModel
import com.softeer.togeduck.data.repository.SeatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeatSelectViewModel @Inject constructor(private val seatRepository: SeatRepository) :
    ViewModel() {


}