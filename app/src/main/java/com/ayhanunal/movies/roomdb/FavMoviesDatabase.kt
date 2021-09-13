package com.ayhanunal.movies.roomdb

import android.content.Context
import androidx.room.*
import com.ayhanunal.movies.model.Result

@Database(entities = arrayOf(Result::class), version = 1)
@TypeConverters(DataConverter::class)
abstract class FavMoviesDatabase : RoomDatabase() {

    abstract fun favMoviesDao() : FavMoviesDao

    //Singleton
    companion object {

        @Volatile private var instance : FavMoviesDatabase? = null
        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext, FavMoviesDatabase::class.java,"favmoviesdatabase"
        ).build()
    }

}