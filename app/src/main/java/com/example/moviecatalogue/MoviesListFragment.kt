package com.example.moviecatalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MoviesListFragment : Fragment() {

    var listener: OpenPreviewClickListener? = null
    var listener1: AddToFavListener? = null

    companion object {
        const val TAG = "Movie List Fragment"
    }

    private val movies = arrayListOf(
        MovieItem(R.string.guardians_of_the_galaxy_title, R.drawable.guardians_of_the_galaxy),
        MovieItem(R.string.grand_hotel_budapest_title, R.drawable.grand_budapest_hotel),
        MovieItem(R.string.gone_with_the_wind_title, R.drawable.gone_with_the_wind),
        MovieItem(R.string.what_we_do_in_the_shadows_title, R.drawable.what_we_do_in_the_shadows),
        MovieItem(R.string.iron_man_title, R.drawable.iron_man),
        MovieItem(R.string.thor_ragnarok_title, R.drawable.thor_ragnarok),
        MovieItem(R.string.knives_out_title, R.drawable.knives_out)
    )

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
        recyclerView.adapter = MoviesAdapter(
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
        fun openPreview(movieTitle: Int, moviePoster: Int)
    }

    interface AddToFavListener {
        fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView)
    }
}
