package com.example.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class FavouritesAdapter(
    private val inflater: LayoutInflater,
    private val items: ArrayList<MovieItem>,
    private val listener: OnMovieClickListener
) : RecyclerView.Adapter<com.example.moviecatalogue.MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val detailsButton = holder.itemView.findViewById<View>(R.id.movie_details_button)
        detailsButton.setOnClickListener { listener.onDetailsButtonClickListener(item) }

        val removeFromFavouritesView =
            holder.itemView.findViewById<ImageView>(R.id.add_to_favourites_button)
        removeFromFavouritesView.setOnClickListener {
            listener.onFavouritesButtonClickListener(
                item,
                position,
                removeFromFavouritesView
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    interface OnMovieClickListener {
        fun onDetailsButtonClickListener(movieItem: MovieItem)
        fun onFavouritesButtonClickListener(
            movieItem: MovieItem,
            position: Int,
            removeFromFavouritesView: ImageView
        )
    }
}