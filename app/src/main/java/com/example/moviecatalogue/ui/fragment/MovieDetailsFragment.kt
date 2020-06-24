package com.example.moviecatalogue.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviecatalogue.App
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.data.MovieModel
import com.example.moviecatalogue.data.loadPoster
import com.example.moviecatalogue.ui.viewmodel.MovieListViewModelFactory
import com.example.moviecatalogue.ui.viewmodel.MoviesListViewModel

class MovieDetailsFragment : Fragment() {

    private lateinit var movieTitle: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieDescription: TextView

    private var viewModel: MoviesListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movie_details, container, false)

        with(root) {
            movieTitle = findViewById(R.id.movie_details_title)
            moviePoster = findViewById(R.id.movie_details_poster)
            movieDescription = findViewById(R.id.movie_details_description)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieItem = arguments?.getParcelable(MOVIE_ITEM) ?: MovieItem(0, "", "", "")

//        movieTitle.text = movieItem.title
//        movieDescription.text = movieItem.description
//
//        Glide.with(view)
//            .load(movieItem.getPosterPath())
//            .placeholder(R.color.movieDescriptionPosterPlaceholder)
//            .fallback(R.drawable.ic_broken_image_black_18dp)
//            .error(R.drawable.ic_broken_image_black_18dp)
//            .centerCrop()
//            .into(moviePoster)

        //viewModel
        viewModel = activity?.let {
            ViewModelProvider(it, MovieListViewModelFactory(App.instance.repository)).get(
                MoviesListViewModel::class.java
            )
        }
        viewModel?.movieDetails?.observe(this, movieDetails)
    }

    private val movieDetails = Observer<MovieModel> {
//        val item = MovieItem(
//            it.id.toLong(),
//            it.movieTitle,
//            it.getPosterPath().toString(),
//            it.movieDescription
//        )
        movieTitle.text = it.movieTitle
//        moviePoster.loadPoster(item.getPosterPath().toString())
        movieDescription.text = it.movieDescription
        Glide.with(this)
            .load(it.getPosterPath())
            .placeholder(R.color.movieDescriptionPosterPlaceholder)
            .fallback(R.drawable.ic_broken_image_black_18dp)
            .error(R.drawable.ic_broken_image_black_18dp)
            .centerCrop()
            .into(moviePoster)
//        moviePoster.loadPoster(it.getPosterPath())
//        movieTitle.text = it.movieTitle
//        movieDescription.text = it.movieDescription
    }

    companion object {
        const val TAG = "Movie Details Fragment"

        const val MOVIE_ITEM = "movieItem"

        fun newInstance(item: MovieItem): MovieDetailsFragment {
            val fragment =
                MovieDetailsFragment()

            val bundle = Bundle()
            bundle.putParcelable(MOVIE_ITEM, item)
            fragment.arguments = bundle

            return fragment
        }
    }
}