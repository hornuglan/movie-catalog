package com.example.moviecatalogue.network

interface GetMoviesCallback<T> {
    fun onSuccess(newMovieList: List<T>?)
    fun onError(error: String?)
}