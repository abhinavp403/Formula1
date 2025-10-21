package dev.abhinav.formula1

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dev.abhinav.formula1.model.CarWithDrivers
import dev.abhinav.formula1.repository.CarRepository
import dev.abhinav.formula1.ui.theme.Formula1Theme
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import io.github.sceneview.ar.node.PlacementMode

class DriverActivity : ComponentActivity() {

    private val carRepository: CarRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val carName = intent.getStringExtra("car")!!

        setContent {
            Formula1Theme {
//                Surface(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    val driverInfoList = remember { mutableStateOf<CarWithDrivers?>(null) }
//
//                    LaunchedEffect(carName) {
//                        driverInfoList.value = carRepository.getDriverInfo(carName)
//                    }
//
//                    if (driverInfoList.value == null) {
//                        LoadingIndicator()
//                    } else if (driverInfoList.value?.drivers!!.isNotEmpty()) {
//                        DriverInfo(driverInfoList.value!!.drivers)
//                    } else {
//                        Text("No driver information found for $carName.")
//                    }
//                }
                ARSceneFormView(modifier = Modifier.fillMaxSize())
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

@Composable
fun ARSceneFormView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            ArSceneView(ctx).apply {
                val sceneView = this
                planeRenderer.isEnabled = true
                planeRenderer.isVisible = true
                Log.d("DriverActivity", "AR Scene initialized")

                val modelNode = ArModelNode(
                    engine = engine,
                    placementMode = PlacementMode.INSTANT
                )

                coroutineScope.launch {
                    Log.d("DriverActivity", "Loading model...")
                    modelNode.loadModelGlbAsync(
                        glbFileLocation = "models/car.glb",
                        autoAnimate = true,
                        scaleToUnits = 1f,
                        centerOrigin = io.github.sceneview.math.Position(0f, 0f, 0f),
                        onError = { exception ->
                            Log.e("DriverActivity", "Failed to load model", exception)
                        },
                        onLoaded = { modelInstance ->
                            Log.d("DriverActivity", "Model loaded successfully")
                            sceneView.addChild(modelNode)
                        }
                    )
                }

                // Add model to scene immediately
                sceneView.addChild(modelNode)

                // Place model when tapping on AR plane
                onTapAr = { hitResult, motionEvent ->
                    Log.d("DriverActivity", "Tap detected at plane")
                    modelNode.anchor = hitResult.createAnchor()
                    modelNode.isVisible = true
                }

                Log.d("DriverActivity", "Setup complete")
            }
        }
    )
}