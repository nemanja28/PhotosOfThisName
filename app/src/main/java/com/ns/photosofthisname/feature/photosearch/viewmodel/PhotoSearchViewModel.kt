package com.ns.photosofthisname.feature.photosearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ns.photosofthisname.datalayer.IRestDataSource
import com.ns.photosofthisname.helper.SingleLiveEvent
import com.ns.photosofthisname.model.Photo
import kotlinx.coroutines.launch

class PhotoSearchViewModel(private val dataSource: IRestDataSource) : ViewModel(), IPhotoSearchViewModel {


    lateinit var searchedName: String
    private var nextPage = 1

    override val photos = MutableLiveData<List<Photo>>()
    override val showLoadMoreProgress: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val showProgressBar: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()

    override fun onSearchButtonClick(name: String) {
        if (name.isBlank()) return
        showProgressBar(true)
        searchedName = name
        getPhotos() { showProgressBar(false) }
    }

    override fun onScrollToEndOfList() {
        showLoadMoreProgress(true)
        getPhotos() { showLoadMoreProgress(false) }
    }

    override fun onContactSelected(contactName: String){
        if (contactName.isBlank()) return
        showProgressBar(true)
        searchedName = contactName
        getPhotos() { showProgressBar(false) }
    }

    private fun getPhotos(onCompleted: () -> Unit) {
        viewModelScope.launch {
            dataSource.searchPhotoByName(searchedName, nextPage).onSuccess {
                nextPage++
                photos.value = it.photos.photo
                onCompleted()
            }.onFailure {
                showErrorMessage.value = it.message
                onCompleted()
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        showProgressBar.value = show
    }

    private fun showLoadMoreProgress(show: Boolean) {
        showLoadMoreProgress.value = show
    }
}