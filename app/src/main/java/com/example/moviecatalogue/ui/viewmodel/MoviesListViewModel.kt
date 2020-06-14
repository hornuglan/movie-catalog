package com.example.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.MovieModel

class MoviesListViewModel(message: String?) : ViewModel() {
    private val errorLiveData = MutableLiveData<String?>()
    val error: LiveData<String?> = errorLiveData

    private val toastLiveData = MutableLiveData<String?>()
    val toast: LiveData<String?> = toastLiveData

    private val moviesLiveData = MutableLiveData<List<MovieModel>>()
//    val movies: LiveData<List<MovieModel>> = Transformations.map(moviesLiveData, ::addInfo)
//    val favourites: LiveData<List<MovieModel>> = Transformations.map(moviesLiveData, ::filterFav)

    private val selectedMovieLiveData = MutableLiveData<MovieModel>()
    val selectedMovie: LiveData<MovieModel> = selectedMovieLiveData

    private val swipeRefreshLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val swipeRefresher: LiveData<Boolean> = swipeRefreshLiveData

    init {
        if (message != null) {
            toastLiveData.postValue(message)
        }
    }
}