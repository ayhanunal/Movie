package com.ayhanunal.movies.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayhanunal.movies.model.ActorDetail
import com.ayhanunal.movies.model.CastX
import com.ayhanunal.movies.model.Credits
import com.ayhanunal.movies.model.MovieDetail
import com.ayhanunal.movies.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PersonDetailsViewModel(application: Application) : BaseViewModel(application) {

    private val movieApiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    var detail = MutableLiveData<ActorDetail>()
    var credits = MutableLiveData<List<CastX>>()
    var videos = MutableLiveData<List<MovieDetail>>()

    var isLoading = MutableLiveData<Boolean>()

    fun refreshData(id: Int) {
        getDataFromAPI(id)
    }

    private fun getDataFromAPI(actorId: Int) {

        disposable.add(
            movieApiService.getActorDetail(actorId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ActorDetail>() {
                    override fun onSuccess(t: ActorDetail) {
                        detail.value = t
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )

        disposable.add(
            movieApiService.getPersonCredits(actorId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Credits>() {
                    override fun onSuccess(t: Credits) {
                        credits.value = t.cast
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