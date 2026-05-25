package de.unixkiwi.politikwatch.data.surveys.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surveys")
data class SurveyEntity(
    @PrimaryKey val id: String,
    val date: String,
    val surveyPeriodStart: String,
    val surveyPeriodEnd: String,
    val surveyedPersons: String,
    val parliamentId: String,
    val instituteId: String,
    val taskerId: String,
    val methodId: String,
    val results: Map<String, Float>
)

