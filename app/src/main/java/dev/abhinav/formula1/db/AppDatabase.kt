package dev.abhinav.formula1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.abhinav.formula1.model.Car
import dev.abhinav.formula1.model.Circuit
import dev.abhinav.formula1.model.Driver

@Database(entities = [Car::class, Driver::class, Circuit::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun carDao(): CarDao
}