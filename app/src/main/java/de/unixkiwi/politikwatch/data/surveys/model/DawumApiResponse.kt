package de.unixkiwi.politikwatch.data.surveys.model

data class DawumApiResponse(
    val Database: DawumDatabaseModel,
    val Surveys: Map<String, DawumSurveyModel>,
    val Parties: Map<String, DawumPartyModel>,
    val Methods: Map<String, DawumMethodsModel>,
    val Taskers: Map<String, DawumTaskerModel>,
    val Institutes: Map<String, DawumInstituteModel>,
    val Parliaments: Map<String, DawumParliamentModel>
)

data class DawumPartyModel(
    val Shortcut: String,
    val Name: String
)

data class DawumMethodsModel(
    val Name: String
)

data class DawumTaskerModel(
    val Name: String
)

data class DawumInstituteModel(
    val Name: String
)

data class DawumParliamentModel(
    val Shortcut: String,
    val Name: String,
    val Election: String
)

data class DawumDatabaseModel(
    val License: DawumLicenseModel,
    val Publisher: String,
    val Author: String,
    val Last_Update: String
)

data class DawumLicenseModel(
    val Name: String,
    val Shortcut: String,
    val Link: String
)
