package com.example.moviecatalogue.network

import com.example.moviecatalogue.data.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int? = 1): Call<MyResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int): Call<MovieModel>
}