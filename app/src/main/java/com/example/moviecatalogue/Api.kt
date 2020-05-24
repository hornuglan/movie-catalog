package com.example.moviecatalogue

import com.example.moviecatalogue.model.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int? = 1): Call<List<MovieModel>>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int): Call<MovieModel>
}