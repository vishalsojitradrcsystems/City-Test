package com.city.test.data.repository

import com.city.test.data.source.local.AppDatabase
import com.city.test.domain.model.City
import com.city.test.domain.repository.CityRepository
import io.reactivex.Single

/**
 * This repository is responsible for
 * fetching data [City] from db
 * */
class CityRepositoryImp(
    private val database: AppDatabase,
) : CityRepository {
    override fun getCities(): Single<MutableList<City>> {
        return database.cityDao.loadAll()
    }

    override fun addCities(cities: List<City>): LongArray {
        return database.cityDao.insertAllCities(cities)
    }

}