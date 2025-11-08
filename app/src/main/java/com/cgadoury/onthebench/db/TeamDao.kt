package com.cgadoury.onthebench.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cgadoury.onthebench.api.model.standing.Standing

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTeams(teams: List<Standing>)

    @Delete
    suspend fun delete(team: Standing)

    @Query("SELECT * FROM TEAMS")
    fun getAllTeams() : List<Standing>
}