package com.stockbit.hiring.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TotalTop::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun totalTopDao(): TotalTopDao

    companion object {
        private var instance: com.stockbit.hiring.data.database.Database? = null

        fun getInstance(ctx: Context): com.stockbit.hiring.data.database.Database {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    com.stockbit.hiring.data.database.Database::class.java,
                    "total_top.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!

        }
    }
}