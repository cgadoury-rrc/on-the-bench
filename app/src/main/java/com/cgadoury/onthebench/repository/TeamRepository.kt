package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing

interface TeamRepository {

    /**
     * Purpose - get all teams - gets all current nhl teams
     * using an api call or db retrieval.
     * @return A list of teams
     */
    suspend fun getAllTeams(): List<Standing>

    /**
     * Purpose - insert all teams - inserts all Nhl teams to the database
     * @param teams: The list of teams to insert
     * @return Unit
     */
    suspend fun insertAllTeams(teams: List<Standing>): Unit

    /**
     * Purpose - get current team roster - gets a teams current roster
     * @param teamAbbrev: The team roster to retrieve
     * @return RosterData?
     */
    suspend fun getCurrentTeamRoster(teamAbbrev: String): RosterData?

    /**
     * Purpose - update is favourite team state - updates the is favourite state of a team
     * @param team: The team to update is favourite state
     * @return Unit
     */
    suspend fun updateIsFavouriteTeamState(team: Standing): Unit

    /**
     * Purpose - get favourite teams - gets favourite teams from the firestore db
     * @return Unit
     */
    suspend fun getFavouriteTeams(): List<Standing>

    /**
     * Purpose - save favourite team - save a favourite team to the firestore db
     * @param team: The team to save
     * @return Unit
     */
    suspend fun saveFavouriteTeam(team: Standing): Unit

    /**
     * Purpose - delete favourite team - delete a favourite team from the firestore db
     * @param teamAbbrev: The team abbreviation used to search for and delete a team
     * @return Unit
     */
    suspend fun deleteFavouriteTeam(teamAbbrev: String): Unit
}