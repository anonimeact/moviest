package com.anonimeact.moviest.models

data class MovieItemModel(
    val id: Int,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val release_date: String
)