package com.kemsky.kompas.di

import android.content.Context
import com.kemsky.kompas.data.remote.ApiService
import com.kemsky.kompas.data.repository.GithubRepository
import com.kemsky.kompas.data.repository.GithubRepositoryImpl
import com.kemsky.kompas.data.room.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(@ApplicationContext context: Context): ApiService = ApiService.invoke(context)

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): GithubRepository = GithubRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase =
        GithubDatabase.getDatabase(context)

}