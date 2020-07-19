package com.example.moviecatalogue.db

import androidx.room.*

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>): List<Long>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovie(id: Int): Movie

    @Query("SELECT * FROM movie WHERE isInFavorites=:isInFavorites")
    fun getFavorites(isInFavorites: Boolean): List<Movie>

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteMovies()
}