package br.com.devcapu.cdc

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Author::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: databaseBuilder(
                    context,
                    AppDatabase::class.java,
                   "cdc.db"
                ).build().also { instance = it }
            }
        }
    }
}