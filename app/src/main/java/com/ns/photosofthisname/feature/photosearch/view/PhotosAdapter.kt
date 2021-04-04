package com.ns.photosofthisname.feature.photosearch.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ns.photosofthisname.R
import com.ns.photosofthisname.model.Photo

class PhotosAdapter(
    private val onItemClicked: (imageUrl: String) -> Unit,
    private val models: MutableList<Photo> = mutableListOf()
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_photo, parent, false), onItemClicked)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setData(models[position])
    }

    fun addData(data: List<Photo>) {
        models.addAll(data)
        notifyDataSetChanged()
    }
}