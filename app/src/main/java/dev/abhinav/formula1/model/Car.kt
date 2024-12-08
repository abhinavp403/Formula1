package dev.abhinav.formula1.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
    @PrimaryKey val name: String,
    val image: Int,
) {
    @Ignore var drivers: List<Driver> = emptyList() // Not persisted in the database
}
