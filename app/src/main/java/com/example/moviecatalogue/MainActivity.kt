package com.example.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var detailsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies = ArrayList<Movie>()
        movies.add(Movie(R.string.guardians_of_the_galaxy_title, R.drawable.guardians_of_the_galaxy))
        movies.add(Movie(R.string.grand_hotel_budapest_title, R.drawable.grand_budapest_hotel))
        movies.add(Movie(R.string.gone_with_the_wind_title, R.drawable.gone_with_the_wind))
        movies.add(Movie(R.string.what_we_do_in_the_shadows_title, R.drawable.what_we_do_in_the_shadows))
        movies.add(Movie(R.string.iron_man_title, R.drawable.iron_man))
        movies.add(Movie(R.string.thor_ragnarok_title, R.drawable.thor_ragnarok))
        movies.add(Movie(R.string.knives_out_title, R.drawable.knives_out))

        val adapter = MovieAdapter(this, movies)

        val listView = findViewById<ListView>(R.id.list_movies)

        listView.adapter = adapter
    }
}