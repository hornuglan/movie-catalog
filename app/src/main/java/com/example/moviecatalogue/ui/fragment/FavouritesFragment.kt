package com.example.moviecatalogue.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.App
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.data.MovieModel
import com.example.moviecatalogue.ui.adapters.FavouritesAdapter
import com.example.moviecatalogue.ui.viewmodel.MovieListViewModelFactory
import com.example.moviecatalogue.ui.viewmodel.MoviesListViewModel
import java.io.Serializable
import java.util.concurrent.Executors


class FavouritesFragment : Fragment() {

    var listener: PreviewFromFavClickListener? = null

    private var favourites = arrayListOf<MovieItem>()
    private var viewModel: MoviesListViewModel? = null

    private lateinit var emptyView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favourites, container, false)

        with(root) {
            emptyView = findViewById(R.id.empty_view)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()

        Executors.newSingleThreadScheduledExecutor().execute {
            val favourites = loadFavourites()

            Handler(Looper.getMainLooper()).post {
                renderFavourites(view, favourites as ArrayList<MovieItem>)
            }
        }
    }

    private fun loadFavourites() : List<MovieItem> {
            val dao = App.instance!!.moviesDatabase.moviesDao()

            return dao.getFavorites(true)
                .map {
                    MovieItem(
                        id = it.id.toLong(),
                        title = it.title,
                        description = it.description,
                        poster = it.posterPath.toString()
                    )
                }
    }

    private fun renderFavourites(view: View, favourites: ArrayList<MovieItem>) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.favourites_list_recyclerview)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        if (favourites.isEmpty()) {
            showEmptyView(recyclerView, emptyView)
        } else {
            recyclerView.adapter =
                FavouritesAdapter(
                    LayoutInflater.from(activity),
                    favourites,
                    {
                        viewModel?.openMovieDetails(
                            MovieModel(
                                it.id.toInt(),
                                it.title,
                                it.getPosterPath().toString(),
                                it.description
                            )
                        )
                        listener?.openPreviewFromFavourites(it)
                    },
                    { movieItem: MovieItem, position: Int, removeFromFavouritesView: ImageView ->
                        favourites.remove(movieItem)
                        recyclerView.adapter?.notifyItemRemoved(position)
                        recyclerView.adapter?.notifyDataSetChanged()
                        if (favourites.isEmpty()) {
                            showEmptyView(recyclerView, emptyView)
                        }
                    }
                )
        }
    }

    private fun initializeViewModel() {
        viewModel = activity?.let {
            ViewModelProvider(it, MovieListViewModelFactory(App.instance.repository)).get(
                MoviesListViewModel::class.java
            )
        }
        if (favourites.isEmpty()) viewModel?.loadMovies()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is PreviewFromFavClickListener) {
            listener = activity as PreviewFromFavClickListener
        } else {
            throw Exception("Activity must implement PreviewFromFavClickListener")
        }
    }

    private fun showEmptyView(recyclerView: RecyclerView, emptyView: View) {
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    interface PreviewFromFavClickListener {
        fun openPreviewFromFavourites(item: MovieItem)
    }

    interface RemoveFromFavClickListener {
        fun removeFromFavourites(
            movieItem: MovieModel,
            position: Int,
            removeFromFavouritesView: ImageView
        )
    }

    companion object {
        const val TAG = "Favourites Fragment"

        private const val FAVOURITES_LIST = "favouritesList"

        fun newInstance(favouritesList: Serializable): FavouritesFragment {
            val fragment =
                FavouritesFragment()

            val bundle = Bundle()
            bundle.putSerializable(FAVOURITES_LIST, favouritesList)
            fragment.arguments = bundle

            return fragment
        }
    }
}