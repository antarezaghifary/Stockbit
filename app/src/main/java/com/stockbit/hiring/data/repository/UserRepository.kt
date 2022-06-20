package com.stockbit.hiring.data.repository

import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.stockbit.hiring.data.network.MyApi
import io.reactivex.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository : KoinComponent {
    private val api: MyApi by inject()

    fun totalTopTierVolFull(
        page: Int
    ): Single<List<DataItem>> {
        return api.totalTopTierVolFull(
            page,
            50,
            "USD",
            "application/json",
            "Bearer 3ef3d845377d246e368c48ac40ba6a621f72962cd8626ed36edcd139f27db97a",
        ).map {
            it.data
        }
    }
}