package de.unixkiwi.politikwatch.data.surveys.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parties")
data class PartyEntity(
    @PrimaryKey val id: String,
    val shortcut: String,
    val name: String
)

