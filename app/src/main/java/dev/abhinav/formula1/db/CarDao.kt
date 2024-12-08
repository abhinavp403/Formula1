package dev.abhinav.formula1.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.abhinav.formula1.model.Car
import dev.abhinav.formula1.model.CarWithDrivers
import dev.abhinav.formula1.model.Driver

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCars(cars: List<Car>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDrivers(drivers: List<Driver>)

    @Transaction
    @Query("SELECT * FROM car WHERE name = :carName")
    suspend fun getCarWithDrivers(carName: String): CarWithDrivers

    @Transaction
    @Query("SELECT * FROM car")
    suspend fun getAllCarsWithDrivers(): List<CarWithDrivers>
}