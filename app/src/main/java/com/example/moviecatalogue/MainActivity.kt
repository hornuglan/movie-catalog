package com.example.moviecatalogue

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.Serializable


class MainActivity :
    AppCompatActivity(),
    MoviesListFragment.OpenPreviewClickListener,
    MoviesListFragment.AddToFavListener,
    FavouritesFragment.PreviewFromFavClickListener,
    FavouritesFragment.RemoveFromFavClickListener {

    private lateinit var sharedPreferences: SharedPreferences

    private val favourites: ArrayList<MovieItem> = arrayListOf()

    private lateinit var bottomNav: BottomNavigationView
    private val themeKey = "currentTheme"

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

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.main_list -> {
                    supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.movie_list_frame, MoviesListFragment())
                    .commit()
                }
                R.id.favourites_list -> { openFavouritesFragment(favourites) }
            }
            true
        })

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MoviesListFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when(itemId) {
            R.id.invite_friend -> inviteFriend()
            R.id.change_theme -> changeTheme()
        }
        return true
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
    //opens movie details from the main movie list fragment
    override fun openPreview(movieTitle: Int, moviePoster: Int){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MovieDetailsFragment.newInstance(movieTitle, moviePoster), MovieDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    //1 click on heart image view adds movie to favourites list, 2 clicks - remove movie from favourites
    override fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView) {
        when (addToFavouritesView.imageTintList) {
            this.getColorStateList(R.color.add_to_favourites_button) -> {
                Toast.makeText(this, R.string.added_to_fav, Toast.LENGTH_SHORT).show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.added_to_favourites_button)
                favourites.add(item)
            }
            this.getColorStateList(R.color.added_to_favourites_button) -> {
                Toast.makeText(this, R.string.removed_from_fav, Toast.LENGTH_SHORT)
                    .show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.add_to_favourites_button)
                favourites.remove(item)
            }
        }
    }

    //opens movie details from the favourites
    override fun openPreviewFromFavourites(movieTitle: Int, moviePoster: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MovieDetailsFragment.newInstance(movieTitle, moviePoster), MovieDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun removeFromFavourites(movieItem: MovieItem, position: Int, removeFromFavouritesView: ImageView) {
        //remove from fav
    }

    private fun openFavouritesFragment(favourites: Serializable) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, FavouritesFragment.newInstance(favourites))
            .commit()
    }

    //sends ain invitation by e-mail
    private fun inviteFriend() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_TEXT, R.string.invite_friend)
        this.startActivity(intent)
    }

    //changes app theme
    private fun changeTheme() {

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