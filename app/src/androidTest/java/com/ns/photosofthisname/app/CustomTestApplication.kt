package com.ns.photosofthisname.app

import com.ns.photosofthisname.PhotosOfThisNameApplication
import org.koin.core.module.Module

class CustomTestApplication: PhotosOfThisNameApplication() {
    override fun provideDependency()= listOf<Module>()
}