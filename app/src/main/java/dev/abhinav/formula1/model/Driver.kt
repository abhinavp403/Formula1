package dev.abhinav.formula1.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "driver",
    foreignKeys = [ForeignKey(
        entity = Car::class,
        parentColumns = ["name"],
        childColumns = ["team"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["team"])]
)
data class Driver (
    @PrimaryKey val name: String,
    val team: String,
    val country: String,
    val gpEntered: Int,
    val podiums: Int,
    val championships: Int,
    val image: Int
)
