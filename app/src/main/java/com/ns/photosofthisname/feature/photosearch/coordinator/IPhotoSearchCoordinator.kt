package com.ns.photosofthisname.feature.photosearch.coordinator

interface IPhotoSearchCoordinator {
    fun showImage(imageUrl: String)
    fun openContactPicker(onContactSelected: (contactName: String)-> Unit)
}