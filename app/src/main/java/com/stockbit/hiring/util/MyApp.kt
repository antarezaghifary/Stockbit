package com.stockbit.hiring.util

import android.app.Application
import android.content.Context
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

class MyApp : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
        RxJavaPlugins.setErrorHandler { throwable: Throwable? -> }
        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                return@setErrorHandler
            }
        }
    }

    companion object {
        var instance: MyApp? = null
            private set

        val context: Context?
            get() = instance

    }
}