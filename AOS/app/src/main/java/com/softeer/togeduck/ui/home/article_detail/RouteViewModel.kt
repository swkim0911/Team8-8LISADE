package com.softeer.togeduck.ui.home.article_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.dto.request.home.ArticleRouteRequest
import com.softeer.togeduck.data.model.home.article_detail.RouteListModel
import com.softeer.togeduck.data.repository.ArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouteViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository,
) : ViewModel() {

    private val _articleRouteList = MutableLiveData<List<RouteListModel>>()
    val articleRouteList: LiveData<List<RouteListModel>> = _articleRouteList

    private val _articleId = MutableLiveData(1)
    val articleId: LiveData<Int> = _articleId


    // Need FIx
    private val params = ArticleRouteRequest(
        city = "",
        sort = "",
        page = 1,
        size = 3,
    ).toMap()

    init {
        getArticleRouteList()
    }

    private var _size = MutableLiveData("0")
    val size: LiveData<String> = _size
    fun setItemSize(itemCount: String) {
        _size.value = itemCount
    }

    private fun getArticleRouteList() {
        viewModelScope.launch {
            articleDetailRepository.getArticleRoute(_articleId.value.toString(), params)
                .onSuccess {
                    _articleRouteList.value = it
                }
                .onFailure {
                    Log.d("TESTLOG", it.toString())
                }
        }

    }
}