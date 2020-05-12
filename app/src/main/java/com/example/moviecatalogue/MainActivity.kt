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


class MainActivity : AppCompatActivity(), MoviesListFragment.OnMovieItemClickListener, FavouritesFragment.OnFavouritesClickListener {

    private lateinit var inviteFriendButton: Button
    private lateinit var goToFavouritesButton: Button

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
                    Toast.makeText(this, "Main List", Toast.LENGTH_SHORT).show()
                    supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.movie_list_frame, MoviesListFragment(this))
                    .commit()
                }
                R.id.favourites_list -> {
                    Toast.makeText(this, "Fav List", Toast.LENGTH_SHORT).show()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.movie_list_frame, FavouritesFragment(this))
                        .commit()
                }
            }
            true
        })

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MoviesListFragment(this))
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

    //sends ain invitation by e-mail
    private fun inviteFriend() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_TEXT, R.string.invite_friend)
        this.startActivity(intent)
    }

    //opens movie details
    override fun openPreview(movieTitle: Int, moviePoster: Int) {
//        val fragmentClass = Fragment()
//        val b = Bundle()
//        b.putInt("movieTitle", movieTitle)
//        b.putInt("moviePoster", moviePoster)
//        fragmentClass.arguments = b

        val b = Bundle()
        b.putInt("movieTitle", movieTitle)
        b.putInt("moviePoster", moviePoster)
        val fragment = MovieDetailsFragment()
        fragment.arguments = b
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MovieDetailsFragment())
            .addToBackStack(null)
            .commit()
    }

    //1 click on heart image view adds movie to favourites list, 2 clicks - remove movie from favourites
    override fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView) {
        when (addToFavouritesView.imageTintList) {
            this.getColorStateList(R.color.add_to_favourites_button) -> {
                Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.added_to_favourites_button)
                favourites.add(item)
            }
            this.getColorStateList(R.color.added_to_favourites_button) -> {
                Toast.makeText(this, "Removed from Favourites", Toast.LENGTH_SHORT)
                    .show()
                addToFavouritesView.imageTintList =
                    this.getColorStateList(R.color.add_to_favourites_button)
                favourites.remove(item)
            }
        }
    }

    override fun openPreviewFromFavourites(movieTitle: Int, moviePoster: Int) {
        //open preview
    }

    override fun removeFromFavourites() {
        //remove from fav
    }

    //changes app theme
    fun changeTheme() {

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