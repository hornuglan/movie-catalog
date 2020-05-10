package com.example.moviecatalogue

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var inviteFriendButton: Button
    private lateinit var goToFavouritesButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var bottomNav: BottomNavigationView
    private val themeKey = "currentTheme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedPreferences = getSharedPreferences(
//            "ThemePref",
//            Context.MODE_PRIVATE
//        )
//
//        when (sharedPreferences.getString(themeKey, "appTheme")) {
//            "light" -> theme.applyStyle(R.style.Light, true)
//            "dark" -> theme.applyStyle(R.style.Dark, true)
//        }

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.main_list -> Toast.makeText(this, "Main List", Toast.LENGTH_SHORT).show()
                R.id.favourites_list -> Toast.makeText(this, "Fav List", Toast.LENGTH_SHORT).show()
            }
            true
        })

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.movie_list_frame, MoviesListFragment())
            .commit()

//        inviteFriendButton = findViewById(R.id.invite_friend_button)
//        inviteFriendButton.setOnClickListener { inviteFriend() }
//
//        goToFavouritesButton = findViewById(R.id.favourites_button)
//        goToFavouritesButton.setOnClickListener { goToFavourites() }
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

    //opens favourites list activity
    private fun goToFavourites() {
//        val intent = Intent(this, FavouritesActivity::class.java)
//        val b = Bundle()
//        b.putSerializable("favouritesList", favourites)
//        intent.putExtras(b)
//        this.startActivity(intent)
    }

    //changes app theme
//    fun changeTheme(view: View) {
//
//        when (sharedPreferences.getString(themeKey, "appTheme")) {
//            "dark" -> sharedPreferences.edit().putString(themeKey, "light").apply()
//            "light" -> sharedPreferences.edit().putString(themeKey, "dark").apply()
//        }
//
//        val intent = intent
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        finish()
//        startActivity(intent)
//    }
}