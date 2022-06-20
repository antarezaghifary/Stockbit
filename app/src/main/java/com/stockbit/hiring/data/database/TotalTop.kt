package com.stockbit.hiring.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TotalTop(
    val name: String?,
    val fullname: String?,
    val price: String?,
    val changeHouse: String?,
    val page: Int,
    @PrimaryKey(autoGenerate = false) val id: String
)
