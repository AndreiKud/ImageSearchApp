package ru.andreikud.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)

        val adapter = GalleryAdapter()
        binding?.apply {
            rvPhotos.setHasFixedSize(true)
            rvPhotos.adapter = adapter.withLoadStateHeaderAndFooter(
                footer = GalleryLoadStateAdapter { adapter.retry() },
                header = GalleryLoadStateAdapter { adapter.retry() }
            )
        }

        viewModel.photos.observe(viewLifecycleOwner) { data ->
            adapter.submitData(viewLifecycleOwner.lifecycle, data)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}