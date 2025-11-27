package com.cgadoury.onthebench.db

import androidx.room.TypeConverter
import com.cgadoury.onthebench.api.model.standing.PlaceName
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import com.cgadoury.onthebench.api.model.standing.TeamName

/**
 * Purpose - converters - convert to and from complex data types when
 * interacting with room database
 * If Room does not know how to handle a type, it will look for and
 * automatically implement the type converters registered with @TypeConverters
 * in AppDatabase (or on specific entities/DAOs)
 */
class Converters {

    /**
     * Purpose - from team name - converts a value from team name to
     * it's default value as a string
     * @param value: The team name to convert
     * @return String: The team name's default value as a string
     */
    @TypeConverter
    fun fromTeamName(value: TeamName?): String? = value?.default

    /**
     * Purpose - to team name - converts a value from a string to
     * a team name
     * @param value: The string value string to convert
     * @return TeamName: The team name
     */
    @TypeConverter
    fun toTeamName(value: String?): TeamName? =
        value?.let { TeamName(default = it, fr = null) }

    /**
     * Purpose - from team abbrev - converts a value from team abbrev to
     * it's default value as a string
     * @param value: The team abbrev to convert
     * @return String: The team abbrev's default value as a string
     */
    @TypeConverter
    fun fromTeamAbbrev(value: TeamAbbrev?): String? = value?.default

    /**
     * Purpose - to team abbrev - converts a value from a string to
     * a team abbrev
     * @param value: The string value to convert
     * @return TeamAbbrev: The team abbrev
     */
    @TypeConverter
    fun toTeamAbbrev(value: String?): TeamAbbrev? =
        value?.let { TeamAbbrev(default = it) }

    /**
     * Purpose - from place name - converts a value from a place name to
     * it's default value as a string
     * @param value: The place name to convert
     * @return String: The place name's default value as a string
     */
    @TypeConverter
    fun fromPlaceName(value: PlaceName?): String? = value?.default

    /**
     * Purpose - to place name - converts a value from a string to
     * a place name
     * @param value: The string value to convert
     * @return PlaceName: The place name
     */
    @TypeConverter
    fun toPlaceName(value: String?): PlaceName? =
        value?.let { PlaceName(default = it, fr = null) }
}