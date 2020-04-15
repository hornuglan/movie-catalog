package com.example.moviecatalogue

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.ArrayList

class MovieAdapter(context: Context, sights: ArrayList<Movie>) :
    ArrayAdapter<Movie>(context, 0, sights) {

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
        imageView.setImageResource(currentMovie!!.imageResourceId)

        return listItemView
    }
}
