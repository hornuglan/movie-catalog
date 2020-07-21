package com.example.moviecatalogue.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviecatalogue.utils.TMDB_IMAGE_BASE_URL
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String,

    @ColumnInfo(name = "poster")
    @SerializedName("poster_path")
    var poster: String? = null,

    @ColumnInfo(name = "description")
    @SerializedName("overview")
    var description: String,

    @ColumnInfo(name = "isInFavorites")
    @SerializedName("isInFavorites")
    var isInFavorites: Boolean = false
)
{
    fun getPosterPath(): String? = poster.let { "$TMDB_IMAGE_BASE_URL$poster" }
}