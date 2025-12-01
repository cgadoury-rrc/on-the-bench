package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>: A list of the top nhl players in terms of points
     */
    suspend fun getTopPlayers(): List<Point>

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?: The player to be updated
     */
    suspend fun getPlayerById(playerId: Int): Player?

    /**
     * Purpose - update is favourite state - updates the is favourite state of a player
     * using the player dao
     * @param player: The player to update
     * @return Unit
     */
    suspend fun updateIsFavouritePlayerState(player: Player): Unit

    /**
     * Purpose - get favourite players - gets favourite players from the firestore db
     * @return List<Player>: A list of favourite nhl players
     */
    suspend fun getFavouritePlayers(): List<Player>

    /**
     * Purpose - save a favourite player to the firestore db
     * @param player: The player to save
     * @return Unit
     */
    suspend fun saveFavouritePlayer(player: Player): Unit

    /**
     * Purpose - delete favourite player - delete a favourite player from the database
     * @param playerId: The player id used to search for and delete a player
     * @return Unit
     */
    suspend fun deleteFavouritePlayer(playerId: String): Unit
}