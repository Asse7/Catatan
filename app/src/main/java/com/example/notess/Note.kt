package com.example.notess

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val status: Int // 0 = belum selesai, 1 = selesai
)