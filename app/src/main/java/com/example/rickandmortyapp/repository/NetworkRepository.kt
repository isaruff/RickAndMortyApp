package com.example.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.rickandmortyapp.network.ApiService
import com.example.rickandmortyapp.network.ResponseState
import com.example.rickandmortyapp.paging_source.PagingDataSource


class NetworkRepository(private val api: ApiService) : RepositoryParent() {


    fun getCharacterData() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200
        ), pagingSourceFactory = { PagingDataSource(api) }
    ).liveData

    suspend fun getEpisode(url: String) : ResponseState{
        return try{
            val response = api.getEpisode(url)
            getResponseState(response)
        } catch (e: Exception) {
            ResponseState.NetworkException(e.message)
        }
    }

    suspend fun getLocation(url: String): ResponseState {
        return try {
            val response = api.getLocation(url)
            getResponseState(response)
        } catch (e: Exception) {
            ResponseState.NetworkException(e.message)
        }
    }


}