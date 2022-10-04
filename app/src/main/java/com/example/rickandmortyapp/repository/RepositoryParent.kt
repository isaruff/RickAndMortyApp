package com.example.rickandmortyapp.repository

import com.example.rickandmortyapp.model.ErrorData
import com.example.rickandmortyapp.network.ResponseState
import com.example.rickandmortyapp.network.moshi
import retrofit2.Response
import java.io.IOException

open class RepositoryParent {
    fun <T> getResponseState(response: Response<T>?): ResponseState {
        return try {
            if (response?.isSuccessful == true) {
                if (response.body() != null) {
                    val data = response.body()
                    ResponseState.Success(data)
                } else {
                    ResponseState.NoContent
                }
            } else {
                val errorBody = try {
                    response?.errorBody()?.source()?.let {
                        moshi.adapter(ErrorData::class.java).fromJson(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                when (response?.code()) {
                    401 -> ResponseState.Unauthorized
                    403 -> ResponseState.HttpErrors.ResourceForbidden(errorBody)
                    404 -> ResponseState.HttpErrors.ResourceNotFound(errorBody)
                    500 -> ResponseState.HttpErrors.InternalServerError(errorBody)
                    502 -> ResponseState.HttpErrors.BadGateWay(errorBody)
                    301 -> ResponseState.HttpErrors.ResourceRemoved(errorBody)
                    302 -> ResponseState.HttpErrors.RemovedResourceFound(errorBody)
                    else -> ResponseState.Error(errorBody)
                }
            }
        } catch (e: IOException) {
            ResponseState.NetworkException(e.message!!)
        }
    }

}
