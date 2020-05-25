package com.example.moviecatalogue

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatalogue.model.MovieModel

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTv: TextView = itemView.findViewById(R.id.movie_title)
    private val posterIv: ImageView = itemView.findViewById(R.id.movie_poster)

    fun bind(item: MovieItem) {
        titleTv.text = item.title

        Glide.with(posterIv)
            .load(item.poster)
            .centerCrop()
            .into(posterIv)
    }
}