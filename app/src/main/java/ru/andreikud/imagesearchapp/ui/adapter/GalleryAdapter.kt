package ru.andreikud.imagesearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.andreikud.imagesearchapp.R
import ru.andreikud.imagesearchapp.data.models.UnsplashObject
import ru.andreikud.imagesearchapp.databinding.UnsplashItemBinding

class GalleryAdapter :
    PagingDataAdapter<UnsplashObject, GalleryAdapter.UnsplashViewHolder>(UNSPLASH_OBJECT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolderBinding = UnsplashItemBinding.inflate(inflater, parent, false)
        return UnsplashViewHolder(viewHolderBinding)
    }

    override fun onBindViewHolder(holder: UnsplashViewHolder, position: Int) {
        val unsplashObject = getItem(position)
        unsplashObject?.let(holder::bind)
    }

    class UnsplashViewHolder(private val binding: UnsplashItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val ivPhoto = binding.ivPhoto
        private val tvUsername = binding.tvUsername

        fun bind(unsplashObject: UnsplashObject) {
            Glide.with(itemView)
                .load(unsplashObject.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(ivPhoto)
            tvUsername.text = unsplashObject.user.username
        }
    }

    companion object {
        val UNSPLASH_OBJECT_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashObject>() {
            override fun areItemsTheSame(oldItem: UnsplashObject, newItem: UnsplashObject) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashObject, newItem: UnsplashObject) =
                oldItem == newItem
        }
    }
}