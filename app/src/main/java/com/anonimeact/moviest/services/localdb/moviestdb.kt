package com.anonimeact.moviest.services.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anonimeact.moviest.models.DetailMovieModel
import com.anonimeact.moviest.utils.GenreConverter

@Database(entities = [DetailMovieModel::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class MoviestDb : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteMovieDao
}