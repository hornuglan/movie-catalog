package com.example.moviecatalogue.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    fun buildDatabase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        MoviesDatabase::class.java,
        "moviesDatabase"
    ).build()
}