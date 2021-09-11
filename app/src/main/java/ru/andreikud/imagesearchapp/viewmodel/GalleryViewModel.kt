package ru.andreikud.imagesearchapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.switchMap
import ru.andreikud.imagesearchapp.data.UnsplashRepository
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) : ViewModel() {

    val searchQuery = MutableLiveData("cats")

    val photos = searchQuery.switchMap { currentQuery ->
        unsplashRepository.getSearchResults(currentQuery).cachedIn(viewModelScope)
    }

}