package com.example.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movieTitle: TextView
    private lateinit var moviePoster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val b = intent.extras

        movieTitle = findViewById(R.id.movie_details_title)
        movieTitle.text = b?.getString("movieTitle")

        moviePoster = findViewById(R.id.movie_details_poster)
        moviePoster.setImageResource(b!!.getInt("moviePoster"))
    }
}
