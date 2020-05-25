package com.example.moviecatalogue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.App
import com.example.moviecatalogue.data.MovieItem
import com.example.moviecatalogue.ui.adapters.MoviesAdapter
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.MovieModel
import com.example.moviecatalogue.network.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MoviesListFragment : Fragment() {

    var listener: OpenPreviewClickListener? = null
    var listener1: AddToFavListener? = null

//    val items: ArrayList<MovieModel> = arrayListOf()

    companion object {
        const val TAG = "Movie List Fragment"
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

        val recyclerView = view.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            MoviesAdapter(
                LayoutInflater.from(activity),
                movies,
                { listener?.openPreview(it.title, it.poster) },
                { movieItem: MovieItem, addToFavouritesView: ImageView ->
                    listener1?.addToFavourites(
                        movieItem,
                        addToFavouritesView
                    )
                }
            )
        loadMovies()
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
        fun openPreview(movieTitle: String, moviePoster: String)
    }

    interface AddToFavListener {
        fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView)
    }

    fun loadMovies() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        App.instance.api.getPopularMovies()
            .enqueue(object : Callback<MyResponse> {
                override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
                override fun onResponse(
                    call: Call<MyResponse>,
                    response: Response<MyResponse>
                ) {
                    movies.clear()
                    if (response.isSuccessful) {
                        val results = response.body()?.results
                            ?.forEach {
                                movies.add(
                                    MovieItem(
                                        it.id.toLong(),
                                        it.movieTitle,
                                        it.moviePosterPath!!,
                                        it.movieDescription
                                    )
                                )
                            }
                    }
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            })
    }
}
