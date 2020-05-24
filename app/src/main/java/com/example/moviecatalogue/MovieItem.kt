package com.example.moviecatalogue

import java.io.Serializable

data class MovieItem(
    val title: Int,
    val poster: Int
) : Serializable

data class MovieItem1 (
    val id: Long,
    val title: String,
    val poster: String,
    val description: String
)
