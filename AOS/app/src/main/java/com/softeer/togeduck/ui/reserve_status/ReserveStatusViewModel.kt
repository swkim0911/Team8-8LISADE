package com.softeer.togeduck.ui.reserve_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReserveStatusViewModel @Inject constructor(
    private val reserveStatusRepository: ReserveStatusRepository,
) : ViewModel() {
    private val tag = this.javaClass.simpleName.substring(0, 22)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private var _reserveStatusItems = MutableLiveData<ReserveStatusModel>()
    val reserveStatusItems: LiveData<ReserveStatusModel> = _reserveStatusItems


    fun loadReserveStatusData() {
        viewModelScope.launch {
            reserveStatusRepository.getReserveStatusList(1, 10).onSuccess {
                _reserveStatusItems.value = it
            }.onFailure {
                recordErrLog(tag, it.message!!)
                _errMessage.value = DATA_LOAD_ERROR_MESSAGE
            }
        }
    }
}