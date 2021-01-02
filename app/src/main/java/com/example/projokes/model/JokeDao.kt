package com.example.projokes.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JokeDao {
    @Insert
    suspend fun insertAll(vararg jokes: JokeCategory): List<Long>

    @Query("SELECT * FROM jokecategory")
    suspend fun getAllJokes(): List<JokeCategory>

    @Query("SELECT * FROM jokecategory WHERE uuid = :jokeId")
    suspend fun getJoke(jokeId: Int): JokeCategory

    @Query("DELETE FROM jokecategory")
    suspend fun deleteAllJokes()
}