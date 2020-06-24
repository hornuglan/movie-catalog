package com.example.moviecatalogue.data

import android.os.Parcelable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.R
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
        return moviePosterPath
    }
}

var movies: ArrayList<MovieModel> = ArrayList()
var favourites: ArrayList<MovieModel> = ArrayList()
val indInFavor: MutableList<Int> = mutableListOf()
fun ImageView.loadPoster(uri: String?) {
    val options = RequestOptions()
        .placeholder(R.color.movieDescriptionPosterPlaceholder)
        .fallback(R.drawable.ic_broken_image_black_18dp)
        .error(R.drawable.ic_broken_image_black_18dp)
        .centerCrop()
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}