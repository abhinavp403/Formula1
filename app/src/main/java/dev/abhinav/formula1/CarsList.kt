package dev.abhinav.formula1

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.abhinav.formula1.model.CarWithDrivers

@Composable
fun CarsList(carStateList: List<CarWithDrivers>?) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        carStateList?.size?.let {
            items(it) { index ->
                CarRow(carStateList[index].car.name, carStateList[index].car.image)
            }
        }
    }
}

@Composable
fun CarRow(
    carName: String,
    carImage: Int
) {
    val context = LocalContext.current
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
            .clickable {
                val intent = Intent(context, DriverActivity::class.java)
                intent.putExtra("car", carName)
                context.startActivity(intent)
            }
    ) {
        Text(
            text = carName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Image(
            painter = painterResource(carImage),
            contentDescription = "car",
        )
    }
}