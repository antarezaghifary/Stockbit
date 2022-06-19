package com.stockbit.hiring.data.repository

import com.stockbit.hiring.data.model.totaltoptiervolfull.DataItem
import com.stockbit.hiring.data.network.Api
import io.reactivex.Single

class UserRepository {

    fun totalTopTierVolFull(
        page: Int
    ): Single<List<DataItem>> {
        return Api.getApi().totalTopTierVolFull(
            page,
            10,
            "USD",
            "application/json",
            "Bearer 3ef3d845377d246e368c48ac40ba6a621f72962cd8626ed36edcd139f27db97a",
        ).map {
            it.data
        }
    }
}