package com.anonimeact.moviest.repositories

import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.services.localdb.FavoriteMovieDao
import com.anonimeact.moviest.services.localdb.MoviestDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieFavoriteRepositories @Inject constructor(db: MoviestDb) {
    private val itemMovieDao: FavoriteMovieDao = db.favoriteDao()
    suspend fun addFavoriteMovie(itemMovie: DetailMovieModel?): Long = withContext(Dispatchers.IO) {
        itemMovieDao.addItemMovie(itemMovie)
    }

    suspend fun getFavoriteMovieItem(movieId: Int) =
        withContext(Dispatchers.IO) { itemMovieDao.getMovieItem(movieId) }

    suspend fun getFavoriteMovieList() =
        withContext(Dispatchers.IO) { itemMovieDao.getMovieItemList() }

    suspend fun deleteFavoriteMovie(idMovie : Int?) =
        withContext(Dispatchers.IO) { itemMovieDao.deleteItemMovie(idMovie) }
}