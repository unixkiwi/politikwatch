package de.unixkiwi.politikwatch.data.surveys.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.unixkiwi.politikwatch.data.surveys.local.entity.InstituteEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.ParliamentEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.PartyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.MethodEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.TaskerEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.SurveyEntity

@Database(
    entities = [
        SurveyEntity::class,
        PartyEntity::class,
        ParliamentEntity::class,
        InstituteEntity::class,
        MethodEntity::class,
        TaskerEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SurveyDatabase : RoomDatabase() {

    abstract val surveyDao: SurveyDao

    companion object {
        const val DATABASE_NAME = "survey_db"
    }
}
