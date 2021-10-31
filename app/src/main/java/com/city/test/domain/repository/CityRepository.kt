package com.city.test.domain.repository

import com.city.test.domain.model.City
import io.reactivex.Single


/**
 * To make an interaction between [CityRepositoryImp] & [GetCityUseCase]
 * */
interface CityRepository {
    fun getCities(): Single<MutableList<City>>

    fun addCities(cities: List<City>):LongArray

}