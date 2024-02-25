package com.softeer.togeduck.ui.home.article_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softeer.togeduck.data.model.home.article_detail.ArticleDetailModel
import com.softeer.togeduck.data.repository.ArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository
): ViewModel() {
    private val _articleDetail = MutableLiveData<ArticleDetailModel>()
    val articleDetail: LiveData<ArticleDetailModel> = _articleDetail
    private var articleId = 1

    fun getArticleId(id:Int){
        articleId = id
    }

    fun getArticleDetails(){
        viewModelScope.launch {
            articleDetailRepository.getFestivalDetail(articleId.toString())
                .onSuccess {
                    _articleDetail.value = it
                }
                .onFailure {

                }
        }
    }


}