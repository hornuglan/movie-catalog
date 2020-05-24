package com.example.moviecatalogue

import com.example.moviecatalogue.model.MovieModel
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<MovieModel>? = null,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0
)