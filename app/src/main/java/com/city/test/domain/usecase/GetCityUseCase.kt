package com.city.test.domain.usecase

import com.city.test.domain.model.City
import com.city.test.domain.repository.CityRepository
import com.city.test.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [CityViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetCityUseCase @Inject constructor(private val repository: CityRepository) : SingleUseCase<MutableList<City>>() {

    override fun buildUseCaseSingle(): Single<MutableList<City>> {
        return repository.getCities()
    }

    fun saveCities(cities:List<City>): LongArray {
        return repository.addCities(cities)
    }
}