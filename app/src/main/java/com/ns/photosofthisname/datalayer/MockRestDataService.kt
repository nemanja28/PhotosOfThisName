package com.ns.photosofthisname.datalayer

import com.google.gson.Gson
import com.ns.photosofthisname.model.APIError
import com.ns.photosofthisname.model.PhotosResponse

class MockRestDataService : IRestDataSource {

    override suspend fun searchPhotoByName(name: String, page: Int, perPage: Int): Result<PhotosResponse> {
        val jsonString = getJsonString(SUCCESS_RESPONSE_FILE) ?: return Result.failure(APIError.UnknownError)
        val response = Gson().fromJson(jsonString, PhotosResponse::class.java)
        return Result.success(response)
    }

    private fun getJsonString(path: String): String? {
        return this.javaClass.classLoader?.getResource(path)?.readText(Charsets.UTF_8)
    }

    companion object{
        const val SUCCESS_RESPONSE_FILE = "mock/success_response_search_photos.json"
    }
}
