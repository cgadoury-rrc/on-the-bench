package com.cgadoury.onthebench.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cgadoury.onthebench.api.model.last_updated.LastUpdated

/**
 * Purpose - last updated dao - handles database interactions with the last updated table
 */
@Dao
interface LastUpdatedDao {

    /**
     * Purpose - insert last updated - insert the last time that the database has been updated
     * @param lastUpdated: The last updated time
     * @return Unit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLastUpdated(lastUpdated: LastUpdated): Unit

    /**
     * Purpose - delete - delete a record from last updated
     * @param lastUpdated: The last updated time to delete
     */
    @Delete
    suspend fun delete(lastUpdated: LastUpdated): Unit

    /**
     * Purpose - get last updated - get the last updated record from the database
     * @return LastUpdated: The last updated record in the database
     */
    @Query("SELECT * FROM last_updated WHERE id = 1")
    suspend fun getLastUpdated(): LastUpdated?
}