package com.anonimeact.moviest.services

import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.GlobalListResultModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("page") page: Int
    ): GlobalListResultModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): DetailMovieModel

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int
    ): GlobalListResultModel

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int
    ): GlobalListResultModel
}