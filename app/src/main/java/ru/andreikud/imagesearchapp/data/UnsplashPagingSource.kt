package ru.andreikud.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.andreikud.imagesearchapp.data.models.UnsplashObject
import ru.andreikud.imagesearchapp.data.network.UnsplashApi
import java.io.IOException
import java.lang.Exception

const val UNSPLASH_PAGING_START_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashObject>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashObject> {
        val position = params.key ?: UNSPLASH_PAGING_START_INDEX

        return try {
            val response = unsplashApi.getPhotos(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_PAGING_START_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashObject>): Int? = null
}