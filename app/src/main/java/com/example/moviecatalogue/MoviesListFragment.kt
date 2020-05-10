package com.example.moviecatalogue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesListFragment : Fragment() {

    companion object {
        const val TAG = "Movie List Fragment"
    }

    private val favourites: ArrayList<MovieItem> = arrayListOf()

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.movie_list_recyclerview)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MoviesAdapter(LayoutInflater.from(activity), movies, object : MoviesAdapter.OnMovieClickListener {
            override fun onDetailsButtonClickListener(movieItem: MovieItem) {
                openPreview(movieItem.title, movieItem.poster)
            }

            override fun onFavouritesButtonClickListener(
                movieItem: MovieItem,
                addToFavouritesView: ImageView
            ) {
                addToFavourites(movieItem, addToFavouritesView)
            }
        })
    }

    //opens movie preview activity
    fun openPreview(movieTitle: Int, moviePoster: Int) {
//        val intent = Intent(activity, MovieDetailsActivity::class.java)
//        val b = Bundle()
//        b.putInt("movieTitle", movieTitle)
//        b.putInt("moviePoster", moviePoster)
//        intent.putExtras(b)
//        this.startActivity(intent)
    }

    //1 click on heart image view adds movie to favourites list, 2 clicks - remove movie from favourites
    private fun addToFavourites(item: MovieItem, addToFavouritesView: ImageView) {
        when (addToFavouritesView.imageTintList) {
            activity?.getColorStateList(R.color.add_to_favourites_button) -> {
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
                addToFavouritesView.imageTintList =
                    activity?.getColorStateList(R.color.added_to_favourites_button)
                favourites.add(item)
            }
            activity?.getColorStateList(R.color.added_to_favourites_button) -> {
                Toast.makeText(context, "Removed from Favourites", Toast.LENGTH_SHORT)
                    .show()
                addToFavouritesView.imageTintList =
                    activity?.getColorStateList(R.color.add_to_favourites_button)
                favourites.remove(item)
            }
        }
    }
}
