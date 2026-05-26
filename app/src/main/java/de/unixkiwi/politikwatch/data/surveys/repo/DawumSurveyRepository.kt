package de.unixkiwi.politikwatch.data.surveys.repo

import de.unixkiwi.politikwatch.data.surveys.local.SurveyDatabase
import de.unixkiwi.politikwatch.data.surveys.local.entity.InstituteEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.MethodEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.ParliamentEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.PartyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.SurveyEntity
import de.unixkiwi.politikwatch.data.surveys.local.entity.TaskerEntity
import de.unixkiwi.politikwatch.data.surveys.model.DawumApiResponse
import de.unixkiwi.politikwatch.data.surveys.remote.DawumApi
import de.unixkiwi.politikwatch.domain.models.DawumParty
import de.unixkiwi.politikwatch.domain.models.Survey
import de.unixkiwi.politikwatch.domain.models.SurveyMapper
import de.unixkiwi.politikwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DawumSurveyRepository @Inject constructor(
    private val api: DawumApi,
    private val db: SurveyDatabase
) {

    private val dao = db.surveyDao

    fun getSurveys(
        useLocal: Boolean = true,
        useRemote: Boolean = true
    ): Flow<Resource<List<Survey>>> {
        return flow {
            emit(Resource.Loading())

            if (useLocal) {
                try {
                    emit(Resource.Success(getLocal()))
                } catch (e: Exception) {
                    Timber.e(e)
                    emit(
                        Resource.Error(
                            "Couldn't load local data! (Exception)",
                            e.localizedMessage
                        )
                    )
                }

                if (!useRemote) {
                    return@flow
                }
            }

            try {
                val surveys = api.getSurveys()
                saveToLocalDatabase(surveys)
                val result = try {
                    Resource.Success(getLocal())
                } catch (e: Exception) {
                    Timber.e(e)
                    Resource.Error("Couldn't load local data! (Exception)", e.localizedMessage)
                }
                emit(result)
                return@flow
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resource.Error("Couldn't load data! (IOException)", e.localizedMessage))
                return@flow
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resource.Error("Couldn't load data! (HttpException)", e.localizedMessage))
                return@flow
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error("Couldn't load data! (Unknown Error)", e.localizedMessage))
                return@flow
            }
        }
    }

    private suspend fun getLocal(): List<Survey> {
        val localSurveys = dao.getSurveys()
        val localParliaments = dao.getParliaments()
        val localMethods = dao.getMethods()
        val localTaskers = dao.getTaskers()
        val localParties = dao.getParties()
        val localInstitutes = dao.getInstitutes()

        if (localSurveys.isNotEmpty()) {
            return localSurveys.map { survey ->
                SurveyMapper.toSurveyDomain(
                    survey = survey,
                    parliament = localParliaments.firstOrNull { p -> p.id == survey.parliamentId },
                    institute = localInstitutes.firstOrNull { i -> i.id == survey.instituteId },
                    tasker = localTaskers.firstOrNull { t -> t.id == survey.taskerId },
                    method = localMethods.firstOrNull { m -> m.id == survey.methodId },
                    result = survey.results.flatMap { (partyId, percentage) ->
                        val party = localParties.firstOrNull { p -> p.id == partyId }?.let {
                            SurveyMapper.toDawumPartyDomain(it)
                        } ?: DawumParty()
                        listOf(party to percentage)
                    }.toMap()
                )
            }
        } else {
            throw Exception("No local data available/Local data is empty1")
        }
    }

    private suspend fun saveToLocalDatabase(response: DawumApiResponse) {
        val surveyEntities = response.Surveys.map { (id, survey) ->
            SurveyEntity(
                id = id,
                date = survey.Date,
                surveyPeriodStart = survey.Survey_Period.Date_Start,
                surveyPeriodEnd = survey.Survey_Period.Date_End,
                surveyedPersons = survey.Surveyed_Persons,
                parliamentId = survey.Parliament_ID,
                instituteId = survey.Institute_ID,
                taskerId = survey.Tasker_ID,
                methodId = survey.Method_ID,
                results = survey.Results
            )
        }

        val partyEntities = response.Parties.map { (id, party) ->
            PartyEntity(id = id, shortcut = party.Shortcut, name = party.Name)
        }

        val parliamentEntities = response.Parliaments.map { (id, parliament) ->
            ParliamentEntity(
                id = id,
                shortcut = parliament.Shortcut,
                name = parliament.Name,
                election = parliament.Election
            )
        }

        val instituteEntities = response.Institutes.map { (id, institute) ->
            InstituteEntity(id = id, name = institute.Name)
        }

        val methodEntities = response.Methods.map { (id, method) ->
            MethodEntity(id = id, name = method.Name)
        }

        val taskerEntities = response.Taskers.map { (id, tasker) ->
            TaskerEntity(id = id, name = tasker.Name)
        }

        dao.insertSurveys(surveyEntities)
        dao.insertParties(partyEntities)
        dao.insertParliaments(parliamentEntities)
        dao.insertInstitutes(instituteEntities)
        dao.insertMethods(methodEntities)
        dao.insertTaskers(taskerEntities)
    }
}
