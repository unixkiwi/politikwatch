package de.unixkiwi.politikwatch.data.polls.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import de.unixkiwi.politikwatch.data.core.model.AWApiResponseWrapper
import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.data.polls.model.AWApiPollModel
import de.unixkiwi.politikwatch.domain.models.BundestagsPoll
import retrofit2.HttpException
import java.io.IOException

class PollsPagingSource(
    private val api: AbgeordnetenWatchApi
) : PagingSource<Int, BundestagsPoll>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BundestagsPoll> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize

            val response: AWApiResponseWrapper<List<AWApiPollModel>> = api.getPolls(
                rangeStart = offset,
                rangeEnd = offset + limit
            )

            val polls = response.data.map { it.toDomain() }

            LoadResult.Page(
                data = polls,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (polls.isEmpty()) null else offset + limit
            )
        } catch (e: IOException) {
            LoadResult.Error(e) // No internet connection
        } catch (e: HttpException) {
            LoadResult.Error(e) // Server error 404, 500
        } catch (e: Exception) {
            Log.e("PollsPagingSource", "Unexpected error: ${e.localizedMessage}", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BundestagsPoll>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}