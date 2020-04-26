package com.example.moviecatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(val inflater: LayoutInflater, val items: ArrayList<MovieItem>) : RecyclerView.Adapter<MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }
}