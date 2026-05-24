package de.unixkiwi.politikwatch.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.unixkiwi.politikwatch.data.core.remote.AbgeordnetenWatchApi
import de.unixkiwi.politikwatch.data.core.remote.DawumApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideAbgeordnetenWatchApi(client: OkHttpClient): AbgeordnetenWatchApi {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(AbgeordnetenWatchApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDawumApiService(client: OkHttpClient): DawumApi {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(DawumApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
            .create(DawumApi::class.java)
    }
}