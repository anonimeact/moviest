package com.anonimeact.moviest.repositories

import com.anonimeact.moviest.models.ApiHandlerModel
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.services.ApiServices
import com.anonimeact.moviest.utils.Utility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieRepositories @Inject constructor(
    private val api: ApiServices,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun getMovieList(page: Int): Flow<ApiHandlerModel<List<MovieItemModel>?, String?>> {
        return flow {
            try {
                val result = api.getMovieList(page)
                val dataMovie: List<MovieItemModel> = Utility.jsonToArray(result.results.toString())
                emit(ApiHandlerModel(dataMovie, null))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(ApiHandlerModel(null, errorBody))
            } catch (e: Exception) {
                emit(ApiHandlerModel(null, e.localizedMessage))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiHandlerModel<DetailMovieModel?, String?>> {
        return flow {
            try {
                val detailMovie = api.getMovieDetail(movieId)
                emit(ApiHandlerModel(detailMovie, null))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(ApiHandlerModel(null, errorBody))
            } catch (e: Exception) {
                emit(ApiHandlerModel(null, e.localizedMessage))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getMovieReviews(movieId: Int): Flow<ApiHandlerModel<List<ReviewItemModel>?, String?>> {
        return flow {
            try {
                val result = api.getMovieReviews(movieId)
                val reviews: List<ReviewItemModel> = Utility.jsonToArray(result.results.toString())
                emit(ApiHandlerModel(reviews, null))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(ApiHandlerModel(null, errorBody))
            } catch (e: Exception) {
                emit(ApiHandlerModel(null, e.localizedMessage))
            }
        }.flowOn(ioDispatcher)
    }

    suspend fun getMovieTrailers(movieId: Int): Flow<ApiHandlerModel<List<TrailersItemModel>?, String?>> {
        return flow {
            try {
                val result = api.getMovieTrailers(movieId)
                val reviews: List<TrailersItemModel> =
                    Utility.jsonToArray(result.results.toString())
                emit(ApiHandlerModel(reviews, null))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                emit(ApiHandlerModel(null, errorBody))
            } catch (e: Exception) {
                emit(ApiHandlerModel(null, e.localizedMessage))
            }
        }.flowOn(ioDispatcher)
    }
}