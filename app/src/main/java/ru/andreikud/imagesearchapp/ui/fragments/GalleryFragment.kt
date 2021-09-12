package ru.andreikud.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import ru.andreikud.imagesearchapp.R
import ru.andreikud.imagesearchapp.databinding.FragmentGalleryBinding
import ru.andreikud.imagesearchapp.ui.adapter.GalleryAdapter
import ru.andreikud.imagesearchapp.ui.adapter.GalleryLoadStateAdapter
import ru.andreikud.imagesearchapp.viewmodel.GalleryViewModel

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel: GalleryViewModel by viewModels()
    private var binding: FragmentGalleryBinding? = null
    private val adapter = GalleryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)

        setupRecycler()
        setupObservers()
        setHasOptionsMenu(true)
    }

    private fun setupRecycler() {
        binding?.apply {
            rvPhotos.setHasFixedSize(true)
            rvPhotos.adapter = adapter.withLoadStateHeaderAndFooter(
                footer = GalleryLoadStateAdapter { adapter.retry() },
                header = GalleryLoadStateAdapter { adapter.retry() }
            )
            btnRetry.setOnClickListener { adapter.retry() }

            adapter.addLoadStateListener { combinedLoadStates ->
                val refresh = combinedLoadStates.source.refresh
                pbLoading.isVisible = refresh is LoadState.Loading
                rvPhotos.isVisible = refresh is LoadState.NotLoading
                btnRetry.isVisible = refresh is LoadState.Error
                tvError.isVisible = refresh is LoadState.Error

                if (refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached
                    && adapter.itemCount < 1
                ) {
                    rvPhotos.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.photos.observe(viewLifecycleOwner) { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)

        val searchView = menu.findItem(R.id.miSearch).actionView
        if (searchView is SearchView) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { value ->
                        binding?.rvPhotos?.scrollToPosition(0)
                        viewModel.searchQuery.value = value
                        searchView.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}