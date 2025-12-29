package com.github.aachartmodel.aainfographics.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAChart
import com.github.aachartmodel.aainfographics.aachartcreator.AAOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAScrollablePlotArea
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AASubtitle
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATitle
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAXAxis
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAYAxis
import com.github.aachartmodel.aainfographics.aatools.AAJSStringPurer
import kotlin.math.sin

class ComposeScrollHighlightActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScrollHighlightScreen()
                }
            }
        }
    }
}

@Composable
private fun ScrollHighlightScreen() {
    val pointInput = remember { mutableStateOf(TextFieldValue("12")) }
    val chartViewState = remember { mutableStateOf<AAChartView?>(null) }
    val aaOptions = remember { mutableStateOf(makeOptions()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Compose + AndroidView 示例：滚动并高亮指定点", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Controls(pointInput, chartViewState)
        Spacer(modifier = Modifier.height(16.dp))
        AndroidChartView(chartViewState, aaOptions)
    }
}

@Composable
private fun Controls(
    pointInput: MutableState<TextFieldValue>,
    chartViewState: MutableState<AAChartView?>
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = pointInput.value,
            onValueChange = { pointInput.value = it },
            label = { Text("Point index (0-based)") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Button(onClick = {
            val index = pointInput.value.text.toIntOrNull() ?: 0
            scrollToPoint(chartViewState.value, index)
        }) {
            Text("滚动并高亮")
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(5, 25, 40).forEach { target ->
            Button(onClick = { scrollToPoint(chartViewState.value, target) }) {
                Text("跳到 $target")
            }
        }
        Button(onClick = { resetZoom(chartViewState.value) }) { Text("重置缩放") }
    }
}

@Composable
private fun AndroidChartView(
    chartViewState: MutableState<AAChartView?>,
    aaOptions: MutableState<AAOptions>
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        factory = { ctx ->
            AAChartView(ctx).also { view ->
                chartViewState.value = view
                view.aa_drawChartWithChartOptions(aaOptions.value)
                view.post { scrollToPoint(view, 5) }
            }
        }
    )
}

private fun makeOptions(): AAOptions {
    val pointsCount = 50
    val categories = (0 until pointsCount).map { it.toString() }.toTypedArray()
    val sineData = (0 until pointsCount).map { i ->
        val rad = (i.toDouble() / pointsCount) * Math.PI * 2
        sin(rad) * 10 + 20
    }.toTypedArray()

    val series = AASeriesElement()
        .type(AAChartType.Column)
        .name("Sine Wave")
        .data(sineData.map { it as Any }.toTypedArray())

    return AAOptions()
        .chart(
            AAChart()
                .type(AAChartType.Column)
                .margin(arrayOf(16, 12, 16, 12))
//                .scrollablePlotArea(
//                    AAScrollablePlotArea()
//                        .minWidth(1200)
//                        .scrollPositionX(0.4f)
//                )
        )
        .title(AATitle().text("Weekly Temperature"))
        .subtitle(AASubtitle().text("Scroll to a point then highlight it"))
        .xAxis(
            AAXAxis()
                .categories(categories)
                .tickLength(0)
//                .minPadding(0.05f)
//                .maxPadding(0.05f)
        )
        .yAxis(
            AAYAxis()
                .title(AATitle().text("Temperature (°C)"))
        )
        .tooltip(AATooltip().shared(false))
        .series(arrayOf(series) as Array<Any>)
}

private fun scrollToPoint(chartView: AAChartView?, targetIndex: Int) {
    val safeView = chartView ?: return
    val js = AAJSStringPurer.pureJavaScriptFunctionString(
        """
            	// Scroll the x-axis to the target point and highlight it.
		function scrollToPoint(targetIndex) {
const chart = aaGlobalChart;
			const series = chart.series[0];
			const point = series.points[targetIndex];
			if (!point) {
				alert("Point does not exist. Try a smaller index.");
				return;
			}

			// Determine a small padding so the point is nicely centered.
			const pad = (series.closestPointRange || 1) * 0.6;
			const min = point.x - pad;
			const max = point.x + pad;

			chart.xAxis[0].setExtremes(min, max, true, { duration: 300 });

			// Clear existing hovers then highlight the target.
			series.points.forEach((p) => p.setState(""));
			point.setState("hover");
			chart.tooltip.refresh(point);
		}

scrollToPoint($targetIndex);
        """.trimIndent()
    )
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        safeView.evaluateJavascript(js) { }
    } else {
        safeView.loadUrl("javascript:$js")
    }
}

private fun resetZoom(chartView: AAChartView?) {
    val safeView = chartView ?: return
    val js = AAJSStringPurer.pureJavaScriptFunctionString(
        "(function(){" +
            "var chart = this;" +
            "chart.xAxis[0].setExtremes(null, null, true, {duration:200});" +
        "})()"
    )
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        safeView.evaluateJavascript(js) { }
    } else {
        safeView.loadUrl("javascript:$js")
    }
}

