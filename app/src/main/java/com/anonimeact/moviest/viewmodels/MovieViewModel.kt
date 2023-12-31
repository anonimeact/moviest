package com.anonimeact.moviest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonimeact.moviest.models.ApiHandlerModel
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.models.MovieItemModel
import com.anonimeact.moviest.models.ReviewItemModel
import com.anonimeact.moviest.models.TrailersItemModel
import com.anonimeact.moviest.repositories.MovieRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repo: MovieRepositories
) : ViewModel() {

    private val _movies = MutableLiveData<ApiHandlerModel<List<MovieItemModel>?, String?>>()
    private val _detailMovie = MutableLiveData<ApiHandlerModel<DetailMovieModel?, String?>>()
    private val _reviews = MutableLiveData<ApiHandlerModel<List<ReviewItemModel>?, String?>>()
    private val _trailers = MutableLiveData<ApiHandlerModel<List<TrailersItemModel>?, String?>>()
    val movies: LiveData<ApiHandlerModel<List<MovieItemModel>?, String?>> = _movies
    val detailMovie: LiveData<ApiHandlerModel<DetailMovieModel?, String?>> = _detailMovie
    val reviews: LiveData<ApiHandlerModel<List<ReviewItemModel>?, String?>> = _reviews
    val trailers: LiveData<ApiHandlerModel<List<TrailersItemModel>?, String?>> = _trailers

    fun getMovieList(page: Int) {
        viewModelScope.launch { repo.getMovieList(page).collect { _movies.postValue(it) } }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            repo.getMovieDetail(movieId).collect { _detailMovie.postValue(it) }
            repo.getMovieReviews(movieId).collect { _reviews.postValue(it) }
            repo.getMovieTrailers(movieId).collect { _trailers.postValue(it) }
        }
    }
}