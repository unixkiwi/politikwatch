package de.unixkiwi.politikwatch.domain.models

import java.time.LocalDate

data class Poll(
    val id: Int,
    val entityType: String,
    val label: String,
    val description: String,
    val accepted: Boolean,
    val date: LocalDate
)