package com.example.newsappmvvm.repository

import com.example.newsappmvvm.api.RetrofitInstance
import com.example.newsappmvvm.database.ArticleDao
import com.example.newsappmvvm.model.Article
import com.example.newsappmvvm.model.NewsRespone
import retrofit2.Response

class NewsRepository(val articleDao: ArticleDao) {
    suspend fun insert(article: Article) {
        articleDao.insert(article)
    }
    suspend fun delete(article: Article) {
        articleDao.deleteArticle(article)
    }
    fun getSaveNews() = articleDao.getAllArticle()

    suspend fun getBreakingNews(coutryCode: String, page: Int): Response<NewsRespone> {
       return RetrofitInstance.api.getBreakingNews(coutryCode,page)
    }
    suspend fun searchNews(searchQuery: String, pageNumber: Int): Response<NewsRespone> {
        return RetrofitInstance.api.searchForNews(searchQuery,pageNumber)
    }


}