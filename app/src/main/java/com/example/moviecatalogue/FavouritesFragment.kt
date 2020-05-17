package com.example.moviecatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import java.lang.Exception

class FavouritesFragment : Fragment() {

    var listener: PreviewFromFavClickListener? = null

    private var favourites = arrayListOf<MovieItem>()

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
        favourites = arguments?.getSerializable(FAVOURITES_LIST) as ArrayList<MovieItem>
        val recyclerView = view.findViewById<RecyclerView>(R.id.favourites_list_recyclerview)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        if (favourites.isEmpty()) {
            showEmptyView(recyclerView, emptyView)
        } else {
            recyclerView.adapter = FavouritesAdapter(
                LayoutInflater.from(activity),
                favourites,
                { listener?.openPreviewFromFavourites(it.title, it.poster) },
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
        fun openPreviewFromFavourites(movieTitle: Int, moviePoster: Int)
    }

    interface RemoveFromFavClickListener {
        fun removeFromFavourites(
            movieItem: MovieItem,
            position: Int,
            removeFromFavouritesView: ImageView
        )
    }

    companion object {
        const val TAG = "Favourites Fragment"

        private const val FAVOURITES_LIST = "favouritesList"

        fun newInstance(favouritesList: Serializable): FavouritesFragment {
            val fragment = FavouritesFragment()

            val bundle = Bundle()
            bundle.putSerializable(FAVOURITES_LIST, favouritesList)
            fragment.arguments = bundle

            return fragment
        }
    }
}