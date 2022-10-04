package com.example.rickandmortyapp.model.character_info

import android.os.Parcelable
import com.example.rickandmortyapp.model.Info
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val info: Info,
    val results: List<CharacterData>?
)