package com.anonimeact.moviest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.repositories.MovieFavoriteRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteViewModel @Inject constructor(
    private val repo: MovieFavoriteRepositories
) : ViewModel() {
    private val _favoriteMovieList = MutableLiveData<List<DetailMovieModel>?>()
    private val _favoriteMovieItem = MutableLiveData<DetailMovieModel?>()
    val favoriteMovieList: LiveData<List<DetailMovieModel>?> = _favoriteMovieList
    val favoriteMovieItem: LiveData<DetailMovieModel?> = _favoriteMovieItem

    fun getMovieFavoriteList() {
        viewModelScope.launch { _favoriteMovieList.postValue(repo.getFavoriteMovieList()) }
    }

    fun getMovieFavoriteItem(movieId: Int) {
        viewModelScope.launch { _favoriteMovieItem.postValue(repo.getFavoriteMovieItem(movieId)) }
    }

    fun addFavoriteMovie(itemMovie: DetailMovieModel?, onFinished: ((Long) -> Unit)? = null) {
        viewModelScope.launch {
            val idFav = repo.addFavoriteMovie(itemMovie)
            onFinished?.invoke(idFav)
        }
    }

    fun deleteFavoriteMovie(idMovie: Int?) {
        viewModelScope.launch { repo.deleteFavoriteMovie(idMovie) }
    }
}