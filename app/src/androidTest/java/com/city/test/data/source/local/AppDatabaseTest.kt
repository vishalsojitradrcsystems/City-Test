package com.city.test.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.city.test.data.source.local.dao.CityDao
import com.city.test.domain.model.City
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var cityDao: CityDao
    private lateinit var db: com.city.test.data.source.local.AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, com.city.test.data.source.local.AppDatabase::class.java
        ).build()
        cityDao = db.cityDao
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun passTestCase() {
        cityDao.insert(City(name = "Soverato", country = "IT", id = 6542288))
        cityDao.insert(City(name = "Pinerolo", country = "IT", id = 6540662))
        cityDao.insert(City(name = "Sydney", country = "AU", id = 2154855))
        cityDao.insert(City(name = "Mundaring", country = "AU", id = 2065337))
        val cities = cityDao.loadData()
        val searchresult = cities.filter { it.getDisplayName().startsWith("s", true) }
        searchresult.forEach { print(it.getDisplayName()) }
        assertTrue("passed", searchresult.isNotEmpty())
    }

    @Test
    @Throws(IOException::class)
    fun failTestCase() {
        cityDao.insert(City(name = "Soverato", country = "IT", id = 6542288))
        cityDao.insert(City(name = "Pinerolo", country = "IT", id = 6540662))
        cityDao.insert(City(name = "Sydney", country = "AU", id = 2154855))
        cityDao.insert(City(name = "Mundaring", country = "AU", id = 2065337))
        val cities = cityDao.loadData()
        val searchresult = cities.filter { it.getDisplayName().startsWith("test", true) }
        searchresult.forEach { print(it.getDisplayName()) }
        assertTrue("failed", searchresult.isNotEmpty())
    }

}