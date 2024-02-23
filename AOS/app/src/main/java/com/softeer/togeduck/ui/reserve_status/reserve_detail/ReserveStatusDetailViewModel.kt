package com.softeer.togeduck.ui.reserve_status.reserve_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.reserve_status.reserve_detail.ReserveStatusDetailModel
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReserveStatusDetailViewModel @Inject constructor(private val reserveStatusRepository: ReserveStatusRepository) :
    ViewModel() {
    private val tag = this.javaClass.simpleName.substring(0, 22)
    private var routeId: Int = 0

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _reserveStatusDetail = MutableLiveData<ReserveStatusDetailModel>()
    val reserveStatusDetail: LiveData<ReserveStatusDetailModel> = _reserveStatusDetail

    private var _driverDispatch = MutableLiveData<Boolean>(true)
    val driverDispatch: LiveData<Boolean> = _driverDispatch


    fun setRouteId(value: Int) {
        routeId = value
    }

    fun loadReserveStatusDetailData() {
        viewModelScope.launch {
            reserveStatusRepository.getReserveStatusDetail(routeId).onSuccess {
                _reserveStatusDetail.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }
}