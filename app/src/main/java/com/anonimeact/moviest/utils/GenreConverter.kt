package com.anonimeact.moviest.utils

import androidx.room.TypeConverter
import com.anonimeact.moviest.models.Genre
import com.google.gson.Gson

class GenreConverter {
    @TypeConverter
    fun fromString(value: String): List<Genre> {
        return Utility.jsonToArray(value)
    }

    @TypeConverter
    fun fromList(list: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}