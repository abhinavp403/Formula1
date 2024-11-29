package dev.abhinav.formula1.repository

import dev.abhinav.formula1.db.DriverDao
import dev.abhinav.formula1.model.Driver

class DriverRepository(private val driverDao: DriverDao) {

    suspend fun addDriver(driver: Driver) {
        driverDao.insertDriver(driver)
    }
}