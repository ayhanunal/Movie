package com.ayhanunal.movies.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayhanunal.movies.model.*
import com.ayhanunal.movies.roomdb.FavMoviesDatabase
import com.ayhanunal.movies.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(application: Application) : BaseViewModel(application) {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()
    var actors = MutableLiveData<List<Cast>>()
    var detail = MutableLiveData<MovieDetail>()
    var videos = MutableLiveData<MovieVideo>()
    var isFavMovie = MutableLiveData<Boolean>()

    var actorsLoading = MutableLiveData<Boolean>()
    var movieDetailLoading = MutableLiveData<Boolean>()

    fun refreshData(movieId: Int) {
        getDataFromAPI(movieId)
        checkDataFromRoomDB(movieId)
    }

    private fun checkDataFromRoomDB(id: Int){
        launch {
            val foundMovieCount = FavMoviesDatabase(getApplication()).favMoviesDao().getFavMovie(id)
            isFavMovie.value = foundMovieCount > 0
        }
    }

    fun favOrUnfavMovie(isFav: Boolean){
        launch {
            if (isFav){
                isFavMovie.value = true
                val addedMovie = detail.value?.let {
                    Result(
                        it.adult, it.backdrop_path, listOf(), it.id, it.original_language, it.original_title, it.overview, it.popularity,
                        it.poster_path, it.release_date, it.title, it.video, it.vote_average, it.vote_count
                    )
                }
                FavMoviesDatabase(getApplication()).favMoviesDao().insertFavMovie(addedMovie!!)
            }else{
                isFavMovie.value = false
                detail.value?.let {
                    FavMoviesDatabase(getApplication()).favMoviesDao().deleteFavMovie(it.id)
                }
            }
        }
    }

    private fun getDataFromAPI(movieId: Int) {
        actorsLoading.value = true
        movieDetailLoading.value = true

        disposable.add(
            movieApiService.getActors(movieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Actors>() {
                    override fun onSuccess(t: Actors) {
                        actors.value = t.cast
                        actorsLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        actorsLoading.value = false
                    }
                })
        )

        disposable.add(
            movieApiService.getMovieDetail(movieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<MovieDetail>() {
                    override fun onSuccess(t: MovieDetail) {
                        detail.value = t
                        movieDetailLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        movieDetailLoading.value = false
                    }
                })
        )

        disposable.add(
            movieApiService.getMovieVideos(movieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<MovieVideo>() {
                    override fun onSuccess(t: MovieVideo) {
                        videos.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}