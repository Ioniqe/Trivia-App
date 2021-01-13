package com.example.trivia.model

import java.time.Instant

class Question(
    val id: Int,
    val answer: String,
    val question: String,
    val value: Int,
    val airdate: String,
    val created_at: String,
    val updated_at: String,
    val category_id: Int,
    val game_id: Int,
    val category: Category
)