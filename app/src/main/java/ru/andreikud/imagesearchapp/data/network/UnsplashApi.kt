package ru.andreikud.imagesearchapp.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.andreikud.imagesearchapp.BuildConfig
import ru.andreikud.imagesearchapp.data.network.models.UnsplashResponse

interface UnsplashApi {

    companion object {
        const val CLIENT_ID = BuildConfig.UNSPLASH_API_KEY
        const val BASE_URL = "https://api.unsplash.com/"
    }

    @Headers("Accept-Version: v1", "Authorization: Client ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse

}