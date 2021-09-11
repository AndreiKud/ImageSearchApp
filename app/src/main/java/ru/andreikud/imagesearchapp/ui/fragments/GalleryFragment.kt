package ru.andreikud.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.andreikud.imagesearchapp.R
import ru.andreikud.imagesearchapp.databinding.FragmentGalleryBinding
import ru.andreikud.imagesearchapp.ui.adapter.GalleryAdapter
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
            rvPhotos.adapter = adapter
            rvPhotos.setHasFixedSize(true)
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