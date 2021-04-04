package com.ns.photosofthisname.model

import com.google.gson.annotations.SerializedName


data class PhotoData(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("total") val total: String,
    @SerializedName("photo") val photo: List<Photo>
)
