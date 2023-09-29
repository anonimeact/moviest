package com.anonimeact.moviest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class DetailMovieModel(
    @PrimaryKey(autoGenerate = true)
    var favorite_id: Int,
    val backdrop_path: String,
    @ColumnInfo(name = "genres")
    val genres: List<Genre>,
    val id: Int,
    val imdb_id: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: Double
)

data class Genre(
    val id: Int,
    val name: String
)