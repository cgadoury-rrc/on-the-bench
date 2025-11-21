package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev

interface TeamRepository {
    suspend fun getAllTeams(): List<Standing>
    fun insertAllTeams(teams: List<Standing>): Unit
    suspend fun getTeamByAbbreviation(teamAbbrev: String): Standing
    suspend fun getCurrentTeamRoster(teamAbbrev: String): RosterData?
}