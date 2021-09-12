package com.ayhanunal.movies.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ayhanunal.movies.model.Movie
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.model.Search
import com.ayhanunal.movies.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    var movies = MutableLiveData<List<Result>>()
    var movieError = MutableLiveData<Boolean>()
    var movieLoading = MutableLiveData<Boolean>()
    var totalPage = MutableLiveData<Int>()

    fun refreshData(page: Int, query: String? = null) {
        movieLoading.value = true
        if (query != null) searchMoviesFromAPI(page, query) else getMoviesFromAPI(page)
    }

    private fun searchMoviesFromAPI(page: Int, query: String){
        disposable.add(
            movieApiService.searchMovies(page, query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Search>() {
                    override fun onSuccess(t: Search) {
                        totalPage.value = t.total_pages
                        movies.value = t.results
                        movieError.value = false
                        movieLoading.value = false

                        if (t.results.isEmpty()) { movieError.value = true }
                    }

                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun getMoviesFromAPI(page: Int) {
        disposable.add(
            movieApiService.getMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>() {
                    override fun onSuccess(t: Movie) {
                        totalPage.value = t.total_pages
                        movies.value = t.results
                        movieError.value = false
                        movieLoading.value = false

                        if (t.results.isEmpty()) { movieError.value = true }
                    }

                    override fun onError(e: Throwable) {
                        movieLoading.value = false
                        movieError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}