package ru.andreikud.imagesearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.andreikud.imagesearchapp.databinding.GalleryFooterBinding

class GalleryLoadStateAdapter(
    val onRetryClick: () -> Unit
) : LoadStateAdapter<GalleryLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GalleryFooterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: GalleryFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                onRetryClick()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                val isLoading = loadState is LoadState.Loading
                pbLoading.isVisible = isLoading
                btnRetry.isVisible = !isLoading
                tvError.isVisible = !isLoading
            }
        }
    }
}