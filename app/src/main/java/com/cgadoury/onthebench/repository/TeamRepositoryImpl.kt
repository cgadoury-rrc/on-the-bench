package com.cgadoury.onthebench.repository

import android.util.Log
import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.last_updated.LastUpdated
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.db.LastUpdatedDao
import com.cgadoury.onthebench.db.TeamDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Purpose - Team Repository Impl - implementations of methods found in Team Repository,
 * used to interact with the data layers of the application
 */
class TeamRepositoryImpl(
    private val nhlApiService: NhlApiService,
    private val teamDao: TeamDao,
    private val lastUpdatedDao: LastUpdatedDao
): TeamRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private var teams: List<Standing> = emptyList()

    /**
     * Purpose - get all teams - gets all current nhl teams
     * using an api call or db retrieval.
     * @return A list of teams
     */
    override suspend fun getAllTeams(): List<Standing> {
        val cachedTeams = withContext(Dispatchers.IO){ teamDao.getAllTeams() }
        val lastUpdated = withContext(Dispatchers.IO){ lastUpdatedDao.getLastUpdated() }

        val currentTime = System.currentTimeMillis()
        val isCacheExpired =
            lastUpdated?.id == null
                    || currentTime - lastUpdated.lastUpdated > 43200000L

        if (cachedTeams.isEmpty() || isCacheExpired) {
            teams = nhlApiService.getCurrentStandings().body()?.standings?.filterNotNull() ?: emptyList()
            withContext(Dispatchers.IO) {
                teamDao.insertAllTeams(teams = teams)
                lastUpdatedDao.insertLastUpdated(LastUpdated(1, currentTime))
            }

            Log.i("Data", "Using api")
        } else {
            teams = cachedTeams

            Log.i("Data", "Using db")
        }

        return teams
    }

    /**
     * Purpose - insert all teams - inserts all Nhl teams to the database
     * @param teams: The list of teams to insert
     * @return Unit
     */
    override suspend fun insertAllTeams(teams: List<Standing>): Unit =
        teamDao.insertAllTeams(teams=teams)

    /**
     * Purpose - get current team roster - gets a teams current roster
     * @param teamAbbrev: The team roster to retrieve
     * @return RosterData?
     */
    override suspend fun getCurrentTeamRoster(teamAbbrev: String): RosterData? {
        return nhlApiService.getCurrentRoster(teamAbbrev).body()
    }

    /**
     * Purpose - update is favourite team state - updates the is favourite state of a team
     * @param team: The team to update is favourite state
     * @return Unit
     */
    override suspend fun updateIsFavouriteTeamState(team: Standing): Unit {
        withContext(Dispatchers.IO) {
            teamDao.updateIsFavouriteTeamState(team = team)
        }
    }

    /**
     * Purpose - get favourite teams - gets favourite teams from the firestore db
     * @return List<Standing>: A list of favourite teams from firebase
     */
    override suspend fun getFavouriteTeams(): List<Standing> {
        return try {
            firestore.collection("teams")
                .get()
                .await()
                .toObjects<Standing>()
        } catch (e: Exception) {
            Log.e("Firebase", "Error getting favourite teams", e)
            emptyList()
        }
    }

    /**
     * Purpose - save favourite team - save a favourite team to the firestore db
     * @param team: The team to save
     * @return Unit
     */
    override suspend fun saveFavouriteTeam(team: Standing): Unit {
        firestore.collection("teams")
            .document(team.teamAbbrev.default)
            .set(team)
            .addOnSuccessListener {
                Log.d("Firebase", "Team saved")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error saving team", e)
            }
    }

    /**
     * Purpose - delete favourite team - delete a favourite team from the firestore db
     * @param teamAbbrev: The team abbreviation used to search for and delete a team
     * @return Unit
     */
    override suspend fun deleteFavouriteTeam(teamAbbrev: String): Unit {
        firestore.collection("teams")
            .document(teamAbbrev)
            .delete()
            .addOnSuccessListener {
                Log.d("Firebase", "Team deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Error deleting team", e)
            }
    }
}