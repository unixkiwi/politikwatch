package de.unixkiwi.politikwatch.data.awpolls.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.unixkiwi.politikwatch.data.awpolls.paging.AWPollsPagingSource
import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.domain.models.AWPoll
import de.unixkiwi.politikwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AWPollRepository @Inject constructor(
    private val api: AbgeordnetenWatchApi
) {
    fun getPagedPolls(): Flow<PagingData<AWPoll>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AWPollsPagingSource(api) }
        ).flow
    }

    fun getLatestPolls(count: Int = 100): Flow<Resource<List<AWPoll>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val latestRemotePolls = api.getPolls(0, count).data.map { it.toDomain() }
                emit(Resource.Success(latestRemotePolls))
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