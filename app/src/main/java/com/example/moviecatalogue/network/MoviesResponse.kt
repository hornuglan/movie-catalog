package com.example.moviecatalogue.network

import com.example.moviecatalogue.db.Movie
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<Movie>? = null,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    val errorMessage: String = "Error"
) {
    fun isSuccessful(): Boolean = results!!.isNotEmpty()
}