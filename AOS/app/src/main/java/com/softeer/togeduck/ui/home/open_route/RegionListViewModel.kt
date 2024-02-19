package com.softeer.togeduck.ui.home.open_route

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegionListViewModel : ViewModel() {
    private val _selectedRegion = MutableLiveData("선택")
    val selectedRegion: LiveData<String> = _selectedRegion
    fun setSelectedRegion(text: String) {
        _selectedRegion.value = text
    }
}