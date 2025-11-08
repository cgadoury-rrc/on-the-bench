package com.cgadoury.onthebench.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cgadoury.onthebench.api.model.standing.Standing

@TypeConverters(Converters::class)
@Database(entities = [Standing::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun teamDao() : TeamDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    AppDatabase::class.java,
                    "OnTheBench"
              ).fallbackToDestructiveMigration().build()
              INSTANCE = instance
              instance
          }
        }
    }
}