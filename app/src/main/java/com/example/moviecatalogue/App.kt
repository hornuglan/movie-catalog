package com.example.moviecatalogue

import android.app.Application
import com.example.moviecatalogue.network.Api
import com.example.moviecatalogue.utils.API_TOKEN
import com.example.moviecatalogue.utils.TMDB_BASIC_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    lateinit var api: Api

    override fun onCreate() {
        super.onCreate()
        instance = this

        initRetrofit()
    }

    private fun initRetrofit() {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $API_TOKEN")
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(TMDB_BASIC_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(Api::class.java)
    }

    companion object {
        lateinit var instance: App
        private set
    }
}