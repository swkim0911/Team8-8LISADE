package com.softeer.togeduck.ui.home.main.home_category

import android.media.metrics.Event
import android.util.EventLog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.model.home.main.PopularArticleModel
import com.softeer.togeduck.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeCategoryViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _categoryList = MutableLiveData<List<HomeCategoryModel>>()
    val categoryList: LiveData<List<HomeCategoryModel>> = _categoryList

    private val _categoryNum = MutableLiveData(-1)
    val categoryNum = _categoryNum

    init{
        getCategory()
    }
    private fun getCategory(){
        viewModelScope.launch {
          homeRepository.getCategory()
                .onSuccess {
                    _categoryList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }
    }

}