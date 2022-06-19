package com.stockbit.hiring.data.network

import com.stockbit.hiring.data.model.totaltoptiervolfull.TotalTopTierResponse
import io.reactivex.Single
import retrofit2.http.*

interface MyApi {
    /**
     *
     */
    @GET("totaltoptiervolfull")
    fun totalTopTierVolFull(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("tsym") tsym: String,
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): Single<TotalTopTierResponse>

}
