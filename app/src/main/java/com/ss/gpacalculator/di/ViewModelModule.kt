package com.ss.gpacalculator.di

import com.ss.gpacalculator.repository.overall.OverallRepository
import com.ss.gpacalculator.repository.overall.OverallRepositoryImpl
import com.ss.gpacalculator.repository.semester.SemesterRepository
import com.ss.gpacalculator.repository.semester.SemesterRepositoryImpl
import com.ss.gpacalculator.repository.settings.SettingsRepository
import com.ss.gpacalculator.repository.settings.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindOverallRepository(
        overallRepositoryImpl: OverallRepositoryImpl
    ): OverallRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSemesterRepository(
        semesterRepositoryImpl: SemesterRepositoryImpl
    ): SemesterRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSettingsRepository(
        settingsManagerImpl: SettingsRepositoryImpl
    ): SettingsRepository
}