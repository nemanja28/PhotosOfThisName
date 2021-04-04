package com.ns.photosofthisname.di

import com.google.gson.GsonBuilder
import com.ns.photosofthisname.datalayer.service.PhotoSearchService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun configureNetworkModuleForTest(baseApi: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    factory { get<Retrofit>().create(PhotoSearchService::class.java) }
}