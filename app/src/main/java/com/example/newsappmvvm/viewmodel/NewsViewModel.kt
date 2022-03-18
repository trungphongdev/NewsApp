package com.example.newsappmvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.*
import com.bumptech.glide.load.engine.Resource
import com.example.newsappmvvm.ResponseStatus
import com.example.newsappmvvm.database.ArticleDao
import com.example.newsappmvvm.database.ArticleDatabase
import com.example.newsappmvvm.model.Article
import com.example.newsappmvvm.model.NewsRespone
import com.example.newsappmvvm.repository.NewsRepository
import com.example.newsappmvvm.ulti.Resouce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val repository: NewsRepository
    val breakingNews: MutableLiveData<Resouce<NewsRespone>> = MutableLiveData()
    var breakingNewPage = 1
    var searchNewPage =1
    val searchNews: MutableLiveData<Resouce<NewsRespone>> = MutableLiveData()
    val status: MutableLiveData<ResponseStatus> = MutableLiveData()

    init {
        val dao = ArticleDatabase.getInstance(application).getArticleDao()
        repository = NewsRepository(dao)
    }

    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(contryCode: String) =  viewModelScope.launch {
        status.value = ResponseStatus.LOADING
        breakingNews.postValue(Resouce.Loading())
        try {
            val response = repository.getBreakingNews(contryCode,breakingNewPage)
            breakingNews.postValue(handlerBreakingNewsResponse(response))
            status.value = ResponseStatus.SUCCESS
        }catch (e: Exception) {
            breakingNews.postValue(Resouce.Error(message = "error"))
            status.value = ResponseStatus.ERROR
        }

    }
    private fun handlerBreakingNewsResponse(response: Response<NewsRespone>) : Resouce<NewsRespone> {
        if (response.isSuccessful) {
            status.value = ResponseStatus.SUCCESS
            response.body()?.let { resultResponse ->
                return Resouce.Success(resultResponse)
            }
        }
        return Resouce.Error(message = response.message())

    }

     fun getSearchNews(queryString: String) {
         searchNews.postValue(Resouce.Loading())
         viewModelScope.launch {
             try {
                 val response: Response<NewsRespone> = repository.searchNews(queryString,searchNewPage)
                 searchNews.value = handlerSearchView(response)
             } catch (e: Exception) {
                 searchNews.postValue(Resouce.Error(message = e.message!!))
             }

         }
    }
    fun handlerSearchView(response: Response<NewsRespone>): Resouce<NewsRespone> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resouce.Success(it)
            }
        }
        return Resouce.Error(message = response.message())

    }
    fun saveArticle(article: Article) = viewModelScope.launch{
        repository.insert(article)
    }
    fun deleteAritcle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(article)
    }

    fun getSaveNews() = repository.getSaveNews()


}
