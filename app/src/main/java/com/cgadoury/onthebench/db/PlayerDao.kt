package com.cgadoury.onthebench.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cgadoury.onthebench.api.model.player.Player

/**
 * Purpose - player dao - handles data access with the players table
 */
@Dao
interface PlayerDao {

    /**
     * Purpose - insert player - insert a single player into the database
     * @param player: The player to insert
     * @return Unit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player): Unit

    /**
     * Purpose - delete - delete a player from the database
     * @param player: The player to delete
     * @return Unit
     */
    @Delete
    suspend fun delete(player: Player): Unit

    /**
     * Purpose - get player by id - get a player from the database
     * using their player id
     * @param playerId: The player id to search for
     * @return Player: The player with the matching player id
     */
    @Query("SELECT * FROM players WHERE playerId = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?
}