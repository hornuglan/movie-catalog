package com.example.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private val items: ArrayList<MovieItem>,
    private val listener: ((movieItem: MovieItem) -> Unit)?,
    private val listener1: ((movieItem: MovieItem, addToFavouritesView: ImageView) -> Unit)?

) : RecyclerView.Adapter<MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val detailsButton = holder.itemView.findViewById<View>(R.id.movie_details_button)
        detailsButton.setOnClickListener {
//            listener.onDetailsButtonClickListener(item)
            listener?.invoke(items[position])
        }

        val addToFavouritesView =
            holder.itemView.findViewById<ImageView>(R.id.add_to_favourites_button)
        addToFavouritesView.setOnClickListener {
//            listener.onFavouritesButtonClickListener(
//                item,
//                addToFavouritesView
//            )
            listener1?.invoke(items[position], addToFavouritesView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

//    interface OnMovieClickListener {
//        fun onDetailsButtonClickListener(movieItem: MovieItem)
//        fun onFavouritesButtonClickListener(movieItem: MovieItem, addToFavouritesView: ImageView)
//    }
}