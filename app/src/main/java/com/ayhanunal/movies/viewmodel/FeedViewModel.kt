package com.ayhanunal.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayhanunal.movies.model.Movie
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.model.Search
import com.ayhanunal.movies.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    var movies = MutableLiveData<List<Result>>()
    var movieError = MutableLiveData<Boolean>()
    var movieLoading = MutableLiveData<Boolean>()

    fun refreshData(page: Int, query: String? = null) {
        getDataFromAPI(page, query)
    }

    private fun getDataFromAPI(page: Int, query: String?) {
        movieLoading.value = true

        if (query == null) {
            disposable.add(
                movieApiService.getMovies(page)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Movie>() {
                        override fun onSuccess(t: Movie) {
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
        } else {
            disposable.add(
                movieApiService.searchMovies(page, query)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Search>() {
                        override fun onSuccess(t: Search) {
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

    }

}