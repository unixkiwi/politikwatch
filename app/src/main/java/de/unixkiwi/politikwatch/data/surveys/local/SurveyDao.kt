package de.unixkiwi.politikwatch.data.surveys.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.unixkiwi.politikwatch.data.surveys.local.entity.InstituteEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.MethodEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.ParliamentEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.PartyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.SurveyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.TaskerEntity

@Dao
interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurveys(surveys: List<SurveyEntity>)

    @Query("SELECT * FROM surveys")
    suspend fun getSurveys(): List<SurveyEntity>

    @Query("SELECT * FROM surveys WHERE id = :id")
    suspend fun getSurveyById(id: String): SurveyEntity?

    @Query("DELETE FROM surveys")
    suspend fun clearSurveys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParties(parties: List<PartyEntity>)

    @Query("SELECT * FROM parties WHERE id = :id")
    suspend fun getPartyById(id: String): PartyEntity?

    @Query("SELECT * FROM parties")
    suspend fun getParties(): List<PartyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParliaments(parliaments: List<ParliamentEntity>)

    @Query("SELECT * FROM parliaments WHERE id = :id")
    suspend fun getParliamentById(id: String): ParliamentEntity?

    @Query("SELECT * FROM parliaments")
    suspend fun getParliaments(): List<ParliamentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstitutes(institutes: List<InstituteEntity>)

    @Query("SELECT * FROM institutes WHERE id = :id")
    suspend fun getInstituteById(id: String): InstituteEntity?

    @Query("SELECT * FROM institutes")
    suspend fun getInstitutes(): List<InstituteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethods(methods: List<MethodEntity>)

    @Query("SELECT * FROM methods WHERE id = :id")
    suspend fun getMethodById(id: String): MethodEntity?

    @Query("SELECT * FROM methods")
    suspend fun getMethods(): List<MethodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskers(taskers: List<TaskerEntity>)

    @Query("SELECT * FROM taskers WHERE id = :id")
    suspend fun getTaskerById(id: String): TaskerEntity?

    @Query("SELECT * FROM taskers")
    suspend fun getTaskers(): List<TaskerEntity>
}
