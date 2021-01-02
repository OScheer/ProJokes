package com.example.projokes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class JokeCategory(
    @ColumnInfo(name = "category_id")
    @SerializedName("id")
    val categoryId: String?,

    @ColumnInfo(name = "joke_setup")
    @SerializedName("setup")
    val jokeSetup: String?,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    val jokeType: String?,

    @ColumnInfo(name = "punchline")
    @SerializedName("punchline")
    val jokePunchline: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}