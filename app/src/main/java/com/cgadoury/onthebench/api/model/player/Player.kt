package com.cgadoury.onthebench.api.model.player


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "players")
@JsonClass(generateAdapter = true)
data class Player(
    var lastUpdated: Long = System.currentTimeMillis(),
    @Ignore
    @Json(name = "awards")
    var awards: List<Award>?=null,
    @Ignore
    @Json(name = "badges")
    var badges: List<Badge>?=null,
    @Json(name = "birthCity")
    var birthCity: BirthCity=BirthCity(),
    @Json(name = "birthCountry")
    var birthCountry: String="",
    @Json(name = "birthDate")
    var birthDate: String="",
    @Json(name = "birthStateProvince")
    var birthStateProvince: BirthStateProvince = BirthStateProvince(),
    @Ignore
    @Json(name = "careerTotals")
    var careerTotals: CareerTotals?=null,
    @Json(name = "currentTeamAbbrev")
    var currentTeamAbbrev: String="",
    @Json(name = "currentTeamId")
    var currentTeamId: Int=0,
    @Ignore
    @Json(name = "currentTeamRoster")
    var currentTeamRoster: List<CurrentTeamRoster> = emptyList(),
    @Embedded(prefix = "draft_")
    @Json(name = "draftDetails")
    var draftDetails: DraftDetails = DraftDetails(),
    @Json(name = "featuredStats")
    var featuredStats: FeaturedStats = FeaturedStats(),
    @Json(name = "firstName")
    var firstName: FirstNameX = FirstNameX(),
    @Json(name = "fullTeamName")
    var fullTeamName: FullTeamName = FullTeamName(),
    @Json(name = "headshot")
    var headshot: String="",
    @Json(name = "heightInCentimeters")
    var heightInCentimeters: Int=0,
    @Json(name = "heightInInches")
    var heightInInches: Int=0,
    @Json(name = "heroImage")
    var heroImage: String="",
    @Json(name = "inHHOF")
    var inHHOF: Int=0,
    @Json(name = "inTop100AllTime")
    var inTop100AllTime: Int=0,
    @Json(name = "isActive")
    var isActive: Boolean=true,
    @Ignore
    @Json(name = "last5Games")
    var last5Games: List<Last5Game> = emptyList(),
    @Json(name = "lastName")
    var lastName: LastNameX?=null,
    @PrimaryKey
    @Json(name = "playerId")
    var playerId: Int=0,
    @Json(name = "playerSlug")
    var playerSlug: String="",
    @Json(name = "position")
    var position: String="",
    @Json(name = "seasonTotals")
    var seasonTotals: List<SeasonTotal> = emptyList(),
    @Json(name = "shootsCatches")
    var shootsCatches: String="",
    @Ignore
    @Json(name = "shopLink")
    var shopLink: String="",
    @Json(name = "sweaterNumber")
    var sweaterNumber: Int=0,
    @Json(name = "teamCommonName")
    var teamCommonName: TeamCommonNameX?=null,
    @Json(name = "teamLogo")
    var teamLogo: String="",
    @Ignore
    @Json(name = "teamPlaceNameWithPreposition")
    var teamPlaceNameWithPreposition: TeamPlaceNameWithPrepositionX?=null,
    @Json(name = "twitterLink")
    var twitterLink: String="",
    @Json(name = "watchLink")
    var watchLink: String="",
    @Json(name = "weightInKilograms")
    var weightInKilograms: Int=0,
    @Json(name = "weightInPounds")
    var weightInPounds: Int=0
)