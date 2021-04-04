package com.ns.photosofthisname.feature.photosearch.viewmodel

import androidx.lifecycle.LiveData
import com.ns.photosofthisname.helper.SingleLiveEvent
import com.ns.photosofthisname.model.Photo

interface IPhotoSearchViewModel {

    val photos: LiveData<List<Photo>>
    val showLoadMoreProgress: SingleLiveEvent<Boolean>
    val showProgressBar: SingleLiveEvent<Boolean>
    val showErrorMessage: SingleLiveEvent<String>

    fun onSearchButtonClick(name: String)
    fun onContactSelected(contactName: String)
    fun onScrollToEndOfList()
}