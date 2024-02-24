package com.softeer.togeduck.ui.home.article_detail

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

    init{
        getArticleDetail()
    }

    private fun getArticleDetail(){
        viewModelScope.launch {
            articleDetailRepository.getFestivalDetail("1")
                .onSuccess {
                    _articleDetail.value = it
                }
                .onFailure {

                }
        }
    }


}