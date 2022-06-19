package com.stockbit.hiring.data.network

import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createReactiveService

object Api {
    fun getApi(): MyApi {
        return createReactiveService(
            MyApi::class.java,
            createOkHttpClient(
                //arrayOf()
                interceptors = arrayOf(),
                authenticator = null,
                showDebugLog = true,
                pinner = null
            ),
            "https://min-api.cryptocompare.com/data/top/"
        )
    }
}