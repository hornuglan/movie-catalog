package com.example.moviecatalogue.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {
    @Insert
    fun insertAll(vararg movies: Movie): List<Long>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE uuid=:id")
    fun getMovie(id: Int): Movie

    @Query("SELECT * FROM movie WHERE isInFavorites=:isInFavorites")
    fun getFavorites(isInFavorites: Boolean): List<Movie>

    @Query("DELETE FROM movie")
    fun deleteMovies()
}