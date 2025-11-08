package com.cgadoury.onthebench.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTeams(teams: List<Standing>)

    @Delete
    suspend fun delete(team: Standing)

    @Query("SELECT * FROM teams")
    fun getAllTeams() : List<Standing>

    @Query("SELECT * FROM teams WHERE teamAbbrev = :abbrev")
    fun getTeamByAbbreviation(abbrev: String): Standing
}