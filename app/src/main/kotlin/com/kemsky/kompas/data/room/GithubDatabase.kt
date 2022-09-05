package com.kemsky.kompas.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kemsky.kompas.data.model.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao


    companion object {
        private var INSTANCE: GithubDatabase? = null
//        private const val DB_NAME = "dipay-fav-pokemon"

        fun getDatabase(context: Context): GithubDatabase {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Room.inMemoryDatabaseBuilder(context, GithubDatabase::class.java).build()
//                            Room.databaseBuilder(context, PokemonDatabase::class.java, DB_NAME)
                                //.allowMainThreadQueries() // Uncomment if you don't want to use RxJava or coroutines just yet (blocks UI thread)
//                                .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }

}