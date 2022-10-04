package com.example.rickandmortyapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorData(
    val errorData: String?
)
