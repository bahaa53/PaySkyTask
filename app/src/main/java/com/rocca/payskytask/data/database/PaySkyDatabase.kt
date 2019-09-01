package com.rocca.payskytask.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rocca.payskytask.data.database.dao.GithubRepositoriesDao
import com.rocca.payskytask.domain.enitities.jackwhartonRepositoriesResponse.GithubRepositoriesItem


@Database(
    entities = [GithubRepositoriesItem::class],
    version = 4
)
abstract class PaySkyDatabase : RoomDatabase() {

    abstract fun GithubRepositoriesDao(): GithubRepositoriesDao

    companion object {
        @Volatile private var instance: PaySkyDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }


        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PaySkyDatabase::class.java, "paySkyDatabase.db"
            ).fallbackToDestructiveMigration()
                .build()

    }
}