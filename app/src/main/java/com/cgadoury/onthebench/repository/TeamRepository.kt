package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev

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
    fun insertAllTeams(teams: List<Standing>): Unit

    /**
     * Purpose - get current team roster - gets a teams current roster
     * @param teamAbbrev: The team roster to retrieve
     * @return RosterData?
     */
    suspend fun getCurrentTeamRoster(teamAbbrev: String): RosterData?
}