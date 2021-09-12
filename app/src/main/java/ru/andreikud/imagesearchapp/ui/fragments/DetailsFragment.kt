package ru.andreikud.imagesearchapp.ui.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.andreikud.imagesearchapp.R
import ru.andreikud.imagesearchapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)

        with(binding) {
            val photo = args.photo
            Glide.with(this@DetailsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoading.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoading.isVisible = false
                        tvDescription.isVisible = true
                        tvAuthor.isVisible =
                            photo.description != null && photo.description.isNotEmpty()
                        return false
                    }
                })
                .into(ivImage)

            photo.description?.let(tvDescription::setText)

            tvAuthor.text = "Photo by ${photo.user.name} on Unsplash"
            tvAuthor.paint.isUnderlineText = true
            tvAuthor.setOnClickListener {
                val uri = Uri.parse(photo.user.attributionUrl)
                Intent(Intent.ACTION_VIEW, uri).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

}