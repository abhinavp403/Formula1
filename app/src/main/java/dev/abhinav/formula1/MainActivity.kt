package dev.abhinav.formula1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dev.abhinav.formula1.db.DriverDatabase
import dev.abhinav.formula1.model.Driver
import dev.abhinav.formula1.repository.DriverRepository
import dev.abhinav.formula1.ui.theme.Formula1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var driverRepository: DriverRepository

    @OptIn(ExperimentalFoundationApi::class)
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

        val database = DriverDatabase.getInstance(this)
        val driverDao = database.driverDao()
        driverRepository = DriverRepository(driverDao)

        lifecycleScope.launch {
            drivers.forEach {
                driverRepository.addDriver(it)
            }
        }

        setContent {
            Formula1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTabIndex by remember { mutableIntStateOf (0) }
                    val pagerState = rememberPagerState { tabItems.size }

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }

                    LaunchedEffect(pagerState.currentPage) {
                        selectedTabIndex = pagerState.currentPage
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
                                    CarsList()
                                } else {
                                    CircuitsList()
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
    Driver("Alexander Albon", "Williams", "Thailand", 103, 2, 0),
    Driver("Fernando Alonso", "Aston Martin", "Spain", 402, 106, 2),
    Driver("Valtteri Bottas", "Kick Sauber", "Finland", 244, 67, 0),
    Driver("Franco Colapinto", "Williams", "Argentina", 7, 0, 0),
    Driver("Pierre Gasly", "Alpine", "France", 152, 5, 0),
    Driver("Lewis Hamilton", "Mercedes", "United Kingdom", 354, 202, 7),
    Driver("Nico Hulkenberg", "Haas", "Germany", 228, 0, 0),
    Driver("Liam Lawson", "RB", "New Zealand", 9, 0, 0),
    Driver("Charles LeClerc", "Ferrari", "Monaco", 147, 41, 0),
    Driver("Kevin Magnussen", "Haas", "Denmark", 184, 1, 0),
    Driver("Lando Norris", "McLaren", "United Kingdom", 126, 25, 0),
    Driver("Esteban Ocon", "Alpine", "France", 155, 4, 0),
    Driver("Sergio Perez", "Red Bull", "Mexico", 280, 39, 0),
    Driver("Oscar Piastri", "McLaren", "Australia", 44, 9, 0),
    Driver("George Russell", "Mercedes", "United Kingdom", 126, 15, 0),
    Driver("Carlos Sainz", "Ferrai", "Spain", 206, 26, 0),
    Driver("Lance Stroll", "Aston Martin", "Canada", 165, 3, 0),
    Driver("Yuki Tsunoda", "RB", "Japan", 88, 309, 0),
    Driver("Max Verstappen", "Red Bull", "Netherlands", 207, 111, 4),
    Driver("Zhou Guanyu", "Kick Sauber", "China", 66, 0, 0),
)