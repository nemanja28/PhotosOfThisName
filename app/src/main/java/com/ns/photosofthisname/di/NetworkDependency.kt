package com.ns.photosofthisname.di

import com.google.gson.GsonBuilder
import com.ns.photosofthisname.datalayer.service.PhotoSearchService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun networkDependency(baseUrl:String, apiKey: String) = module {
    single {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor {
            val original = it.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build()

            val requestBuilder: Request.Builder = original.newBuilder().url(url)
            val request: Request = requestBuilder.build()
            it.proceed(request)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    factory{ get<Retrofit>().create(PhotoSearchService::class.java) }

}