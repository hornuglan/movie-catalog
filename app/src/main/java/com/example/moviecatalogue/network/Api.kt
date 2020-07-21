package com.example.moviecatalogue.network

import com.example.moviecatalogue.db.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int? = 1,
        @Query("language") language: String? = "ru"
    ): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int): Call<Movie>
}