package com.cgadoury.onthebench.repository

import android.util.Log
import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.db.PlayerDao
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

/**
 * Purpose - player repository impl - the player repository implementation
 * @param nhlApiService: The service to communicate with the nhl api
 */
class PlayerRepositoryImpl(
    private val nhlApiService: NhlApiService,
    private val playerDao: PlayerDao
): PlayerRepository {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>: A list of the top nhl players in terms of points
     */
    override suspend fun getTopPlayers(): List<Point> {
        return nhlApiService
            .getTop50SkaterPoints().body()?.points?.filterNotNull() ?: emptyList()
    }

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?: The player to be updated
     */
    override suspend fun getPlayerById(playerId: Int): Player? {
        var player = withContext(Dispatchers.IO){
            playerDao.getPlayerById(playerId = playerId)
        }
        val currentTime = System.currentTimeMillis()
        val isPlayerExpired =
            player?.lastUpdated == null
                    || currentTime - player.lastUpdated > 14400000L

        if (player == null || isPlayerExpired) {
            try {
                player = nhlApiService.getPlayerById(playerId).body()
                withContext(Dispatchers.IO){
                    playerDao.insertPlayer(player = player!!)
                    Log.i("Data", "Saving player to db")
                }
            } catch (e: Exception) {
                Log.i("Data", "Could not retrieve player data", e)
            }
        } else {
            Log.i("Data", "Player found in database")
        }

        return player
    }

    /**
     * Purpose - update is favourite state - updates the is favourite state of a player
     * using the player dao
     * @param player: The player to update
     * @return Unit
     */
    override suspend fun updateIsFavouritePlayerState(player: Player): Unit {
        withContext(Dispatchers.IO) {
            playerDao.updateIsFavouriteState(player)
        }
    }

    /**
     * Purpose - get favourite players - gets favourite players from the firestore db
     * @return List<Player>: A list of favourite nhl players
     */
    override suspend fun getFavouritePlayers(): List<Player> {
        return try {
            firestore.collection("players")
                .get()
                .await()
                .toObjects<Player>()
        } catch (e: Exception) {
            Log.e("Firebase", "Error getting favourite players", e)
            emptyList()
        }
    }

    /**
     * Purpose - save a favourite player to the firestore db
     * @param player: The player to save
     * @return Unit
     */
    override suspend fun saveFavouritePlayer(player: Player): Unit {
        firestore.collection("players")
            .document(player.playerId.toString())
            .set(player)
            .addOnSuccessListener {
                Log.d("Firebase", "Player saved")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error saving player", e)
            }
    }

    /**
     * Purpose - delete favourite player - delete a favourite player from the database
     * @param playerId: The player id used to search for and delete a player
     * @return Unit
     */
    override suspend fun deleteFavouritePlayer(playerId: String): Unit {
        firestore.collection("players")
            .document(playerId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase", "Player deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Error deleting player", e)
            }
    }

    /**
     * Purpose - does player exist - checks the firestore db to see if a player exists
     * @param playerId: The player id to search for
     * @param collection: The reference to the firestore collection
     * @return Boolean: True if the player exists in the collection; otherwise false
     */
    private suspend fun doesPlayerExist(
        playerId: Int,
        collection: CollectionReference
    ): Boolean {
        val querySnapshot = collection.whereEqualTo("playerId", playerId).get().await()
        return !querySnapshot.isEmpty
    }
}