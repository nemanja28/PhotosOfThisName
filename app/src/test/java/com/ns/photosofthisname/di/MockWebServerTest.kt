package com.ns.photosofthisname.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val mockWebServerTest = module {
    factory {
        MockWebServer()
    }
}