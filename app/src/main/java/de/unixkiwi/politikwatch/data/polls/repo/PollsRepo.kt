package de.unixkiwi.politikwatch.data.polls.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.data.polls.paging.PollsPagingSource
import de.unixkiwi.politikwatch.domain.models.Poll
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PollRepository @Inject constructor(
    private val api: AbgeordnetenWatchApi
) {
    fun getPagedPolls(): Flow<PagingData<Poll>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PollsPagingSource(api) }
        ).flow
    }
}