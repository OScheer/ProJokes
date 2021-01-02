package com.example.projokes.model

import io.reactivex.Single
import retrofit2.http.GET

interface JokesApi {
    @GET("jokes/programming/ten")
    fun getJokes(): Single<List<JokeCategory>>
}