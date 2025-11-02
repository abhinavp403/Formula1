package dev.abhinav.formula1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dev.abhinav.formula1.ui.theme.Formula1Theme
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import kotlinx.coroutines.launch

class ARViewActivity : ComponentActivity() {
    private lateinit var file: String

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        file = when (intent.getStringExtra("car")) {
            "McLaren" -> "models/mclaren.glb"
            "Red Bull" -> "models/red_bull.glb"
            "Ferrari" -> "models/ferrari.glb"
            "Mercedes" -> "models/mercedes.glb"
            "Alpine" -> "models/alpine.glb"
            "Haas" -> "models/haas.glb"
            "Aston Martin" -> "models/aston_martin.glb"
            "Williams" -> "models/williams.glb"
            else -> {}
        }.toString()

        setContent {
            Formula1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ARSceneFormView(modifier = Modifier.fillMaxSize(), file = file)
                }
            }
        }
    }
}

@Composable
fun ARSceneFormView(modifier: Modifier = Modifier, file: String) {
    val coroutineScope = rememberCoroutineScope()

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            ArSceneView(ctx).apply {
                val sceneView = this
                planeRenderer.isEnabled = true
                planeRenderer.isVisible = true
                val modelNode = ArModelNode(
                    engine = engine,
                    placementMode = PlacementMode.INSTANT
                )

                coroutineScope.launch {
                    Log.d("DriverActivity", "Loading model...")
                    modelNode.loadModelGlbAsync(
                        glbFileLocation = file,
                        autoAnimate = true,
                        scaleToUnits = 1f,
                        centerOrigin = io.github.sceneview.math.Position(0f, 0f, 0f),
                        onError = { exception ->
                            Log.e("DriverActivity", "Failed to load model", exception)
                            Toast.makeText(ctx, "Error loading model", Toast.LENGTH_SHORT).show()
                            (ctx as? Activity)?.finish()
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
                    modelNode.anchor = hitResult.createAnchor()
                    modelNode.isVisible = true
                }
            }
        }
    )
}