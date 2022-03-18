package com.example.newsappmvvm.ulti

sealed class Resouce<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resouce<T>(data)
    class Error<T>(data: T? = null, message: String) : Resouce<T>(data,message)
    class Loading<T> : Resouce<T>()
}