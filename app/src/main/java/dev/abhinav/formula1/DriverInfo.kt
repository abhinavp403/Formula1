package dev.abhinav.formula1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.abhinav.formula1.model.Driver

val driver = Driver("Lewis Hamilton", "Mercedes", "United Kingdom", 353, 201)

val headers = listOf("Name", "Team", "Country", "GP Entered", "Podiums")

@Composable
fun DriverInfo(
    carName: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.hamilton),
            contentDescription = "driver",
            modifier = Modifier.padding(32.dp)
        )

        LazyColumn(
            Modifier.padding(8.dp)
        ) {
            items(headers.size) { index ->

                val data = when (index) {
                    0 -> driver.name
                    1 -> driver.team
                    2 -> driver.country
                    3 -> driver.gpEntered.toString()
                    4 -> driver.podiums.toString()
                    else -> ""
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header Column
                    TableCell(
                        text = headers[index],
                        weight = 1f,
                        isHeader = true
                    )

                    // Data Column
                    TableCell(
                        text = data,
                        weight = 2f
                    )
                }
            }
        }
    }
}

@Composable
fun TableCell(text: String, weight: Float, isHeader: Boolean = false) {
    Text(
        text = text,
        modifier = Modifier.padding(4.dp),
        style = if (isHeader) {
            MaterialTheme.typography.titleMedium.copy(color = Color.Black)
        } else {
            MaterialTheme.typography.bodySmall
        }
    )
}

@Preview
@Composable
fun DriverInfoPreview() {
    DriverInfo("")
}