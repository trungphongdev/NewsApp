package com.example.newsappmvvm.api

import com.example.newsappmvvm.model.NewsRespone
import com.example.newsappmvvm.ulti.Constaints.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.*

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsRespone>



    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        keyword: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsRespone>
}