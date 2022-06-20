package com.stockbit.hiring.util

import android.app.Application
import com.stockbit.hiring.di.databaseModule
import com.stockbit.hiring.di.networkModule
import com.stockbit.hiring.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule
                )
            )
        }
    }
}