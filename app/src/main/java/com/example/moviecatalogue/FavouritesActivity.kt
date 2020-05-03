package com.example.moviecatalogue

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
        recyclerView.adapter = FavouritesAdapter(LayoutInflater.from(this), favourites)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }
}
