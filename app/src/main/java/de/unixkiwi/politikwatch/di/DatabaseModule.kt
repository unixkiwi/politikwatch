package de.unixkiwi.politikwatch.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.unixkiwi.politikwatch.data.surveys.local.SurveyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSurveyDatabase(app: Application): SurveyDatabase {
        return Room.databaseBuilder(
            app,
            SurveyDatabase::class.java,
            SurveyDatabase.DATABASE_NAME
        ).build()
    }
}

