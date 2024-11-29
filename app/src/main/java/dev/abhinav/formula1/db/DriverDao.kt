package dev.abhinav.formula1.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.abhinav.formula1.model.Driver

@Dao
interface DriverDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDriver(driver: Driver)

    @Query("SELECT * FROM driver where team == :car")
    suspend fun getDriversFromCar(car: String): List<Driver>
}