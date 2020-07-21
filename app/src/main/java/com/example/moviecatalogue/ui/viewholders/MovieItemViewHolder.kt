package com.example.moviecatalogue.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.R
import com.example.moviecatalogue.db.Movie

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTv: TextView = itemView.findViewById(R.id.movie_title)
    private val posterIv: ImageView = itemView.findViewById(R.id.movie_poster)

    fun bind(item: Movie) {
        titleTv.text = item.title

        Glide.with(posterIv)
            .load(item.getPosterPath())
            .placeholder(R.color.movieDescriptionPosterPlaceholder)
            .fallback(R.drawable.ic_broken_image_black_18dp)
            .error(R.drawable.ic_broken_image_black_18dp)
            .centerCrop()
            .into(posterIv)
    }
}