package ru.andreikud.imagesearchapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashObject(
    val id: String,
    val description: String,
    val user: UnsplashObject.User,
    val urls: UnsplashObject.Urls,
) : Parcelable {

    @Parcelize
    data class User(
        val name: String,
        val username: String,
    ) : Parcelable {
        val attributionUrl
            get() = "https://unsplash.com/$username?utm_source=ImageSearchAndroidApp&utm_mediate=referral"
    }

    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

}