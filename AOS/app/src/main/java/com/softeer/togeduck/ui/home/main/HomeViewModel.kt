package com.softeer.togeduck.ui.home.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.main.PopularArticleModel
import com.softeer.togeduck.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _popularFestivalList = MutableLiveData<List<PopularArticleModel>>()
    val popularFestivalList: LiveData<List<PopularArticleModel>> = _popularFestivalList



    init{
        getPopularFestival()
    }
    private fun getPopularFestival(){
        viewModelScope.launch {
            homeRepository.getPopularArticle()
                .onSuccess {
                    _popularFestivalList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }
    }
}