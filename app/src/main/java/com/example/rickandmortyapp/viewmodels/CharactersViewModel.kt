package com.example.rickandmortyapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmortyapp.network.ApiInitHelper
import com.example.rickandmortyapp.repository.NetworkRepository


class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext get() = getApplication<Application>().applicationContext

    private val repository by lazy {
        NetworkRepository(ApiInitHelper.api)
    }

    val characterPageData = repository.getCharacterData().cachedIn(viewModelScope)


}