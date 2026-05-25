package de.unixkiwi.politikwatch.data.surveys.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "institutes")
data class InstituteEntity(
    @PrimaryKey val id: String,
    val name: String
)

