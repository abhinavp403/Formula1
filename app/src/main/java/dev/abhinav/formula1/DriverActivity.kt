package dev.abhinav.formula1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.abhinav.formula1.model.CarWithDrivers
import dev.abhinav.formula1.repository.CarRepository
import dev.abhinav.formula1.ui.theme.Formula1Theme
import org.koin.android.ext.android.inject

class DriverActivity : ComponentActivity() {

    private val carRepository: CarRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val carName = intent.getStringExtra("car")!!

        setContent {
            Formula1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val driverInfoList = remember { mutableStateOf<CarWithDrivers?>(null) }

                    LaunchedEffect(carName) {
                        driverInfoList.value = carRepository.getDriverInfo(carName)
                    }

                    if (driverInfoList.value == null) {
                        LoadingIndicator()
                    } else if (driverInfoList.value?.drivers!!.isNotEmpty()) {
                        DriverInfo(driverInfoList.value!!.drivers)
                    } else {
                        Text("No driver information found for $carName.")
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}