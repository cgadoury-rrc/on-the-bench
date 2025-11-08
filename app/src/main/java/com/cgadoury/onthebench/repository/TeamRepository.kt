package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.standing.Standing

interface TeamRepository {
    suspend fun getAllTeams(): List<Standing>
    fun insertAllTeams(teams: List<Standing>): Unit
}