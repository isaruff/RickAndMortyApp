package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.model.character_info.ApiResponse
import com.example.rickandmortyapp.model.episode_info.Episode
import com.example.rickandmortyapp.model.location_info.LocationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("character")
    suspend fun getCharacter(
        @Query("page") page: Int
    ): Response<ApiResponse>

    @GET
    suspend fun getLocation(@Url url:String): Response<LocationInfo>

    @GET
    suspend fun getEpisode(@Url url: String): Response<Episode>

}