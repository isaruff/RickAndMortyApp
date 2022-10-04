package com.example.rickandmortyapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.episode_info.Episode
import com.example.rickandmortyapp.model.location_info.LocationInfo
import com.example.rickandmortyapp.network.ApiInitHelper
import com.example.rickandmortyapp.network.ResponseState
import com.example.rickandmortyapp.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel: ViewModel() {

    private var _episodeLiveData : MutableLiveData<Episode> = MutableLiveData()
    val episodeLiveData: LiveData<Episode> = _episodeLiveData

    private var _locationLiveData : MutableLiveData<LocationInfo> = MutableLiveData()
    val locationLiveData: LiveData<LocationInfo> = _locationLiveData


    private val repository by lazy {
        NetworkRepository(ApiInitHelper.api)
    }


    fun getEpisodeLiveData(url: String){
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getEpisode(url)) {
                is ResponseState.Success<*> -> {
                    _episodeLiveData.postValue(response.data as Episode)
                }
                else -> {
                    Log.e("DetailsLiveDataError","Episode info error")
                }
            }

        }
    }

    fun getLocationLiveData(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.getLocation(url)) {
                is ResponseState.Success<*> ->{
                    _locationLiveData.postValue(response.data as LocationInfo)
                }
                else ->{
                    Log.e("DetailsLiveDataError","Location info error")
                }
            }
        }
    }
}