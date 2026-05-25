package de.unixkiwi.politikwatch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.unixkiwi.politikwatch.data.awpolls.repo.AWPollRepository
import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.data.surveys.remote.DawumApi
import de.unixkiwi.politikwatch.data.surveys.repo.DawumSurveyRepository
import de.unixkiwi.politikwatch.data.surveys.local.SurveyDatabase
import de.unixkiwi.politikwatch.data.votes.repo.VoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePollRepo(api: AbgeordnetenWatchApi): AWPollRepository {
        return AWPollRepository(api)
    }

    @Provides
    @Singleton
    fun provideVoteRepo(api: AbgeordnetenWatchApi): VoteRepository {
        return VoteRepository(api)
    }

    @Provides
    @Singleton
    fun provideDawumSurveyRepo(api: DawumApi, db: SurveyDatabase): DawumSurveyRepository {
        return DawumSurveyRepository(api, db)
    }
}