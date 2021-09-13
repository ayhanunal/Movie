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

class FavMoviesViewModel(application: Application) : BaseViewModel(application) {

    var movies = MutableLiveData<List<Result>>()

    //silinecek
    private val disposable = CompositeDisposable()
    private val movieApiService = MovieAPIService()


    fun refreshData() {
        getMoviesFromAPI(1)
    }

    private fun getMoviesFromAPI(page: Int) {
        disposable.add(
            movieApiService.getMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>() {
                    override fun onSuccess(t: Movie) {
                        movies.value = t.results
                    }

                    override fun onError(e: Throwable) {
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