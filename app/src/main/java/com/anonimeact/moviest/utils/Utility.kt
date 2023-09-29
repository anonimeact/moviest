package com.anonimeact.moviest.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object Utility {
    inline fun <reified T> jsonToArray(stringJson: String): List<T> {
        val typeToken = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(stringJson, typeToken)
    }
}