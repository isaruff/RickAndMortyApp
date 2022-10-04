package com.example.rickandmortyapp.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class Info(
    val count: Int?,
    val next: String?,
    val pages: Int?,
    val prev: String?
)