package de.unixkiwi.politikwatch.data.surveys.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskers")
data class TaskerEntity(
    @PrimaryKey val id: String,
    val name: String
)

