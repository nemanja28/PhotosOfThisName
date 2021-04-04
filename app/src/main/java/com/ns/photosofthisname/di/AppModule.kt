package com.ns.photosofthisname.di

import com.ns.photosofthisname.BuildConfig
import com.ns.photosofthisname.navigator.Navigator
import com.ns.photosofthisname.feature.photosearch.viewmodel.PhotoSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun appModules(forceMock: Boolean = false) = module {
    single { Navigator(get()) }

    loadKoinModules(listOf(dataSourceModule, networkDependency(baseUrl = BuildConfig.FLICKR_BASE_URL, apiKey = BuildConfig.FLICKR_API_KEY)))

    viewModel { PhotoSearchViewModel(dataSource = if(forceMock) get(named(MockRestDataService)) else get(named(RemoteRestDataService))) }

}