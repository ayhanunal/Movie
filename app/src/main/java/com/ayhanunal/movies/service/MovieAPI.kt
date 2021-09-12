package com.ayhanunal.movies.service

import com.ayhanunal.movies.model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    //@GET("movie/popular?page={page}&api_key=01ec5c16d3a7d5c6af56d8e7d9e3af79")

    @GET("movie/popular")
    fun getMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Single<Movie>

    @GET("movie/{id}/credits")
    fun getActors(@Path("id")movieId: Int, @Query("api_key") apiKey: String): Single<Actors>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id")movieId: Int, @Query("api_key") apiKey: String): Single<MovieDetail>

    @GET("movie/{id}/videos")
    fun getMovieVideos(@Path("id")movieId: Int, @Query("api_key") apiKey: String): Single<MovieVideo>

    @GET("search/movie")
    fun searchMovies(@Query("page") page: Int, @Query("query") query: String, @Query("api_key") apiKey: String): Single<Search>

    @GET("person/{id}")
    fun getActorDetail(@Path("id") actorId: Int, @Query("api_key") apiKey: String): Single<ActorDetail>

    @GET("person/{id}/credits")
    fun getPersonCredits(@Path("id") actorId: Int, @Query("api_key") apiKey: String): Single<Credits>

}