package dev.abhinav.formula1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dev.abhinav.formula1.model.Car
import dev.abhinav.formula1.model.CarWithDrivers
import dev.abhinav.formula1.model.Circuit
import dev.abhinav.formula1.model.Driver
import dev.abhinav.formula1.repository.CarRepository
import dev.abhinav.formula1.ui.theme.Formula1Theme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val carRepository: CarRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabItems = listOf(
            TabItem(
                title = "Car",
            ),
            TabItem(
                title = "Circuit",
            )
        )

        // Map the grouped drivers to the corresponding cars
        val groupedDrivers = drivers.groupBy { it.team }
        val updatedCarList = cars.map { car ->
            car.also { it.drivers = groupedDrivers[car.name].orEmpty() }
        }

        lifecycleScope.launch {
            carRepository.addCars(updatedCarList)
            carRepository.addDrivers(drivers)
            carRepository.addCircuits(circuitList)
        }

        setContent {
            Formula1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember { mutableIntStateOf (0) }
                    val pagerState = rememberPagerState { tabItems.size }
                    val carStateList = remember { mutableStateOf<List<CarWithDrivers>?>(null) }

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }

                    LaunchedEffect(pagerState.currentPage) {
                        selectedTabIndex = pagerState.currentPage
                    }

                    LaunchedEffect(Unit) {
                        carStateList.value = carRepository.getAllCarsWithDrivers()
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            tabItems.forEachIndexed { index, item ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = { selectedTabIndex = index },
                                    text = {
                                        Text(text = item.title)
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { index ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                if(index == 0) {
                                    CarsList(carStateList.value)
                                } else {
                                    CircuitsList(carRepository)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String
)

val drivers = listOf(
    Driver("Alexander Albon", "Williams", "Thailand", 103, 2, 0, R.drawable.albon),
    Driver("Fernando Alonso", "Aston Martin", "Spain", 402, 106, 2, R.drawable.alonso),
    Driver("Valtteri Bottas", "Kick Sauber", "Finland", 244, 67, 0, R.drawable.bottas),
    Driver("Franco Colapinto", "Williams", "Argentina", 7, 0, 0, R.drawable.colapinto),
    Driver("Pierre Gasly", "Alpine", "France", 152, 5, 0, R.drawable.gasly),
    Driver("Lewis Hamilton", "Mercedes", "United Kingdom", 354, 202, 7, R.drawable.hamilton),
    Driver("Nico Hulkenberg", "Haas", "Germany", 228, 0, 0, R.drawable.hulkenberg),
    Driver("Liam Lawson", "RB", "New Zealand", 9, 0, 0, R.drawable.lawson),
    Driver("Charles Leclerc", "Ferrari", "Monaco", 147, 41, 0, R.drawable.leclerc),
    Driver("Kevin Magnussen", "Haas", "Denmark", 184, 1, 0, R.drawable.magnussen),
    Driver("Lando Norris", "McLaren", "United Kingdom", 126, 25, 0, R.drawable.norris),
    Driver("Esteban Ocon", "Alpine", "France", 155, 4, 0, R.drawable.ocon),
    Driver("Sergio Perez", "Red Bull", "Mexico", 280, 39, 0, R.drawable.perez),
    Driver("Oscar Piastri", "McLaren", "Australia", 44, 9, 0, R.drawable.piastri),
    Driver("George Russell", "Mercedes", "United Kingdom", 126, 15, 0, R.drawable.russell),
    Driver("Carlos Sainz", "Ferrari", "Spain", 206, 26, 0, R.drawable.sainz),
    Driver("Lance Stroll", "Aston Martin", "Canada", 165, 3, 0, R.drawable.stroll),
    Driver("Yuki Tsunoda", "RB", "Japan", 88, 309, 0, R.drawable.tsunoda),
    Driver("Max Verstappen", "Red Bull", "Netherlands", 207, 111, 4, R.drawable.verstappen),
    Driver("Zhou Guanyu", "Kick Sauber", "China", 66, 0, 0, R.drawable.zhou)
)

val cars = listOf(
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

val circuitList = listOf(
    Circuit("Bahrain", R.drawable.bahrain_circuit, "Max Verstappen", "Sergio Perez", "Carlos Sainz"),
    Circuit("Jeddah", R.drawable.jeddah_circuit, "Max Verstappen", "Sergio Perez", "Charles Leclerc"),
    Circuit("Melbourne Street", R.drawable.melbourne_circuit, "Carlos Sainz", "Charles Leclerc", "Lando Norris"),
    Circuit("Suzuka", R.drawable.suzuka_circuit, "Max Verstappen", "Sergio Perez", "Carlos Sainz"),
    Circuit("Shanghai", R.drawable.shanghai_circuit, "Max Verstappen", "Lando Norris", "Sergio Perez"),
    Circuit("Miami", R.drawable.miami_circuit, "Lando Norris","Max Verstappen", "Charles Leclerc"),
    Circuit("Imola", R.drawable.imola_circuit, "Max Verstappen", "Lando Norris", "Charles Leclerc"),
    Circuit("Monte Carlo", R.drawable.monte_carlo_circuit, "Charles Leclerc", "Oscar Piastri", "Carlos Sainz"),
    Circuit("Gilles Villeneuve", R.drawable.montreal_circuit, "Max Verstappen", "Lando Norris", "George Russell"),
    Circuit("Barcelona", R.drawable.barcelona_circuit, "Max Verstappen", "Lando Norris", "Lewis Hamilton"),
    Circuit("Red Bull Ring", R.drawable.spielberg_circuit, "George Russell", "Oscar Piastri", "Carlos Sainz"),
    Circuit("Silverstone", R.drawable.silverstone_circuit, "Lewis Hamilton", "Max Verstappen", "Lando Norris"),
    Circuit("Hungaroring", R.drawable.hungaroring_circuit, "Oscar Piastri", "Lando Norris", "Lewis Hamilton"),
    Circuit("Spa-Francorchamps", R.drawable.spa_circuit, "Lewis Hamilton", "Oscar Piastri", "Charles Leclerc"),
    Circuit("Zandvoort", R.drawable.zandvoort_circuit, "Lando Norris", "Max Verstappen", "Charles Leclerc"),
    Circuit("Monza", R.drawable.monza_circuit, "Charles Leclerc", "Oscar Piastri", "Lando Norris"),
    Circuit("Baku Street", R.drawable.baku_circuit, "Oscar Piastri", "Charles Leclerc", "George Russell"),
    Circuit("Marina Bay Street", R.drawable.marina_bay_circuit, "Lando Norris", "Max Verstappen", "Oscar Piastri"),
    Circuit("Circuit Of The Americas", R.drawable.austin_circuit, "Charles Leclerc", "Carlos Sainz", "Max Verstappen"),
    Circuit("Mexico City", R.drawable.mexico_city_circuit, "Carlos Sainz", "Lando Norris", "Charles Leclerc"),
    Circuit("Sao Paulo", R.drawable.sao_paulo_circuit, "Max Verstappen", "Esteban Ocon", "Pierre Gasly"),
    Circuit("Las Vegas", R.drawable.las_vegas_circuit, "George Russell", "Lewis Hamilton", "Carlos Sainz"),
    Circuit("Losail", R.drawable.qatar_circuit, "Max Verstappen", "Charles Leclerc", "Oscar Piastri"),
    Circuit("Yas Marina", R.drawable.abu_dhabi_circuit, "Lando Norris", "Carlos Sainz", "Charles Leclerc")
)