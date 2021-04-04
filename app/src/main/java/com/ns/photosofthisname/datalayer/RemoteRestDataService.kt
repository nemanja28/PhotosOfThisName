package com.ns.photosofthisname.datalayer

import com.ns.photosofthisname.datalayer.service.PhotoSearchService
import com.ns.photosofthisname.model.APIError
import com.ns.photosofthisname.model.PhotosResponse
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.awaitResponse

@KoinApiExtension
class RemoteRestDataService : KoinComponent, IRestDataSource {

    private val service: PhotoSearchService by inject()

    override suspend fun searchPhotoByName(name: String, page: Int, perPage: Int): Result<PhotosResponse> {
        val response = service.searchPhotos(name, page, perPage).awaitResponse()
        return if (response.isSuccessful) {
            response.body()?.let { Result.success(it) } ?: Result.failure(APIError.UnknownError)
        } else {
            Result.failure(APIError(response.code(), response.message()))
        }
    }
}