package com.example.moviecatalogue.data

import com.example.moviecatalogue.db.Movie
import com.example.moviecatalogue.network.GetMoviesCallback

interface MoviesDataSource {
    fun getMovies(page: Int = 1, callback: GetMoviesCallback<Movie>)
    fun cancel()
}