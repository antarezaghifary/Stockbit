package com.stockbit.hiring.di

import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createReactiveService
import com.stockbit.hiring.data.network.MyApi
import org.koin.dsl.module

val networkModule = module {
    single {
        createReactiveService(
            MyApi::class.java,
            createOkHttpClient(
                arrayOf(),
                null,
                null,
                true
            ),
            "https://min-api.cryptocompare.com/data/top/"
        )
    }
}