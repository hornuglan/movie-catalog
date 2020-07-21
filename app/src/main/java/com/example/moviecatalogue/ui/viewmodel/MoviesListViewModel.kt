package com.example.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.App
import com.example.moviecatalogue.data.MoviesRepository
import com.example.moviecatalogue.db.Movie
import com.example.moviecatalogue.network.GetMoviesCallback
import java.util.concurrent.Executors

class MoviesListViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val moviesData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = moviesData

    private val messageErrorData = MutableLiveData<Any>()
    val messageError: LiveData<Any> = messageErrorData

    private val isMoviesLoadingData = MutableLiveData<Boolean>()
    val isMoviesLoading: LiveData<Boolean> = isMoviesLoadingData

    private val movieDetailsData = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> = movieDetailsData

    private val preferencesHelper = App.instance.preferencesHelper
    private val timeToRefresh = 1 * 60 * 1000 * 1000 * 1000L

    fun loadMovies() {
        isMoviesLoadingData.postValue(true)
        repository.getMovies(1, object : GetMoviesCallback<Movie> {
            override fun onError(error: String?) {
                isMoviesLoadingData.postValue(false)
                messageErrorData.postValue(error)
            }

            override fun onSuccess(newMovieList: List<Movie>?) {
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
        repository.getMovies(page++, object : GetMoviesCallback<Movie> {
            override fun onError(error: String?) {
                isMoviesLoadingData.postValue(false)
                messageErrorData.postValue(error)
            }

            override fun onSuccess(newMovieList: List<Movie>?) {
                isMoviesLoadingData.postValue(false)
                if (newMovieList != null) {
                    val oldMovieList = moviesData.value?.toMutableList()
                    oldMovieList?.addAll(newMovieList)
                    moviesData.postValue(oldMovieList)
                }
            }
        })
    }

    fun openMovieDetails(movie: Movie?) {
        movieDetailsData.postValue(movie)
    }

    fun checkCacheSize() {}

    fun loadFromCache() {
        isMoviesLoadingData.postValue(true)
        Executors.newSingleThreadScheduledExecutor().execute {
            val movie = App.instance.moviesDatabase.moviesDao().getAllMovies()
            moviesData.postValue(movie as List<Movie>)
            moviesLoaded(movie)
        }
    }

    fun refresh() {
        checkCacheSize()
        val updatedTime = preferencesHelper.getUpdateTime()
        if (updatedTime != null && updatedTime != 0L && System.nanoTime() - updatedTime < timeToRefresh) {
            loadFromCache()
        } else {
            loadFromRemote()
        }
    }

    fun loadFromRemote() {
        isMoviesLoadingData.postValue(true)
        repository.getMovies(page++, object : GetMoviesCallback<Movie> {
            override fun onError(error: String?) {
                isMoviesLoadingData.postValue(false)
                messageErrorData.postValue(error)
                page--
            }

            override fun onSuccess(newMovieList: List<Movie>?) {
                isMoviesLoadingData.postValue(false)
                if (newMovieList != null) {
                    val oldMovieList = if (moviesData.value == null) { arrayListOf() } else moviesData.value?.toMutableList()
                    oldMovieList?.addAll(newMovieList)
                    moviesData.postValue(oldMovieList)
                    saveToCache(oldMovieList!!.toMutableList())
                }
            }
        })
    }

    fun saveToCache(list: List<Movie>) {
        Executors.newSingleThreadScheduledExecutor().execute {
            val dao = App.instance.moviesDatabase.moviesDao()
//            val convertedMovies = list.map {
//                Movie(
//                    id = it.id,
//                    title = it.movieTitle,
//                    description = it.movieDescription,
//                    posterPath = it.moviePosterPath,
//                    isInFavorites = false
//                )
//            }

            val result = dao.insertAll(list)
//            var i = 0
//            while (i < list.size) {
//                list[i].uuid = result[i].toInt()
//                i++
//            }
            moviesLoaded(list)
        }
    }

    private fun moviesLoaded(movies: List<Movie>) {
//        moviesData.value = movies as List<Movie>
        moviesData.postValue(movies)
        isMoviesLoadingData.postValue(false)
    }

    fun loadFavorites() {}
}