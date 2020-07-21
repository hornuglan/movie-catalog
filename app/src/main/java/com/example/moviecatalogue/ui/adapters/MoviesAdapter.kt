package com.example.moviecatalogue.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.ui.viewholders.MovieItemViewHolder
import com.example.moviecatalogue.R
import com.example.moviecatalogue.db.Movie

class MoviesAdapter(
    private val inflater: LayoutInflater,
    private var items: ArrayList<Movie>,
    private val listener: ((movieItem: Movie) -> Unit)?,
    private val listener1: ((movieItem: Movie, addToFavouritesView: ImageView) -> Unit)?

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

    fun updateRecyclerView(data: List<Movie>) {
        items = data as ArrayList<Movie>
        notifyDataSetChanged()
    }
}