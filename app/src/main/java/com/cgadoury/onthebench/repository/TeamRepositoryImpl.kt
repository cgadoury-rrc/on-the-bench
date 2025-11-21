package com.cgadoury.onthebench.repository

import android.util.Log
import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import com.cgadoury.onthebench.db.TeamDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Purpose - Team Repository Impl - implementations of methods found in Team Repository,
 * used to interact with the data layers of the application
 */
class TeamRepositoryImpl(
    private val nhlApiService: NhlApiService,
    private val teamDao: TeamDao
): TeamRepository {
    private var teams: List<Standing> = emptyList()

    /**
     * Purpose - get all teams - gets all current nhl teams
     * using an api call or db retrieval.
     * @return A list of teams
     */
    override suspend fun getAllTeams(): List<Standing> {
        val cachedTeams = withContext(Dispatchers.IO){ teamDao.getAllTeams() }

        if (cachedTeams.isEmpty()) {
            teams = nhlApiService.getCurrentStandings().body()?.standings?.filterNotNull() ?: emptyList()
            withContext(Dispatchers.IO) { teamDao.insertAllTeams(teams = teams) }

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
    override fun insertAllTeams(teams: List<Standing>): Unit =
        teamDao.insertAllTeams(teams=teams)

    /**
     * Purpose - get teams by abbreviation - gets an nhl team using its abbreviation
     * i.e. WPG
     * @param teamAbbrev: The abbreviation to search for
     * @return Unit
     */
    override suspend fun getTeamByAbbreviation(teamAbbrev: String): Standing =
        withContext(Dispatchers.IO) { teamDao.getTeamByAbbreviation(teamAbbrev) }

    /**
     * Purpose - get current team roster - gets a teams current roster
     * @param teamAbbrev: The team roster to retrieve
     * @return RosterData?
     */
    override suspend fun getCurrentTeamRoster(teamAbbrev: String): RosterData? {
        return nhlApiService.getCurrentRoster(teamAbbrev).body()
    }
}