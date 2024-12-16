package dev.abhinav.formula1.model

import androidx.room.Embedded
import androidx.room.Relation

data class CircuitWithDrivers(
    @Embedded val circuit: Circuit,
    @Relation(
        parentColumn = "firstPlace",
        entityColumn = "name",
    )
    val firstPlaceDriver: Driver,

    @Relation(
        parentColumn = "secondPlace",
        entityColumn = "name"
    )
    val secondPlaceDriver: Driver,

    @Relation(
        parentColumn = "thirdPlace",
        entityColumn = "name"
    )
    val thirdPlaceDriver: Driver
)