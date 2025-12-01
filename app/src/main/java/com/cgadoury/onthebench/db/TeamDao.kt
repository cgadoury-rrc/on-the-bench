package com.cgadoury.onthebench.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cgadoury.onthebench.api.model.standing.Standing

/**
 * Purpose - team dao - the team data access object
 */
@Dao
interface TeamDao {

    /**
     * Purpose - insert all teams - insert all teams into the database
     * @param teams: A list of teams to insert
     * @return Unit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTeams(teams: List<Standing>): Unit

    /**
     * Purpose - delete - delete a team from the database
     * @param team: The team to delete
     * @return Unit
     */
    @Delete
    suspend fun delete(team: Standing): Unit

    /**
     * Purpose - get all teams - get all teams from the database
     * @return List<Standing>: All teams
     */
    @Query("SELECT * FROM teams")
    suspend fun getAllTeams() : List<Standing>

    /**
     * Purpose - get team by abbreviation - get a teams using it's team abbreviation
     * @param abbrev: The abbreviation to search for
     * @return Standing: The team matching the team abbreviation
     */
    @Query("SELECT * FROM teams WHERE teamAbbrev = :abbrev")
    fun getTeamByAbbreviation(abbrev: String): Standing

    /**
     * Purpose - update is favourite team state - updates the is favourite state of a team
     * @param team: The team to favourite
     * @return Unit
     */
    @Update
    fun updateIsFavouriteTeamState(team: Standing): Unit
}