package com.anonimeact.moviest.models

import com.google.gson.JsonElement

data class GlobalListResultModel(
    val page: Int,
    val results: JsonElement
)