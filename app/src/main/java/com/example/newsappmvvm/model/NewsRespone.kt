package com.example.newsappmvvm.model

data class NewsRespone(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)