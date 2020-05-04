package com.example.moviecatalogue

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTv: TextView = itemView.findViewById(R.id.movie_title)
    private val posterIv: ImageView = itemView.findViewById(R.id.movie_poster)

    fun bind(item: MovieItem) {
        titleTv.setText(item.title)
        posterIv.setImageResource(item.poster)
    }
}