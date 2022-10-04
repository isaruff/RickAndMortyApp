package com.example.rickandmortyapp.model.character_info

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Origin(
    val name: String?,
    val url: String?
): Parcelable