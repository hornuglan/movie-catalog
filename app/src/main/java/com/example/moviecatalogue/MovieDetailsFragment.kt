package com.example.moviecatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }
}

//class MovieDetailsActivity : AppCompatActivity() {
//
//    private lateinit var movieTitle: TextView
//    private lateinit var moviePoster: ImageView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_movie_details)
//
//        val b = intent.extras
//
//        movieTitle = findViewById(R.id.movie_details_title)
//        movieTitle.setText(b!!.getInt("movieTitle"))
//
//        moviePoster = findViewById(R.id.movie_details_poster)
//        moviePoster.setImageResource(b.getInt("moviePoster"))
//    }
//}
