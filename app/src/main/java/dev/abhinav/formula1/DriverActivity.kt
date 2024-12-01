package dev.abhinav.formula1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.abhinav.formula1.db.DriverDatabase
import dev.abhinav.formula1.model.Driver
import dev.abhinav.formula1.repository.DriverRepository
import dev.abhinav.formula1.ui.theme.Formula1Theme

class DriverActivity : ComponentActivity() {

    private lateinit var driverRepository: DriverRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val carName = intent.getStringExtra("car")!!

        val database = DriverDatabase.getInstance(this)
        val driverDao = database.driverDao()
        driverRepository = DriverRepository(driverDao)

        setContent {
            Formula1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val driverInfo = remember { mutableStateOf<List<Driver>?>(null) }

                    LaunchedEffect(carName) {
                        val result = driverRepository.getDriverInfo(carName)
                        driverInfo.value = result
                    }

                    if (driverInfo.value == null) {
                        LoadingIndicator()
                    } else if (driverInfo.value!!.isNotEmpty()) {
                        //DriverInfo(driverInfo.value!![0])
                        DriverInfo(driverInfo.value!!)
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