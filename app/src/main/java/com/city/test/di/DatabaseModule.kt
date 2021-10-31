package com.city.test.di

import dagger.Provides
import androidx.room.Room
import android.app.Application
import com.city.test.data.repository.CityRepositoryImp
import com.city.test.data.source.local.AppDatabase
import com.city.test.data.source.local.dao.CityDao
import com.city.test.domain.repository.CityRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }



    @Provides
    internal fun provideCityDao(appDatabase: AppDatabase): CityDao {
        return appDatabase.cityDao
    }

    @Singleton
    @Provides
    fun provideCitiesRepository(
        appDatabase: AppDatabase
    ): CityRepository {
        return CityRepositoryImp(appDatabase)
    }
}