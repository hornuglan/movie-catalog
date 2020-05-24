package com.example.moviecatalogue.model

import com.example.moviecatalogue.utils.TMDB_IMAGE_BASE_URL
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val movieTitle: String,
    @SerializedName("poster_path")
    val moviePosterPath: String? = null,
    @SerializedName("overview")
    val movieDescription: String
) : Serializable {
    fun getPosterPath() : String? {
        return if (moviePosterPath == null) {
            null
        } else {
            "${TMDB_IMAGE_BASE_URL}$moviePosterPath"
        }
    }
}