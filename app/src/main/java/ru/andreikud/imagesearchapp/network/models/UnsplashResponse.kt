package ru.andreikud.imagesearchapp.network.models

import ru.andreikud.imagesearchapp.data.models.UnsplashObject

data class UnsplashResponse(
    val results: List<UnsplashObject>
)