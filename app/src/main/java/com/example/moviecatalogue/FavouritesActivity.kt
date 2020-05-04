package com.example.moviecatalogue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouritesActivity : AppCompatActivity() {

    private var favourites = arrayListOf<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val b = intent.extras
        favourites = b?.getSerializable("favouritesList") as ArrayList<MovieItem>

        initRecycler()
    }

    private fun initRecycler() {
        val recyclerView = findViewById<RecyclerView>(R.id.list_favourites)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavouritesAdapter(LayoutInflater.from(this), favourites, object : FavouritesAdapter.OnMovieClickListener {
            override fun onDetailsButtonClickListener(movieItem: MovieItem) {
                openPreview(movieItem.title, movieItem.poster)
            }

            override fun onFavouritesButtonClickListener(
                movieItem: MovieItem,
                position: Int,
                removeFromFavouritesView: ImageView
            ) {
                favourites.removeAt(position)
                recyclerView.adapter?.notifyItemRemoved(position)
            }
        })

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    fun openPreview(movieTitle: Int, moviePoster: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val b = Bundle()
        b.putInt("movieTitle", movieTitle)
        b.putInt("moviePoster", moviePoster)
        intent.putExtras(b)
        this.startActivity(intent)
    }
}
