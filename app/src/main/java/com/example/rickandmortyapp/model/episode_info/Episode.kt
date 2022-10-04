package com.example.rickandmortyapp.model.episode_info

import android.os.Parcelable
import androidx.lifecycle.GeneratedAdapter
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Episode(
    val id: Int?,
    val air_date: String?,
    val characters: List<String?>,
    val episode: String?,
    val name: String?
) : Parcelable