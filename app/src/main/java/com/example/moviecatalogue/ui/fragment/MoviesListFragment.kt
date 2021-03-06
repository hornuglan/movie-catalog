package com.example.moviecatalogue.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviecatalogue.App
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.data.MovieModel
import com.example.moviecatalogue.ui.adapters.MoviesAdapter
import com.example.moviecatalogue.ui.viewmodel.MovieListViewModelFactory
import com.example.moviecatalogue.ui.viewmodel.MoviesListViewModel

class MoviesListFragment : Fragment() {

    var listener: OpenPreviewClickListener? = null
    var listener1: AddToFavListener? = null

    private var viewModel: MoviesListViewModel? = null
    private var adapter: MoviesAdapter? = null

    companion object {
        const val TAG = "Movie List Fragment"
        const val MOVIES_PER_LOAD = 20
        const val LOAD_NEXT_PAGE_ELEMENTS = 5
    }

    private val movies = arrayListOf<MovieItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        initRecycler()
        viewModel?.loadMovies()
        onSwipeRefresh()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is OpenPreviewClickListener) {
            listener = activity as OpenPreviewClickListener
        } else {
            throw Exception("Activity must implement OpenPreviewClickListener")
        }

        if (activity is AddToFavListener) {
            listener1 = activity as AddToFavListener
        } else {
            throw Exception("Activity must implement AddToFavListener")
        }
    }

    interface OpenPreviewClickListener {
        fun openPreview(item: MovieItem)
    }

    interface AddToFavListener {
        fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView)
    }

    private fun initRecycler() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager
        adapter = MoviesAdapter(
            LayoutInflater.from(activity),
            movies,
            {
                viewModel?.openMovieDetails(
                    MovieModel(
                        it.id.toInt(),
                        it.title,
                        it.getPosterPath().toString(),
                        it.description
                    )
                )
                listener?.openPreview(it)
            },
            { movieItem: MovieItem, addToFavouritesView: ImageView ->
                listener1?.addToFavourites(
                    movieItem,
                    addToFavouritesView
                )
            }
        )
        recyclerView?.adapter = adapter

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() >= viewModel?.movies?.value?.size!! - LOAD_NEXT_PAGE_ELEMENTS && viewModel?.movies?.value?.size!! > 0) {
                    viewModel?.loadNextPage()
                }
            }
        })
    }


    private fun initializeViewModel() {
        viewModel = activity?.let {
            ViewModelProvider(it, MovieListViewModelFactory(App.instance.repository)).get(
                MoviesListViewModel::class.java
            )
        }
        if (movies.isEmpty()) viewModel?.loadMovies()
        viewModel?.movies?.observe(this, renderMovies)
        viewModel?.isMoviesLoading?.observe(this, isMoviesLoadingObserver)
        viewModel?.messageError?.observe(this, messageErrorObserver)
        viewModel?.movieDetails?.observe(this, onMovieClicked)
    }

    private val renderMovies = Observer<List<MovieModel>> {
        val progressBar = view?.findViewById<ProgressBar>(R.id.movies_list_progress_bar)
        progressBar?.visibility = View.GONE
        adapter?.updateRecyclerView(it)
    }

    private val onMovieClicked = Observer<MovieModel> {
        viewModel?.openMovieDetails(it)
    }

    private val isMoviesLoadingObserver = Observer<Boolean> {
        val progressBar = view?.findViewById<ProgressBar>(R.id.movies_list_progress_bar)
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar?.visibility = visibility
    }

    private val messageErrorObserver = Observer<Any> {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val errorText = view?.findViewById<TextView>(R.id.loading_error_text_view)
        val retryButton = view?.findViewById<Button>(R.id.retry_button)
        recyclerView?.visibility = View.GONE
        errorText?.visibility = View.VISIBLE
        retryButton?.visibility = View.VISIBLE
        retryButton?.setOnClickListener {
            viewModel?.loadMovies()
            recyclerView?.visibility = View.VISIBLE
            errorText?.visibility = View.GONE
            retryButton.visibility = View.GONE
        }
    }

    private fun onSwipeRefresh() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val swipeRefresher = view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresher)
        swipeRefresher?.setOnRefreshListener {
            recyclerView?.adapter?.notifyItemRangeRemoved(0, movies.size)
            viewModel?.loadMovies()
            swipeRefresher.isRefreshing = false
        }
    }
}
