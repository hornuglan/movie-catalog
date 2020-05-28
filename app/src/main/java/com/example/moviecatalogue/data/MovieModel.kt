package com.example.moviecatalogue.data

import android.os.Parcelable
import com.example.moviecatalogue.utils.TMDB_IMAGE_BASE_URL
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val movieTitle: String,
    @SerializedName("poster_path")
    val moviePosterPath: String? = null,
    @SerializedName("overview")
    val movieDescription: String
) : Parcelable {
    fun getPosterPath(): String? {
        return if (moviePosterPath == null) {
            null
        } else {
            "${TMDB_IMAGE_BASE_URL}$moviePosterPath"
        }
    }
}