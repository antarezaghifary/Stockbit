package com.stockbit.hiring.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TotalTopDao {

    /**
     * Insert Data
     */
    @Insert
    fun add(data: totaltop?): Single<Long>

    /**
     * Delete All Data
     */

    @Query("Delete from totaltop")
    fun deleteAllData(): Single<Int>


}