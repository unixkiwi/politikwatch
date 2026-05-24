package de.unixkiwi.politikwatch.data.surveys.model

data class DawumSurveyModel(
    val Date: String,
    val Survey_Period: DawumSurveyPeriodModel,
    val Surveyed_Persons: String,
    val Parliament_ID: String,
    val Institute_ID: String,
    val Tasker_ID: String,
    val Method_ID: String,
    val Results: Map<String, Float>
)

data class DawumSurveyPeriodModel(
    val Date_Start: String,
    val Date_End: String
)