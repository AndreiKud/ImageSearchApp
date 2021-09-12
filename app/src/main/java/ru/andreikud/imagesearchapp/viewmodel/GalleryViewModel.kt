package ru.andreikud.imagesearchapp.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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
    private val unsplashRepository: UnsplashRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData(QUERY_KEY, DEFAULT_QUERY)

    val photos = searchQuery.switchMap { currentQuery ->
        unsplashRepository.getSearchResults(currentQuery).cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
        private const val QUERY_KEY = "query_key"
    }
}