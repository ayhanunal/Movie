package com.ayhanunal.movies.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ayhanunal.movies.model.Movie
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.model.Search
import com.ayhanunal.movies.roomdb.FavMoviesDatabase
import com.ayhanunal.movies.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FavMoviesViewModel(application: Application) : BaseViewModel(application) {

    var movies = MutableLiveData<List<Result>>()
    var movieError = MutableLiveData<Boolean>()
    var movieLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        getFavMoviesFromRoomDB()
    }

    private fun getFavMoviesFromRoomDB(){
        movieLoading.value = true
        launch {
            val favMovies = FavMoviesDatabase(getApplication()).favMoviesDao().getAllFavMovies()
            movies.value = favMovies
            movieLoading.value = false
            if (favMovies.isNullOrEmpty()) movieError.value = true
        }
    }


}