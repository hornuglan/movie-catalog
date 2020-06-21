package com.example.moviecatalogue.network

interface GetMoviesCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}