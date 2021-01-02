package com.example.projokes.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.projokes.model.JokeCategory
import com.example.projokes.model.JokeDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {

    val jokeLiveData = MutableLiveData<JokeCategory>()

    fun fetch(uuid: Int) {
        launch {
            val joke = JokeDatabase(getApplication()).jokeDao().getJoke(uuid)
            jokeLiveData.value = joke
        }
    }
}