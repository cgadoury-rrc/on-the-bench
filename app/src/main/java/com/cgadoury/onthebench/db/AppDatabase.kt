package com.cgadoury.onthebench.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cgadoury.onthebench.api.model.last_updated.LastUpdated
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.standing.Standing

/**
 * Purpose - app database - the application database, inherits from room database
 */
@TypeConverters(Converters::class)
@Database(
    entities = [Standing::class, LastUpdated::class, Player::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    /**
     * Purpose - team dao - the team data access object
     * @return TeamDao: The team data access object
     */
    abstract fun teamDao() : TeamDao

    /**
     * Purpose - last updated dao - the last updated data access object
     * @return The last updated data access object
     */
    abstract fun lastUpdatedDao(): LastUpdatedDao

    /**
     * Purpose - player dao - the player data access object
     */
    abstract fun playerDao(): PlayerDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        /**
         * Purpose - get instance - gets a singleton instance of app database
         * @param context: The context in which to access the database
         * @return AppDatabase: The application database object
         */
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