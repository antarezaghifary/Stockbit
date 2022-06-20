package com.stockbit.hiring.di

import com.stockbit.hiring.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository()
    }
}