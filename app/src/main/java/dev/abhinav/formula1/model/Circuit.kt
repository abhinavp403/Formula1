package dev.abhinav.formula1.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "circuit")
@Immutable
data class Circuit(
    @PrimaryKey val name: String,
    val imageRes: Int,
    val firstPlace: String,
    val secondPlace: String,
    val thirdPlace: String
)
