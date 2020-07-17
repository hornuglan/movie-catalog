package com.example.moviecatalogue.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


@Suppress("DEPRECATION")
class SharedPreferencesHelper {
    companion object {
        const val TIME = "Preference time"
        var preferences: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferencesHelper? = null
        private var lock = Any()

        operator fun invoke(context: Context) : SharedPreferencesHelper =
            instance ?: synchronized(lock) {
                instance ?: builderHelper(context).also {
                    instance = it
                }
            }

        private fun builderHelper(context: Context): SharedPreferencesHelper {
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUpdatedTime(timestamp: Long) {
        preferences?.edit(commit = true) { putLong(TIME, timestamp) }
    }

    fun getUpdateTime() = preferences?.getLong(TIME, 0)

    fun getCacheDuration() = preferences?.getString("pref_cache_duration", "")
}