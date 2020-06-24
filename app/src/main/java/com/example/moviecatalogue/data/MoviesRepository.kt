package com.example.moviecatalogue.data

import com.example.moviecatalogue.network.Api
import com.example.moviecatalogue.network.GetMoviesCallback
import com.example.moviecatalogue.network.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(val api: Api) : MoviesDataSource {
    private var call: Call<MoviesResponse>? = null

    override fun getMovies(page: Int, callback: GetMoviesCallback<MovieModel>) {
        call = api.getPopularMovies(page, "ru")
        call?.enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.isSuccessful())) {
                        callback.onSuccess(it.results)
                    } else {
                        callback.onError(it.errorMessage)
                    }
                }
            }
        })
    }

    override fun cancel() {}
}