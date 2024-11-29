package dev.abhinav.formula1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.abhinav.formula1.model.Driver

@Database(entities = [Driver::class], version = 1)
abstract class DriverDatabase: RoomDatabase() {

    abstract fun driverDao(): DriverDao

    companion object {
        @Volatile
        private var INSTANCE: DriverDatabase? = null

        fun getInstance(context: Context): DriverDatabase {
            // only one thread of execution at a time can enter this block of code
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DriverDatabase::class.java,
                        "driver_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}