package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forward(
    @Json(name = "birthCity")
    var birthCity: BirthCityX?,
    @Json(name = "birthCountry")
    var birthCountry: String?,
    @Json(name = "birthDate")
    var birthDate: String?,
    @Json(name = "birthStateProvince")
    var birthStateProvince: BirthStateProvince?,
    @Json(name = "firstName")
    var firstName: FirstName?,
    @Json(name = "headshot")
    var headshot: String?,
    @Json(name = "heightInCentimeters")
    var heightInCentimeters: Int?,
    @Json(name = "heightInInches")
    var heightInInches: Int?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "lastName")
    var lastName: LastNameX?,
    @Json(name = "positionCode")
    var positionCode: String?,
    @Json(name = "shootsCatches")
    var shootsCatches: String?,
    @Json(name = "sweaterNumber")
    var sweaterNumber: Int?,
    @Json(name = "weightInKilograms")
    var weightInKilograms: Int?,
    @Json(name = "weightInPounds")
    var weightInPounds: Int?
)