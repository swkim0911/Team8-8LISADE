package com.softeer.togeduck.ui.home.main.home_category

import android.media.metrics.Event
import android.util.EventLog
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeCategoryViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private lateinit var categoryList: List<HomeCategoryModel>
    fun getCategory(){
        viewModelScope.launch {
          homeRepository.getCategory()
                .onSuccess {
                    Log.d("TESTLOG", it.toString())
                }
                .onFailure {

                }


        }
    }
}