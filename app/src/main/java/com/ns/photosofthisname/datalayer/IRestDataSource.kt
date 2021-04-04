package com.ns.photosofthisname.datalayer

import com.ns.photosofthisname.model.PhotosResponse

interface IRestDataSource {
    suspend fun searchPhotoByName(name: String, page: Int = 1, perPage: Int = 20): Result<PhotosResponse>
}