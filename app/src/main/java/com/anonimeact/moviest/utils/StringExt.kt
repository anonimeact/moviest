package com.anonimeact.moviest.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatLongDate() : String {
    val instant = Instant.parse(this)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"))
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
    return outputFormatter.format(localDateTime)
}

fun String.formatShortDate() : String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())
    val date = LocalDate.parse(this, inputFormatter)
    return outputFormatter.format(date)
}