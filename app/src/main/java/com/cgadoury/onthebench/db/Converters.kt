package com.cgadoury.onthebench.db

import androidx.room.TypeConverter
import com.cgadoury.onthebench.api.model.player.FeaturedStats
import com.cgadoury.onthebench.api.model.player.SeasonTotal
import com.cgadoury.onthebench.api.model.player.BirthCity
import com.cgadoury.onthebench.api.model.player.BirthStateProvince
import com.cgadoury.onthebench.api.model.player.FirstNameX
import com.cgadoury.onthebench.api.model.player.FullTeamName
import com.cgadoury.onthebench.api.model.player.LastNameX
import com.cgadoury.onthebench.api.model.player.TeamCommonNameX
import com.cgadoury.onthebench.api.model.standing.PlaceName
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import com.cgadoury.onthebench.api.model.standing.TeamName
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Purpose - converters - convert to and from complex data types when
 * interacting with room database
 * If Room does not know how to handle a type, it will look for and
 * automatically implement the type converters registered with @TypeConverters
 * in AppDatabase (or on specific entities/DAOs)
 */
class Converters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val seasonTotalListType = Types.newParameterizedType(List::class.java, SeasonTotal::class.java)
    private val seasonTotalListAdapter = moshi.adapter<List<SeasonTotal>>(seasonTotalListType)
    private val featuredStatsAdapter = moshi.adapter(FeaturedStats::class.java)

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
        value?.let { TeamName(default = it, fr = "") }

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

    /**
     * Purpose - from birth city - converts a value from a birth city to
     * it's default value as a string
     * @param value: The birth city to convert
     * @return String: The birth city's default value as a string
     */
    @TypeConverter
    fun fromBirthCity(value: BirthCity?): String? = value?.default

    /**
     * Purpose - to birth city - converts a value from a string to
     * a birth city
     * @param value: The string to convert
     * @return BirthCity: The birth city
     */
    @TypeConverter
    fun toBirthCity(value: String?): BirthCity? =
        value?.let { BirthCity(default = it) }

    /**
     * Purpose - from birth state province - converts a value from a birth
     * state province to it's default value as a string
     * @param value: The birth state province to convert
     * @return String: The birth state province's default value as a string
     */
    @TypeConverter
    fun fromBirthStateProvince(value: BirthStateProvince?): String? = value?.default

    /**
     * Purpose - to birth state province - converts a value from a string to
     * a birth state province
     * @param value: The string to convert
     * @return BirthStateProvince: The birth state province
     */
    @TypeConverter
    fun toBirthStateProvince(value: String?): BirthStateProvince? =
        value?.let { BirthStateProvince(default = it) }

    /**
     * Purpose - from season total list - converts a value from a list of
     * season totals to a JSON string for database storage
     * @param value: The list of season totals
     * @return String: The list of season totals as a string
     */
    @TypeConverter
    fun fromSeasonTotalList(value: List<SeasonTotal>?): String? =
        value?.let { seasonTotalListAdapter.toJson(it) }

    /**
     * Purpose - to season total list - converts a JSON string from the database
     * to a list of season totals
     * @param value: The JSON string of season totals
     * @return List<SeasonTotal>: A list of season total objects
     */
    @TypeConverter
    fun toSeasonTotalList(value: String?): List<SeasonTotal>? =
        value?.let { seasonTotalListAdapter.fromJson(it) }

    /**
     * Purpose - from featured stats - converts a value from featured stats
     * to a JSON string for database storage
     * @param value: The players featured stats
     * @return String: The featured stats as a JSON string
     */
    @TypeConverter
    fun fromFeaturedStats(value: FeaturedStats?): String? =
        value?.let { featuredStatsAdapter.toJson(it) }

    /**
     * Purpose - to featured stats - converts a JSON string from the database
     * to a featured stats object
     * @param value: The JSON string of featured stats
     * @return FeaturedStats: The featured stats object
     */
    @TypeConverter
    fun toFeaturedStats(value: String?): FeaturedStats? =
        value?.let { featuredStatsAdapter.fromJson(it) }

    /**
     * Purpose - from first name x - converts a value from first name x
     * to its default value as a string for database storage
     * @param value: The first name x object
     * @return String: The first name x's default value as a string
     */
    @TypeConverter
    fun fromFirstNameX(value: FirstNameX?): String? = value?.default

    /**
     * Purpose - to first name x - converts a value from a string to
     * a to first name x object
     * @param value: The first name x's default value
     * @return FirstNameX: The first name x object
     */
    @TypeConverter
    fun toFirstNameX(value: String?): FirstNameX? =
        value?.let { FirstNameX(default = it) }

    /**
     * Purpose - from last name x - converts a value from last name x
     * to its default value as a string for database storage
     * @param value: The last name x object
     * @return String: The last name x's default value as a string
     */
    @TypeConverter
    fun fromLastNameX(value: LastNameX?): String? = value?.default

    /**
     * Purpose - to last name x - converts a value from the database to
     * a to last name x object
     * @param value: The last name x's default value
     * @return LastNameX: The last name x object
     */
    @TypeConverter
    fun toLastNameX(value: String?): LastNameX? =
        value?.let { LastNameX(default = it) }

    /**
     * Purpose - from full team name - converts a value from a full team name
     * object to it's default value as a string for database storage
     * @param value: The full team name object
     * @return String: The full team name's default value as a string
     */
    @TypeConverter
    fun fromFullTeamName(value: FullTeamName?): String? = value?.default

    /**
     * Purpose - to full team name - converts a value from the database
     * to a full team name object
     * @param value: The full team name's default value
     * @return FullTeamName: The full team name object
     */
    @TypeConverter
    fun toFullTeamName(value: String?): FullTeamName? =
        value?.let { FullTeamName(default = it) }

    /**
     * Purpose - from team common name x - converts a value from a team common
     * name x object to it's default value for database storage
     * @param value: The team common name x object
     * @return String: The team common name x's default string value
     */
    @TypeConverter
    fun fromTeamCommonNameX(value: TeamCommonNameX?): String? = value?.default

    /**
     * Purpose - to team common name x - converts a value from the database
     * to a team common name x object
     * @param value: The team common name x's default value
     * @return TeamCommonNameX: The team common name x object
     */
    @TypeConverter
    fun toTeamCommonNameX(value: String?): TeamCommonNameX? =
        value?.let { TeamCommonNameX(default = it) }
}