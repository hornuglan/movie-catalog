package com.example.moviecatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

}

//class FavouritesActivity : AppCompatActivity() {
//
//    private var favourites = arrayListOf<MovieItem>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_favourites)
//
//        val b = intent.extras
//        favourites = b?.getSerializable("favouritesList") as ArrayList<MovieItem>
//
//        initRecycler()
//    }
//
//    private fun initRecycler() {
//        val recyclerView = findViewById<RecyclerView>(R.id.list_favourites)
//        val emptyView = findViewById<View>(R.id.empty_view)
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = layoutManager
//        if (favourites.isEmpty()) {
//            showEmptyView(recyclerView, emptyView)
//        } else {
//            recyclerView.adapter = FavouritesAdapter(
//                LayoutInflater.from(this),
//                favourites,
//                object : FavouritesAdapter.OnMovieClickListener {
//                    override fun onDetailsButtonClickListener(movieItem: MovieItem) {
//                        openPreview(movieItem.title, movieItem.poster)
//                    }
//
//                    override fun onFavouritesButtonClickListener(
//                        movieItem: MovieItem,
//                        position: Int,
//                        removeFromFavouritesView: ImageView
//                    ) {
//                        favourites.remove(movieItem)
//                        recyclerView.adapter?.notifyItemRemoved(position)
//                        if (favourites.isEmpty()) {
//                            showEmptyView(recyclerView, emptyView)
//                        }
//                    }
//                })
//
//            val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//            recyclerView.addItemDecoration(itemDecoration)
//        }
//    }
//
//    //hides recycler view and shows message if the list is empty
//    private fun showEmptyView(recyclerView: RecyclerView, emptyView: View) {
//        recyclerView.visibility = View.GONE
//        emptyView.visibility = View.VISIBLE
//    }
//
//    //opens movie preview activity from the favourites list
//    fun openPreview(movieTitle: Int, moviePoster: Int) {
//        val intent = Intent(this, MovieDetailsActivity::class.java)
//        val b = Bundle()
//        b.putInt("movieTitle", movieTitle)
//        b.putInt("moviePoster", moviePoster)
//        intent.putExtras(b)
//        this.startActivity(intent)
//    }
//}
