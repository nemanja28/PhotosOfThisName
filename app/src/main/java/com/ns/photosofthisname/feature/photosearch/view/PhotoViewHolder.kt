package com.ns.photosofthisname.feature.photosearch.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ns.photosofthisname.databinding.ViewHolderPhotoBinding
import com.ns.photosofthisname.model.Photo

class PhotoViewHolder(
    itemView: View,
    private val onItemClicked: (imageUrl: String) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val binding = ViewHolderPhotoBinding.bind(itemView)

    fun setData(photo: Photo) {
        itemView.setOnClickListener { onItemClicked(photo.imageUrlMedium) }
        binding.titleTextView.text = photo.title
        binding.ownerTextView.text = photo.owner
        Glide.with(itemView.context).load(photo.imageUrlSmall).into(binding.photoImageView)
    }

}