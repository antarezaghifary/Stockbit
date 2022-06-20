package com.stockbit.hiring.di

import com.stockbit.hiring.data.database.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Database.getInstance(androidContext())
    }
    single {
        get<Database>().totalTopDao()
    }
}