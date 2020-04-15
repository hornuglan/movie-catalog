package com.example.moviecatalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MovieAdapter(
    context: Context,
    movies: ArrayList<Movie>
) :
    ArrayAdapter<Movie>(context, 0, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_view_item, parent, false
            )
        }

        val currentMovie = getItem(position)

        val nameTextView = listItemView!!.findViewById<TextView>(R.id.movie_title)
        assert(currentMovie != null)
        nameTextView.setText(currentMovie!!.nameResourceId)

        val imageView = listItemView.findViewById<ImageView>(R.id.movie_poster)
        imageView.setImageResource(currentMovie.imageResourceId)

        val detailsButton = listItemView.findViewById<Button>(R.id.movie_details_button)
        detailsButton.setOnClickListener {
            (context as MainActivity).openPreview(
                nameTextView,
                nameTextView.text.toString(),
                currentMovie.imageResourceId
            )
        }

        return listItemView
    }
}
