package de.unixkiwi.politikwatch.data.surveys.repository

import de.unixkiwi.politikwatch.data.core.model.DawumApiResponse
import de.unixkiwi.politikwatch.data.core.remote.DawumApi
import de.unixkiwi.politikwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DawumSurveyRepository @Inject constructor(
    private val api: DawumApi
) {

    suspend fun getSurveys(): Flow<Resource<DawumApiResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val surveys = api.getSurveys()
                emit(Resource.Success(surveys))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data! (IOException)", e.localizedMessage))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data! (HttpException)", e.localizedMessage))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data! (Unknown Error)", e.localizedMessage))
                return@flow
            }
        }
    }
}

