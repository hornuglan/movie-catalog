package com.example.moviecatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar

class MovieDetailsFragment : Fragment() {

    private lateinit var movieTitle: TextView
    private lateinit var moviePoster: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)

        with(root) {
            movieTitle = findViewById(R.id.movie_details_title)
            moviePoster = findViewById(R.id.movie_details_poster)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieTitle.setText(arguments?.getInt(MOVIE_TITLE)!!)
        moviePoster.setImageResource(arguments?.getInt(MOVIE_POSTER)!!)
    }

    companion object {
        const val TAG = "Movie Details Fragment"

        const val MOVIE_TITLE = "movieTitle"
        const val MOVIE_POSTER = "moviePoster"

        fun newInstance(movieTitle: Int, moviePoster: Int) : MovieDetailsFragment {
            val fragment = MovieDetailsFragment()

            val bundle = Bundle()
            bundle.putInt(MOVIE_TITLE, movieTitle)
            bundle.putInt(MOVIE_POSTER, moviePoster)
            fragment.arguments = bundle

            return fragment
        }
    }
}