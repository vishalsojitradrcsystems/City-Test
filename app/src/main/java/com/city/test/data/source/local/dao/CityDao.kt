package com.city.test.data.source.local.dao


import androidx.room.*
import com.city.test.domain.model.City
import io.reactivex.Single

/**
 * it provides access to [City] underlying database
 * */
@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: City): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCities(cities: List<City>):LongArray

    @Query("SELECT * FROM Cities ORDER BY name")
    fun loadAll(): Single<MutableList<City>>

    @Delete
    fun delete(photo: City)

    @Query("DELETE FROM Cities")
    fun deleteAll()

    @Update
    fun update(photo: City)

    @Query("SELECT * FROM Cities ORDER BY name")
    fun loadData(): List<City>

}