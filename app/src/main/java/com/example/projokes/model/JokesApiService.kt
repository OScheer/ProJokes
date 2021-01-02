package com.example.projokes.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JokesApiService {

    private val BASE_URL = "https://official-joke-api.appspot.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(JokesApi::class.java)

    fun getJokes(): Single<List<JokeCategory>> {
        return api.getJokes()
    }
}