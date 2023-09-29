package com.anonimeact.moviest.models

data class ReviewItemModel(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Any,
    val username: String
)