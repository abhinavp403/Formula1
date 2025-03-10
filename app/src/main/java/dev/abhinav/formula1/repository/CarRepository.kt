package dev.abhinav.formula1.repository

import dev.abhinav.formula1.db.CarDao
import dev.abhinav.formula1.model.Car
import dev.abhinav.formula1.model.CarWithDrivers
import dev.abhinav.formula1.model.Circuit
import dev.abhinav.formula1.model.CircuitWithDrivers
import dev.abhinav.formula1.model.Driver

class CarRepository(private val carDao: CarDao) {

    suspend fun addCars(cars: List<Car>) {
        carDao.insertAllCars(cars)
    }

    suspend fun addDrivers(cars: List<Driver>) {
        carDao.insertAllDrivers(cars)
    }

    suspend fun addCircuits(cars: List<Circuit>) {
        carDao.insertAllCircuits(cars)
    }

    suspend fun getAllCarsWithDrivers() : List<CarWithDrivers> {
        return carDao.getAllCarsWithDrivers()
    }

    suspend fun getDriverInfo(carName: String) : CarWithDrivers {
        return carDao.getCarWithDrivers(carName)
    }

    suspend fun getDriverFromCircuit(circuitName: String) : CircuitWithDrivers {
        return carDao.getDriverFromCircuit(circuitName)
    }
}