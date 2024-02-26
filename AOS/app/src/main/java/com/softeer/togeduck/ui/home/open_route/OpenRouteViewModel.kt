package com.softeer.togeduck.ui.home.open_route

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.model.home.article_detail.RouteDetailModel
import com.softeer.togeduck.data.repository.ArticleDetailRepository
import com.softeer.togeduck.data.repository.OpenRouteRepository
import com.softeer.togeduck.utils.DATA_LOAD_ERROR_MESSAGE
import com.softeer.togeduck.utils.convertS3Url
import com.softeer.togeduck.utils.recordErrLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OpenRouteViewModel @Inject constructor(
    private val openRouteRepository: OpenRouteRepository,
    private val articleDetailRepository: ArticleDetailRepository
) : ViewModel() {


    private val tag = this.javaClass.simpleName.substring(0, 4)

    private var _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    private val _articleDetail = MutableLiveData<ArticleDetailModel>()
    var articleDetail:LiveData<ArticleDetailModel> = _articleDetail



    fun getArticleDetails(){
        viewModelScope.launch {
            articleDetailRepository.getFestivalDetail(10.toString())
                .onSuccess {
                    Log.d("imgurl", it.paths.convertS3Url())
                    Log.d("TESTLOG123", it.toString())
                    _articleDetail.value = it
                }
                .onFailure {
                    recordErrLog(tag, it.message!!)
                    _errMessage.value = DATA_LOAD_ERROR_MESSAGE
                }
        }
    }




}