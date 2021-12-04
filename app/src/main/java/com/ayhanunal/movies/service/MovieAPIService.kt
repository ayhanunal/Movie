package com.ayhanunal.movies.service

import com.ayhanunal.movies.configuration.LocaleSettings
import com.ayhanunal.movies.model.*
import com.ayhanunal.movies.util.Constants.API_KEY
import com.ayhanunal.movies.util.Constants.BASE_URL
import com.ayhanunal.movies.configuration.Languages
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    private var apiResponseLang: String = when(LocaleSettings.APP_LOCALE_LANGUAGE){
        "tr" -> Languages.TURKISH.apiLang
        "gr" -> Languages.GERMAN.apiLang
        else -> Languages.ENGLISH.apiLang
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getMovies(page: Int): Single<Movie> {
        return api.getMovies(page, API_KEY, apiResponseLang)
    }

    fun searchMovies(page: Int, query: String): Single<Search> {
        return api.searchMovies(page, query, API_KEY, apiResponseLang)
    }

    fun getActors(id: Int): Single<Actors> {
        return api.getActors(id, API_KEY, apiResponseLang)
    }

    fun getMovieDetail(id: Int): Single<MovieDetail> {
        return api.getMovieDetail(id, API_KEY, apiResponseLang)
    }

    fun getActorDetail(id: Int): Single<ActorDetail> {
        return api.getActorDetail(id, API_KEY, apiResponseLang)
    }

    fun getPersonCredits(id: Int): Single<Credits> {
        return api.getPersonCredits(id, API_KEY, apiResponseLang)
    }

    fun getMovieVideos(id: Int): Single<MovieVideo> {
        return api.getMovieVideos(id, API_KEY, apiResponseLang)
    }

}