package com.ns.photosofthisname.di

import com.ns.photosofthisname.datalayer.IRestDataSource
import org.koin.core.component.KoinApiExtension
import org.koin.core.qualifier.named
import org.koin.dsl.module


@OptIn(KoinApiExtension::class)
val dataSourceModule = module {

    single<IRestDataSource>(named(RemoteRestDataService)) { com.ns.photosofthisname.datalayer.RemoteRestDataService() }
    single<IRestDataSource>(named(MockRestDataService)) { com.ns.photosofthisname.datalayer.MockRestDataService() }

}

const val RemoteRestDataService = "RemoteRestDataService"
const val MockRestDataService = "MockRestDataService"




