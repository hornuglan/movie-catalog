package com.example.moviecatalogue

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var inviteFriendButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies = ArrayList<Movie>()
        movies.add(
            Movie(
                R.string.guardians_of_the_galaxy_title,
                R.drawable.guardians_of_the_galaxy
            )
        )
        movies.add(Movie(R.string.grand_hotel_budapest_title, R.drawable.grand_budapest_hotel))
        movies.add(Movie(R.string.gone_with_the_wind_title, R.drawable.gone_with_the_wind))
        movies.add(
            Movie(
                R.string.what_we_do_in_the_shadows_title,
                R.drawable.what_we_do_in_the_shadows
            )
        )
        movies.add(Movie(R.string.iron_man_title, R.drawable.iron_man))
        movies.add(Movie(R.string.thor_ragnarok_title, R.drawable.thor_ragnarok))
        movies.add(Movie(R.string.knives_out_title, R.drawable.knives_out))

        val adapter = MovieAdapter(this, movies)

        listView = findViewById(R.id.list_movies)

        listView.adapter = adapter

        inviteFriendButton = findViewById(R.id.invite_friend_button)
        inviteFriendButton.setOnClickListener { inviteFriend() }
    }

    fun openPreview(textView: TextView, movieTitle: String, moviePoster: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val b = Bundle()
        b.putString("movieTitle", movieTitle)
        b.putInt("moviePoster", moviePoster)
        intent.putExtras(b)
        this.startActivity(intent)

        textView.setTextColor(resources.getColor(R.color.clickedTitle))
    }

    private fun inviteFriend() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_TEXT, "Hello! Join me in this Movies Catalogue App:-)")
        this.startActivity(intent)
    }
}