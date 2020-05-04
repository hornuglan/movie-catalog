package com.example.moviecatalogue

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var inviteFriendButton: Button
    private lateinit var goToFavouritesButton: Button

    private lateinit var sharedPreferences: SharedPreferences
    private val themeKey = "currentTheme"

    private val movies = arrayListOf(
        MovieItem(R.string.guardians_of_the_galaxy_title, R.drawable.guardians_of_the_galaxy),
        MovieItem(R.string.grand_hotel_budapest_title, R.drawable.grand_budapest_hotel),
        MovieItem(R.string.gone_with_the_wind_title, R.drawable.gone_with_the_wind),
        MovieItem(R.string.what_we_do_in_the_shadows_title, R.drawable.what_we_do_in_the_shadows),
        MovieItem(R.string.iron_man_title, R.drawable.iron_man),
        MovieItem(R.string.thor_ragnarok_title, R.drawable.thor_ragnarok),
        MovieItem(R.string.knives_out_title, R.drawable.knives_out)
    )

    private val favourites: ArrayList<MovieItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(
            "ThemePref",
            Context.MODE_PRIVATE
        )

        when (sharedPreferences.getString(themeKey, "appTheme")) {
            "light" -> theme.applyStyle(R.style.Light, true)
            "dark" -> theme.applyStyle(R.style.Dark, true)
        }

        setContentView(R.layout.activity_main)

        initRecycler()

        inviteFriendButton = findViewById(R.id.invite_friend_button)
        inviteFriendButton.setOnClickListener { inviteFriend() }

        goToFavouritesButton = findViewById(R.id.favourites_button)
        goToFavouritesButton.setOnClickListener { goToFavourites() }
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog)
        dialog
            .setTitle(R.string.close_app_title)
            .setMessage(R.string.close_app_message)
        dialog.apply {
            setPositiveButton(R.string.positive_button) { _, _ ->
                super.onBackPressed()
            }
            setNegativeButton(R.string.negative_button) { dialog, _ ->
                dialog.cancel()
            }
        }

        dialog.show()
    }

    private fun initRecycler() {
        val recyclerView = findViewById<RecyclerView>(R.id.list_movies)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MoviesAdapter(
            LayoutInflater.from(this),
            movies,
            object : MoviesAdapter.OnMovieClickListener {
                override fun onDetailsButtonClickListener(movieItem: MovieItem) {
                    openPreview(movieItem.title, movieItem.poster)
                }

                override fun onFavouritesButtonClickListener(
                    movieItem: MovieItem,
                    addToFavouritesView: ImageView
                ) {
                    addToFavourites(movieItem, addToFavouritesView)
                }
            })

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    //opens movie preview activity
    fun openPreview(movieTitle: Int, moviePoster: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val b = Bundle()
        b.putInt("movieTitle", movieTitle)
        b.putInt("moviePoster", moviePoster)
        intent.putExtras(b)
        this.startActivity(intent)
    }

    //1 click on heart image view adds movie to favourites list, 2 clicks - remove movie from favourites
    private fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView) {
        when (addToFavouritesView.imageTintList) {
            this.getColorStateList(R.color.add_to_favourites_button) -> {
                Toast.makeText(this@MainActivity, "Added to Favourites", Toast.LENGTH_SHORT).show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.added_to_favourites_button)
                favourites.add(item)
            }
            this.getColorStateList(R.color.added_to_favourites_button) -> {
                Toast.makeText(this@MainActivity, "Removed from Favourites", Toast.LENGTH_SHORT)
                    .show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.add_to_favourites_button)
                favourites.remove(item)
            }
        }
    }

    //sends ain invitation by e-mail
    private fun inviteFriend() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_TEXT, R.string.invite_friend)
        this.startActivity(intent)
    }

    //opens favourites list activity
    private fun goToFavourites() {
        val intent = Intent(this, FavouritesActivity::class.java)
        val b = Bundle()
        b.putSerializable("favouritesList", favourites)
        intent.putExtras(b)
        this.startActivity(intent)
    }

    //changes app theme
    fun changeTheme(view: View) {

        when (sharedPreferences.getString(themeKey, "appTheme")) {
            "dark" -> sharedPreferences.edit().putString(themeKey, "light").apply()
            "light" -> sharedPreferences.edit().putString(themeKey, "dark").apply()
        }

        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }
}