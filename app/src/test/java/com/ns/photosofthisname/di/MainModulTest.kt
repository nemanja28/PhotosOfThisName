package com.ns.photosofthisname.di

fun configureTestAppComponent(baseApi: String) = listOf(mockWebServerTest, configureNetworkModuleForTest(baseApi), dataSourceModule)

