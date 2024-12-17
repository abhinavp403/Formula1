package dev.abhinav.formula1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.abhinav.formula1.model.Driver
import dev.abhinav.formula1.repository.CarRepository
import kotlinx.coroutines.launch

@Composable
fun CircuitsList(carRepository: CarRepository) {
    LazyColumn(modifier = Modifier.fillMaxSize()
    ) {
        items(circuitList.size) {
            CircuitRow(circuitList[it].name, circuitList[it].imageRes, carRepository)
        }
    }
}

@Composable
fun CircuitRow(
    circuitName: String,
    circuitImage: Int,
    carRepository: CarRepository
) {
    var isOverlayVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var firstPlaceDriver by remember { mutableStateOf<Driver?>(null) }
    var secondPlaceDriver by remember { mutableStateOf<Driver?>(null) }
    var thirdPlaceDriver by remember { mutableStateOf<Driver?>(null) }
    var driver by remember { mutableStateOf<Driver?>(null) }
    var trophyImage by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RectangleShape,
            modifier = Modifier
                .height(400.dp)
                .padding(16.dp)
                .clickable {
                    isOverlayVisible = true
                    coroutineScope.launch {
                        val driverPlace = carRepository.getDriverFromCircuit(circuitName)
                        firstPlaceDriver = driverPlace.firstPlaceDriver
                        secondPlaceDriver = driverPlace.secondPlaceDriver
                        thirdPlaceDriver = driverPlace.thirdPlaceDriver
                    }
                }
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
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        AnimatedVisibility(
            visible = isOverlayVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { isOverlayVisible = false },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RectangleShape,
                    modifier = Modifier
                        .height(400.dp)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        repeat(3) { index ->
                            when (index) {
                                0 -> {
                                    driver = firstPlaceDriver
                                    trophyImage = R.drawable.first_trophy
                                }
                                1 -> {
                                    driver = secondPlaceDriver
                                    trophyImage = R.drawable.second_trophy
                                }
                                2 -> {
                                    driver = thirdPlaceDriver
                                    trophyImage = R.drawable.third_trophy
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box {
                                    driver?.image?.let { painterResource(it) }?.let {
                                        Image(
                                            painter = it,
                                            contentDescription = "driver",
                                            modifier = Modifier
                                                .size(128.dp)
                                                .padding(8.dp)
                                        )
                                    }

                                    Image(
                                        painter = painterResource(trophyImage),
                                        contentDescription = "trophy",
                                        modifier = Modifier
                                            .size(45.dp)
                                            .padding(4.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }

                                driver?.name?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontSize = 24.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}