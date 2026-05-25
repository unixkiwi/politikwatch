package de.unixkiwi.politikwatch.domain.models

import java.time.LocalDate

data class Survey(
    val id: String,
    val date: LocalDate,
    val surveyPeriodStart: LocalDate,
    val surveyPeriodEnd: LocalDate,
    val surveyedPersons: Int,
    val parliamentName: String,
    val parliamentShortcut: String,
    val instituteName: String,
    val taskerName: String,
    val methodName: String,
    val results: Map<DawumParty, Float>
)

data class DawumParty(
    val name: String = "Unknown",
    val shortcut: String = "N/A"
)
