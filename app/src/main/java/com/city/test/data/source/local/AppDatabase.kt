package com.city.test.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.city.test.data.source.local.dao.CityDao
import com.city.test.domain.model.City
import com.city.test.domain.model.Coord

/**
 * To manage data items that can be accessed, updated
 * & maintain relationships between them
 *
 */
@Database(entities = [City::class, Coord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val cityDao: CityDao

    companion object {
        const val DB_NAME = "CityDatabase.db"
    }
}
