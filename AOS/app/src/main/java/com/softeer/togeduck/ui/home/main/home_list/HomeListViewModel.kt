package com.softeer.togeduck.ui.home.main.home_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.dto.request.home.CategoryFestivalRequest
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _categoryChipList = MutableLiveData<List<HomeCategoryModel>>()
    val categoryChipList: LiveData<List<HomeCategoryModel>> = _categoryChipList

    private val _festivalList = MutableLiveData<List<HomeArticleModel>>()
    val festivalList: LiveData<List<HomeArticleModel>> = _festivalList

    private var _size = MutableLiveData("")
    val size: LiveData<String> = _size

    init {
        getCategory()
    }

    fun setItemSize(itemCount: String) {
        _size.value = itemCount
    }

    fun getCategoryFestival(categoryNum:Int) {
        val params = CategoryFestivalRequest(
            category = categoryNum,
            filter = "best",
            page = 0,
            size = 10,
        ).toMap()
        viewModelScope.launch {
            homeRepository.getCategoryFestival(params)
                .onSuccess {
                    _festivalList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }
    }

    private fun getCategory(){
        viewModelScope.launch {
            homeRepository.getCategory()
                .onSuccess {
                    _categoryChipList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }
    }
}

