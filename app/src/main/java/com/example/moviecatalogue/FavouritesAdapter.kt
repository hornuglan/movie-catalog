package com.example.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FavouritesAdapter(val inflater: LayoutInflater, val items: ArrayList<MovieItem>) : RecyclerView.Adapter<com.example.moviecatalogue.MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: com.example.moviecatalogue.MovieItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.moviecatalogue.MovieItemViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }
}