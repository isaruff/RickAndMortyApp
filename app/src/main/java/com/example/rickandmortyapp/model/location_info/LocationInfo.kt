package com.example.rickandmortyapp.model.location_info

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class LocationInfo(
    val id: Int?,
    val type: String?,
    val name: String?,
    val dimension: String?
): Parcelable