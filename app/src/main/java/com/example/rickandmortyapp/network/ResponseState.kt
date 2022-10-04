package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.model.ErrorData

sealed class ResponseState {
    data class Success<T>(val data: T) : ResponseState()
    object NoContent : ResponseState()
    data class Error(val errorData: ErrorData?) : ResponseState()
    data class NetworkException(val error: String?) : ResponseState()
    object Unauthorized : ResponseState()
    sealed class HttpErrors(val errorData: ErrorData?) : ResponseState() {
        data class ResourceForbidden(val error: ErrorData?) : HttpErrors(error)
        data class ResourceNotFound(val error: ErrorData?) : HttpErrors(error)
        data class InternalServerError(val error: ErrorData?) : HttpErrors(error)
        data class BadGateWay(val error: ErrorData?) : HttpErrors(error)
        data class ResourceRemoved(val error: ErrorData?) : HttpErrors(error)
        data class RemovedResourceFound(val error: ErrorData?) : HttpErrors(error)
    }
}
