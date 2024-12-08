package dev.abhinav.formula1.model

import androidx.room.Embedded
import androidx.room.Relation

data class CarWithDrivers(
    @Embedded val car: Car,
    @Relation(
        parentColumn = "name",
        entityColumn = "team"
    )
    val drivers: List<Driver>
)