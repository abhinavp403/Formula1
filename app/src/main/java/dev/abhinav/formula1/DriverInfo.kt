package dev.abhinav.formula1

import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import dev.abhinav.formula1.model.Driver

val headers = listOf("Name", "Team", "Country", "GP Entered", "Podiums")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DriverInfo(
    drivers: List<Driver>
) {
    val pagerState = rememberPagerState(initialPage = 0) { drivers.size }
    val context = LocalContext.current
    val dominantColor = remember { mutableStateOf(Color.Black) }

    LaunchedEffect(Unit) {
        val imageBitmap = BitmapFactory.decodeResource(context.resources, drivers[0].image)
        val palette = Palette.from(imageBitmap).generate()
        dominantColor.value = Color(palette.getDominantColor(Color.Black.toArgb()))
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(dominantColor.value)
    ) {
        PageIndicator(
            pageCount = drivers.size,
            currentPage = pagerState.currentPage,
        )

        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(LocalOverscrollFactory provides null) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 24.dp)
                ) { currentPage ->
                    Card(
                        modifier = Modifier.wrapContentSize().padding(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(drivers[currentPage].image),
                            contentDescription = "driver",
                            modifier = Modifier.padding(32.dp)
                        )

                        LazyColumn(
                            Modifier.padding(8.dp)
                        ) {
                            items(headers.size) { index ->

                                val data = when (index) {
                                    0 -> drivers[currentPage].name
                                    1 -> drivers[currentPage].team
                                    2 -> drivers[currentPage].country
                                    3 -> drivers[currentPage].gpEntered.toString()
                                    4 -> drivers[currentPage].podiums.toString()
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
                                        isHeader = true
                                    )

                                    // Data Column
                                    TableCell(
                                        text = data,
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

@Composable
fun TableCell(text: String, isHeader: Boolean = false) {
    Text(
        text = text,
        modifier = Modifier.padding(4.dp),
        style = if (isHeader) {
            MaterialTheme.typography.titleMedium
        } else {
            MaterialTheme.typography.bodyMedium
        }
    )
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = Modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color.Gray else Color.Black)
    )
}