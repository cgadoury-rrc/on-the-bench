package com.cgadoury.onthebench.api.model.roster

interface RosterPlayerData {
    var birthCity: BirthCity?
    var birthCountry: String?
    var birthDate: String?
    var birthStateProvince: BirthStateProvince?
    var firstName: FirstName?
    var headshot: String?
    var heightInCentimeters: Int?
    var heightInInches: Int?
    var id: Int?
    var lastName: LastName?
    var positionCode: String?
    var shootsCatches: String?
    var sweaterNumber: Int?
    var weightInKilograms: Int?
    var weightInPounds: Int?
}