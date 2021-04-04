package com.ns.photosofthisname

import android.app.Application
import com.ns.photosofthisname.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class PhotosOfThisNameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotosOfThisNameApplication)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = listOf(appModules())

}