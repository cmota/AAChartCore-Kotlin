package com.github.aachartmodel.aainfographics.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.demo.chartcomposer.BasicChartComposer
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

class ComposeChartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AAChartViewCompose()
                }
            }
        }
    }
}

@Composable
fun AAChartViewCompose() {
    val context = LocalContext.current
    // Use AndroidView to host the existing AAChartView inside Compose.
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            AAChartView(ctx).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { view ->
            // Draw once the view has a size; avoids blank screen on first compose.
            view.post {
                view.aa_drawChartWithChartModel(BasicChartComposer.simpleSplineModel())
            }
        }
    )
}
