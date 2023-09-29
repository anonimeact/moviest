package com.anonimeact.moviest.services.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anonimeact.moviest.models.DetailMovieModel

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movie")
    fun getMovieItemList(): List<DetailMovieModel>

    @Query("Select * FROM favorite_movie WHERE id = :movieId")
    fun getMovieItem(movieId : Int) : DetailMovieModel

    @Insert
    fun addItemMovie(itemMovie : DetailMovieModel?) : Long

    @Query("DELETE FROM favorite_movie WHERE id = :idMovie ")
    fun deleteItemMovie(idMovie : Int?)

}