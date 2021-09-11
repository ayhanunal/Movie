package com.ayhanunal.movies.service

import com.ayhanunal.movies.model.*
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    private val API_KEY = "01ec5c16d3a7d5c6af56d8e7d9e3af79"
    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getMovies(page: Int): Single<Movie> {
        return api.getMovies(page, API_KEY)
    }

    fun searchMovies(page: Int, query: String): Single<Search> {
        return api.searchMovies(page, query, API_KEY)
    }

    fun getActors(id: Int): Single<Actors> {
        return api.getActors(id, API_KEY)
    }

    fun getMovieDetail(id: Int): Single<MovieDetail> {
        return api.getMovieDetail(id, API_KEY)
    }

    fun getActorDetail(id: Int): Single<ActorDetail> {
        return api.getActorDetail(id, API_KEY)
    }

    fun getPersonCredits(id: Int): Single<Credits> {
        return api.getPersonCredits(id, API_KEY)
    }

    fun getMovieVideos(id: Int): Single<MovieVideo> {
        return api.getMovieVideos(id, API_KEY)
    }

}