package com.ns.photosofthisname.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(@SerializedName("photos") val photos: PhotoData)
