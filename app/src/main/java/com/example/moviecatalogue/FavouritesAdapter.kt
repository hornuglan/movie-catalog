package com.example.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.model.MovieModel

class FavouritesAdapter(
    private val inflater: LayoutInflater,
    private val items: ArrayList<MovieItem>,
    private val listener: ((movieItem: MovieItem) -> Unit)?,
    private val listener1: ((movieItem: MovieItem, position: Int, removeFromFavouritesView: ImageView) -> Unit)?
) : RecyclerView.Adapter<MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val detailsButton = holder.itemView.findViewById<View>(R.id.movie_details_button)
        detailsButton.setOnClickListener {
            listener?.invoke(items[position])
        }

        val removeFromFavouritesView =
            holder.itemView.findViewById<ImageView>(R.id.add_to_favourites_button)
        removeFromFavouritesView.setOnClickListener {
            listener1?.invoke(items[position], position, removeFromFavouritesView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }
}