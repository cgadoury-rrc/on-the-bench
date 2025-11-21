package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Defensemen(
    @Json(name = "birthCity")
    override var birthCity: BirthCity?,
    @Json(name = "birthCountry")
    override var birthCountry: String?,
    @Json(name = "birthDate")
    override var birthDate: String?,
    @Json(name = "birthStateProvince")
    override var birthStateProvince: BirthStateProvince?,
    @Json(name = "firstName")
    override var firstName: FirstName?,
    @Json(name = "headshot")
    override var headshot: String?,
    @Json(name = "heightInCentimeters")
    override var heightInCentimeters: Int?,
    @Json(name = "heightInInches")
    override var heightInInches: Int?,
    @Json(name = "id")
    override var id: Int?,
    @Json(name = "lastName")
    override var lastName: LastName?,
    @Json(name = "positionCode")
    override var positionCode: String?,
    @Json(name = "shootsCatches")
    override var shootsCatches: String?,
    @Json(name = "sweaterNumber")
    override var sweaterNumber: Int?,
    @Json(name = "weightInKilograms")
    override var weightInKilograms: Int?,
    @Json(name = "weightInPounds")
    override var weightInPounds: Int?
): RosterPlayerData