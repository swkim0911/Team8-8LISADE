package com.softeer.togeduck.ui.home.open_route

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.article_detail.RouteDetailModel
import com.softeer.togeduck.data.repository.OpenRouteRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OpenRouteViewModel @Inject constructor(
    private val openRouteRepository: OpenRouteRepository
) : ViewModel() {


    private val tag = this.javaClass.simpleName.substring(0, 4)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private val _routeDetail = MutableLiveData<RouteDetailModel>()
    val routeDetail: LiveData<RouteDetailModel> = _routeDetail



}