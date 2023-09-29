package com.anonimeact.moviest.repositories

import com.anonimeact.moviest.models.ApiHandlerModel
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.services.ApiServices
import com.anonimeact.moviest.utils.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositories @Inject constructor(private val api: ApiServices) {

    suspend fun getMovieList(page: Int): ApiHandlerModel<List<MovieItemModel>?, String?> =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getMovieList(page)
                val dataMovie: List<MovieItemModel> = Utility.jsonToArray(result.results.toString())
                ApiHandlerModel(dataMovie, null)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiHandlerModel(null, errorBody)
            } catch (e: Exception) {
                ApiHandlerModel(null, e.localizedMessage)
            }
        }

    suspend fun getMovieDetail(movieId: Int): ApiHandlerModel<DetailMovieModel?, String?> =
        withContext(Dispatchers.IO) {
            try {
                val detailMovie = api.getMovieDetail(movieId)
                ApiHandlerModel(detailMovie, null)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiHandlerModel(null, errorBody)
            } catch (e: Exception) {
                ApiHandlerModel(null, e.localizedMessage)
            }
        }

    suspend fun getMovieReviews(movieId: Int): ApiHandlerModel<List<ReviewItemModel>?, String?> =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getMovieReviews(movieId)
                val reviews: List<ReviewItemModel> = Utility.jsonToArray(result.results.toString())
                ApiHandlerModel(reviews, null)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiHandlerModel(null, errorBody)
            } catch (e: Exception) {
                ApiHandlerModel(null, e.localizedMessage)
            }
        }

    suspend fun getMovieTrailers(movieId: Int): ApiHandlerModel<List<TrailersItemModel>?, String?> =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getMovieTrailers(movieId)
                val reviews: List<TrailersItemModel> =
                    Utility.jsonToArray(result.results.toString())
                ApiHandlerModel(reviews, null)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiHandlerModel(null, errorBody)
            } catch (e: Exception) {
                ApiHandlerModel(null, e.localizedMessage)
            }
        }
}