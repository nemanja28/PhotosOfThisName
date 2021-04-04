package com.ns.photosofthisname.datalayer.service

import com.ns.photosofthisname.model.PhotosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoSearchService {

    @GET("?method=flickr.photos.search")
    fun searchPhotos(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): Call<PhotosResponse>
}