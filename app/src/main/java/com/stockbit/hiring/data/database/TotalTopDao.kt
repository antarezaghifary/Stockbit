package com.stockbit.hiring.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TotalTopDao {

    /**
     * Insert Data
     */
    @Insert
    fun add(data: TotalTop?): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(data: List<TotalTop>): Single<List<Long>>

    @Query("Select * from totaltop where page = :page")
    fun getAll(page: Int): Single<List<TotalTop>>

    /**
     * Delete All Data
     */

    @Query("Delete from TotalTop")
    fun deleteAllData(): Single<Int>


}