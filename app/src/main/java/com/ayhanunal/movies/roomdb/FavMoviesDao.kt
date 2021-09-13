package com.ayhanunal.movies.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayhanunal.movies.model.Result

@Dao
interface FavMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(favMovie: Result)

    @Query("SELECT * FROM result")
    suspend fun getAllFavMovies() : List<Result>

    @Query("SELECT COUNT(*) FROM result WHERE id = :id")
    suspend fun getFavMovie(id: Int) : Int

    @Query("DELETE FROM result WHERE id = :id")
    suspend fun deleteFavMovie(id: Int)

    @Query("DELETE FROM result")
    suspend fun deleteAllFavMovies()

}