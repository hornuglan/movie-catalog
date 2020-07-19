package com.example.moviecatalogue.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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

    @ColumnInfo(name = "posterPath")
    @SerializedName("posterPath")
    var posterPath: String? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String,

    @ColumnInfo(name = "isInFavorites")
    @SerializedName("isInFavorites")
    var isInFavorites: Boolean = false
)
//{
//    @PrimaryKey(autoGenerate = true)
//    var uuid: Int? = 0
//}