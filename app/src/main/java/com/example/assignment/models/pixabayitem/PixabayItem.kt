package com.example.assignment.models.pixabayitem

data class PixabayItem(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)