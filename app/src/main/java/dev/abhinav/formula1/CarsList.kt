package dev.abhinav.formula1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.abhinav.formula1.model.Car

val carList = listOf(
    Car("Mercedes", R.drawable.mercedes_car),
    Car("Red Bull", R.drawable.red_bull_car),
    Car("Ferrari", R.drawable.ferrari_car),
    Car("McLaren", R.drawable.mclaren_car),
    Car("Aston Martin", R.drawable.aston_martin_car),
    Car("Alpine", R.drawable.alpine_car),
    Car("Williams", R.drawable.williams_car),
    Car("RB", R.drawable.rb_car),
    Car("Kick Sauber", R.drawable.kick_sauber_car),
    Car("Haas", R.drawable.haas_car)
)

@Composable
fun CarsList() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        items(carList.size) {
            CarRow(carList[it].name, carList[it].imageRes)
        }
    }
}

@Composable
fun CarRow(
    carName: String,
    carImage: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Text(
            text = carName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        Image(
            painter = painterResource(carImage),
            contentDescription = "Mercedes Car",
        )
    }
}

@Preview
@Composable
fun CarsListPreview() {
    CarsList()
}