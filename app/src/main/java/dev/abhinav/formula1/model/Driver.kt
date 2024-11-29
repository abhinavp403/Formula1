package dev.abhinav.formula1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver")
data class Driver (
    @PrimaryKey val name: String,
    val team: String,
    val country: String,
    val gpEntered: Int,
    val podiums: Int,
    val championships: Int
)
