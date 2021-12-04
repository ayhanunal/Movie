package com.ayhanunal.movies.service

import androidx.annotation.Nullable
import com.ayhanunal.movies.model.*
import com.ayhanunal.movies.util.Languages
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    //@GET("movie/popular?page={page}&api_key=8ebda190411b279464efdc79f1ee09bc")

    @GET("movie/popular")
    fun getMovies(@Query("page") page: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<Movie>

    @GET("movie/{id}/credits")
    fun getActors(@Path("id")movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<Actors>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id")movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<MovieDetail>

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id")movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<MovieVideo>

    @GET("search/movie")
    fun searchMovies(@Query("page") page: Int, @Query("query") query: String, @Query("api_key") apiKey: String, @Query("language") language: String): Single<Search>

    @GET("person/{id}")
    fun getActorDetail(@Path("id") actorId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<ActorDetail>

    @GET("person/{id}/credits")
    fun getPersonCredits(@Path("id") actorId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Single<Credits>

}