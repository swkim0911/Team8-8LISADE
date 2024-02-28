package com.softeer.togeduck.ui.reserve_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.data.remote.datasource.ReserveStatusPagingDataSource.Companion.ITEMS_PER_PAGE
import com.softeer.togeduck.data.repository.ReserveStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReserveStatusViewModel @Inject constructor(
    private val reserveStatusRepository: ReserveStatusRepository,
) : ViewModel() {
    private val tag = this.javaClass.simpleName

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    val reserveStatusItem: Flow<PagingData<ReserveStatusItemModel>> =
        Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { reserveStatusRepository.getPagingSource() }
        ).flow.cachedIn(viewModelScope)

}