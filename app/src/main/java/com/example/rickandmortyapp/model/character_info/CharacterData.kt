package com.example.rickandmortyapp.model.character_info

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
@Parcelize
data class CharacterData(
    val id: Int?,
    val episode: List<String?>,
    val gender: String?,
    val image: String?,
    val location: Location?,
    val name: String?,
    val origin: Origin?,
    val species: String?,
    val status: String?,
    val type: String?,
) : Parcelable