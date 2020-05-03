package com.example.moviecatalogue

import java.io.Serializable

data class MovieItem(
    val title: Int,
    val poster: Int
) : Serializable
