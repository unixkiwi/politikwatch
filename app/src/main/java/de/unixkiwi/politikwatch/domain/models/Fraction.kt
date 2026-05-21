package de.unixkiwi.politikwatch.domain.models

data class Fraction(
    val id: Int,
    val entityType: String,
    val label: String,
    val fullName: String,
    val shortName: String
)