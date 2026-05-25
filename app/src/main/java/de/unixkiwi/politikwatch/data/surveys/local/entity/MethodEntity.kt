package de.unixkiwi.politikwatch.data.surveys.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "methods")
data class MethodEntity(
    @PrimaryKey val id: String,
    val name: String
)

