package com.softeer.togeduck.ui.home.open_route

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.data.repository.RegionListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegionListViewModel @Inject constructor(
    private val regionListRepository: RegionListRepository
): ViewModel() {
    private val _selectedRegion = MutableLiveData("선택")
    val selectedRegion: LiveData<String> = _selectedRegion

    private val _regionList = MutableLiveData<List<RegionListModel>>()
    val regionList: LiveData<List<RegionListModel>> = _regionList

    fun setSelectedRegion(text: String) {
        _selectedRegion.value = text
    }

    fun getPopularFestival(){
        viewModelScope.launch {
            regionListRepository.getRegionList()
                .onSuccess {
                    _regionList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }
    }
}