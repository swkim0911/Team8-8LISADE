package com.softeer.togeduck.ui.home.seat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.seat.SeatsInfoModel
import com.softeer.togeduck.data.repository.SeatRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatViewModel @Inject constructor(private val seatRepository: SeatRepository) :
    ViewModel() {
    private val tag = this.javaClass.simpleName
    var routeId: Int = 0

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _seatsInfo = MutableLiveData<SeatsInfoModel>()
    val seatsInfo: LiveData<SeatsInfoModel> = _seatsInfo

    fun loadSeatsInfoData() {
        viewModelScope.launch {
            seatRepository.getSeatsInfo(routeId).onSuccess {
                _seatsInfo.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }


}