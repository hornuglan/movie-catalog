package com.example.moviecatalogue.ui.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.data.MovieModel
import com.example.moviecatalogue.data.MoviesRepository
import com.example.moviecatalogue.network.GetMoviesCallback

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val moviesData = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>> = moviesData

    private val messageErrorData = MutableLiveData<Any>()
    val messageError: LiveData<Any> = messageErrorData

    private val isMoviesLoadingData = MutableLiveData<Boolean>()
    val isMoviesLoading: LiveData<Boolean> = isMoviesLoadingData

    private val movieDetailsData = MutableLiveData<MovieModel>()
    val movieDetails: LiveData<MovieModel> = movieDetailsData

    fun loadMovies() {
        isMoviesLoadingData.postValue(true)
        repository.getMovies(1, object : GetMoviesCallback<MovieModel> {
            override fun onError(error: String?) {
                isMoviesLoadingData.postValue(false)
                messageErrorData.postValue(error)
            }

            override fun onSuccess(newMovieList: List<MovieModel>?) {
                isMoviesLoadingData.postValue(false)
                if (newMovieList != null) {
                    moviesData.postValue(newMovieList)
                }
            }
        })
    }

    var page = 1
    fun loadNextPage() {
        isMoviesLoadingData.postValue(true)
        repository.getMovies(page++, object : GetMoviesCallback<MovieModel> {
            override fun onError(error: String?) {
                isMoviesLoadingData.postValue(false)
                messageErrorData.postValue(error)
            }

            override fun onSuccess(newMovieList: List<MovieModel>?) {
                isMoviesLoadingData.postValue(false)
                if (newMovieList != null) {
                    val oldMovieList = moviesData.value?.toMutableList()
                    oldMovieList?.addAll(newMovieList)
                    moviesData.postValue(oldMovieList)
                }
            }
        })
    }

    fun openMovieDetails(movie: MovieModel?) {
        movieDetailsData.postValue(movie)
    }
}