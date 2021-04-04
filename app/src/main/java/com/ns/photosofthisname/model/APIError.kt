package com.ns.photosofthisname.model

data class APIError(val code: Int, val messageStatus: String) : Error(messageStatus) {

    companion object {
        val UnknownError =
            APIError(-1, "Unknown error")
    }
}