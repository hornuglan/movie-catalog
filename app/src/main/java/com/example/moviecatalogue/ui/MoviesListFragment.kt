package com.example.moviecatalogue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviecatalogue.App
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.ui.adapters.MoviesAdapter
import com.example.moviecatalogue.R
import com.example.moviecatalogue.network.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MoviesListFragment : Fragment() {

    var listener: OpenPreviewClickListener? = null
    var listener1: AddToFavListener? = null

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
        initRecycler()
        loadMovies()
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
        recyclerView?.adapter =
            MoviesAdapter(
                LayoutInflater.from(activity),
                movies,
                { listener?.openPreview(it) },
                { movieItem: MovieItem, addToFavouritesView: ImageView ->
                    listener1?.addToFavourites(
                        movieItem,
                        addToFavouritesView
                    )
                }
            )

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var page = 1
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() >= movies.size - LOAD_NEXT_PAGE_ELEMENTS && movies.size > 0) {
                    page++
                    loadNextPage(page)
                    recyclerView.adapter?.notifyItemRangeInserted(
                        movies.size + MOVIES_PER_LOAD,
                        MOVIES_PER_LOAD
                    )
                }
            }
        })
    }


    private fun loadMovies() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val progressBar = view?.findViewById<ProgressBar>(R.id.movies_list_progress_bar)
        recyclerView?.visibility = View.INVISIBLE
        progressBar?.visibility = View.VISIBLE
        App.instance.api.getPopularMovies()
            .enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {}
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    movies.clear()
                    if (response.isSuccessful) {
                        recyclerView?.visibility = View.VISIBLE
                        progressBar?.visibility = View.GONE
                        val results = response.body()?.results
                            ?.forEach {
                                movies.add(
                                    MovieItem(
                                        it.id.toLong(),
                                        it.movieTitle,
                                        it.moviePosterPath.toString(),
                                        it.movieDescription
                                    )
                                )
                            }
                    }
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            })
    }

    fun loadNextPage(page: Int) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        App.instance.api.getPopularMovies(page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {}
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    val results = response.body()?.results
                        ?.map {
                            MovieItem(
                                it.id.toLong(),
                                it.movieTitle,
                                it.moviePosterPath.toString(),
                                it.movieDescription
                            )
                        }?.toCollection(ArrayList())
                    if (results != null) {
                        movies.addAll(results)
                    }
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            })
    }

    private fun onSwipeRefresh() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val swipeRefresher = view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresher)
        swipeRefresher?.setOnRefreshListener {
            recyclerView?.adapter?.notifyItemRangeRemoved(0, movies.size)
            loadMovies()
            swipeRefresher.isRefreshing = false
        }
    }
}
