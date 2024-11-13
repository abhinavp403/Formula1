package dev.abhinav.formula1

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import dev.abhinav.formula1.model.Circuit

val circuitList = listOf(
    Circuit("Bahrain", R.drawable.bahrain_circuit),
    Circuit("Jeddah", R.drawable.jeddah_circuit),
    Circuit("Melbourne Street", R.drawable.melbourne_circuit),
    Circuit("Suzuka", R.drawable.suzuka_circuit),
    Circuit("Shanghai", R.drawable.shanghai_circuit),
    Circuit("Miami", R.drawable.miami_circuit),
    Circuit("Imola", R.drawable.imola_circuit),
    Circuit("Monte Carlo", R.drawable.monte_carlo_circuit),
    Circuit("Gilles Villeneuve", R.drawable.montreal_circuit),
    Circuit("Barcelona", R.drawable.barcelona_circuit),
    Circuit("Red Bull Ring", R.drawable.spielberg_circuit),
    Circuit("Silverstone", R.drawable.silverstone_circuit),
    Circuit("Hungaroring", R.drawable.hungaroring_circuit),
    Circuit("Spa-Francorchamps", R.drawable.spa_circuit),
    Circuit("Zandvoort", R.drawable.zandvoort_circuit),
    Circuit("Monza", R.drawable.monza_circuit),
    Circuit("Baku Street", R.drawable.baku_circuit),
    Circuit("Marina Bay Street", R.drawable.marina_bay_circuit),
    Circuit("Circuit Of The Americas", R.drawable.austin_circuit),
    Circuit("Mexico City", R.drawable.mexico_city_circuit),
    Circuit("Sao Paulo", R.drawable.sao_paulo_circuit),
    Circuit("Las Vegas", R.drawable.las_vegas_circuit),
    Circuit("Losail", R.drawable.qatar_circuit),
    Circuit("Yas Marina", R.drawable.abu_dhabi_circuit)

)

@Composable
fun CircuitsList() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        items(circuitList.size) {
            CircuitRow(circuitList[it].name, circuitList[it].imageRes)
        }
    }
}

@Composable
fun CircuitRow(
    circuitName: String,
    circuitImage: Int
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
            text = circuitName,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Image(
            painter = painterResource(circuitImage),
            contentDescription = "circuit",
        )
    }
}

@Preview
@Composable
fun CircuitsListPreview() {
    CircuitsList()
}