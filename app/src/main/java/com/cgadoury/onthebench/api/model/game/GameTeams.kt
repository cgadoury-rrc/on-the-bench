package com.cgadoury.onthebench.api.model.game

import com.cgadoury.onthebench.api.model.standing.Standing

class GameTeams (
    val homeTeam: Standing? = null,
    val awayTeam: Standing? = null
)