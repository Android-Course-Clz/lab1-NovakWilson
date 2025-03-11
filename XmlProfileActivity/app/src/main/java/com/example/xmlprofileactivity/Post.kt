package com.example.xmlprofileactivity

data class Post(
    val id: Long,
    val content: String,
    val imageUrl: String?,
    val likeCount: Int,
    val commentCount: Int,

    val isLiked: Boolean = false
)