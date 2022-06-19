package com.stockbit.hiring.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class totaltop(
    val name: String?,
    val fullname: String?,
    val price: String?,
    val changeHouse: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
