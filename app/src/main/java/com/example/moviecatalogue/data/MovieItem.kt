package com.example.moviecatalogue.data

import android.os.Parcelable
import com.example.moviecatalogue.utils.TMDB_IMAGE_BASE_URL
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    val id: Long,
    val title: String,
    val poster: String,
    val description: String
) : Parcelable {
    fun getPosterPath(): String? {
        return if (poster == null) {
            null
        } else {
            "$TMDB_IMAGE_BASE_URL$poster"
        }
    }
}
