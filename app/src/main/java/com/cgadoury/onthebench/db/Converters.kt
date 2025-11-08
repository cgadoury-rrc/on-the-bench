package com.cgadoury.onthebench.db

import androidx.room.TypeConverter
import com.cgadoury.onthebench.api.model.standing.PlaceName
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import com.cgadoury.onthebench.api.model.standing.TeamName

class Converters {
    @TypeConverter
    fun fromTeamName(value: TeamName?): String? = value?.default

    @TypeConverter
    fun toTeamName(value: String?): TeamName? =
        value?.let { TeamName(default = it, fr = null) }

    @TypeConverter
    fun fromTeamAbbrev(value: TeamAbbrev?): String? = value?.default

    @TypeConverter
    fun toTeamAbbrev(value: String?): TeamAbbrev? =
        value?.let { TeamAbbrev(default = it) }

    @TypeConverter
    fun fromPlaceName(value: PlaceName?): String? = value?.default

    @TypeConverter
    fun toPlaceName(value: String?): PlaceName? =
        value?.let { PlaceName(default = it, fr = null) }
}