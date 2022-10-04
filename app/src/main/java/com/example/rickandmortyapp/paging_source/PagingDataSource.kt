package com.example.rickandmortyapp.paging_source

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.model.character_info.CharacterData
import com.example.rickandmortyapp.network.ApiService


private const val STARTING_PAGE_INDEX = 1

class PagingDataSource(private val service: ApiService) : PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getCharacter(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results

            var nextPageNumber: Int? = null
            if (pagedResponse?.info?.next != null) {
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
                //Log.i("PageNumber", "Previous Key =${pagedResponse.info.prev}\nCurrent Page = $pageNumber\nNext Key =${uri}\n----------")
            }
            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            Log.e("Exception in PagingSource", "$e")
            LoadResult.Error(e)
        }
    }

}