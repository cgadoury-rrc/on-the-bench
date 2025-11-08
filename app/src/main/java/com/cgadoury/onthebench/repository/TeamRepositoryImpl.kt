package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.db.TeamDao

class TeamRepositoryImpl(
    private val nhlApiService: NhlApiService,
    private val teamDao: TeamDao
): TeamRepository {
    private var teams: List<Standing> = emptyList()

    override suspend fun getAllTeams(): List<Standing> {
        if (teamDao.getAllTeams().isEmpty()) {
            teams = nhlApiService.getCurrentStandings().body()?.standings?.filterNotNull() ?: emptyList()
            teamDao.insertAllTeams(teams = teams)
        } else {
            teams = teamDao.getAllTeams()
        }

        return teams
    }


    override fun insertAllTeams(teams: List<Standing>): Unit = teamDao.insertAllTeams(teams=teams)
}