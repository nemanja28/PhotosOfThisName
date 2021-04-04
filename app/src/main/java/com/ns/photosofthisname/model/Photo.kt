package com.ns.photosofthisname.model

import com.google.gson.annotations.SerializedName
import com.ns.photosofthisname.BuildConfig

data class Photo(
    @SerializedName("id") val id: Long,
    @SerializedName("secret") private val secret: String,
    @SerializedName("server") val server: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("farm") val farm: Int,
    @SerializedName("title") val title: String
) {

    val imageUrlSmall: String
        get() = "${BuildConfig.FLICKR_IMAGE_BASE_URL}${server}/${id}_${secret}_m.jpg"

    val imageUrlMedium: String
        get() = "${BuildConfig.FLICKR_IMAGE_BASE_URL}${server}/${id}_${secret}_c.jpg"

}