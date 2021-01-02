package com.example.projokes.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.projokes.model.*
import com.example.projokes.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ListViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val jokesService = JokesApiService()
    private val disposable = CompositeDisposable()

    val jokes = MutableLiveData<List<JokeCategory>>()
    val jokesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()

        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val jokes = JokeDatabase(getApplication()).jokeDao().getAllJokes()
            jokesRetrieved(jokes)
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            jokesService.getJokes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<JokeCategory>>() {
                    override fun onSuccess(jokeList: List<JokeCategory>) {
                        storeJokesLocally(jokeList)
                    }

                    override fun onError(e: Throwable) {
                        jokesLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun jokesRetrieved(jokeList: List<JokeCategory>){
        jokes.value = jokeList
        jokesLoadError.value = false
        loading.value = false
    }

    private fun storeJokesLocally(list: List<JokeCategory>){
        launch {
            val dao = JokeDatabase(getApplication()).jokeDao()
            dao.deleteAllJokes()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            jokesRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}