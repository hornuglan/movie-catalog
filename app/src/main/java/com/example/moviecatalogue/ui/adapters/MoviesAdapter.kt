package com.example.moviecatalogue.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.ui.viewholders.MovieItemViewHolder
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.data.MovieModel

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private var items: ArrayList<MovieItem>,
    private val listener: ((movieItem: MovieItem) -> Unit)?,
    private val listener1: ((movieItem: MovieItem, addToFavouritesView: ImageView) -> Unit)?

) : RecyclerView.Adapter<MovieItemViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        val detailsButton = holder.itemView.findViewById<View>(R.id.movie_details_button)
        detailsButton.setOnClickListener {
            listener?.invoke(items[position])
        }

        val addToFavouritesView =
            holder.itemView.findViewById<ImageView>(R.id.add_to_favourites_button)
        addToFavouritesView.setOnClickListener {
            listener1?.invoke(items[position], addToFavouritesView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            inflater.inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    fun updateRecyclerView(data: List<MovieModel>) {
        items =
            data.map { it ->
                MovieItem(
                    it.id.toLong(),
                    it.movieTitle,
                    it.moviePosterPath.toString(),
                    it.movieDescription
                )
            } as ArrayList<MovieItem>
        notifyDataSetChanged()
    }
}