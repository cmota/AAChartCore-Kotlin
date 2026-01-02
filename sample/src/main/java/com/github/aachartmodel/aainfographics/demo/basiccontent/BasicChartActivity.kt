/**
 * ◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉ ...... SOURCE CODE ......◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉
 * ◉◉◉...................................................       ◉◉◉
 * ◉◉◉   https://github.com/AAChartModel/AAChartCore            ◉◉◉
 * ◉◉◉   https://github.com/AAChartModel/AAChartCore-Kotlin     ◉◉◉
 * ◉◉◉...................................................       ◉◉◉
 * ◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉ ...... SOURCE CODE ......◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉◉
 */

/**

 * -------------------------------------------------------------------------------
 *
 *  🌕 🌖 🌗 🌘  ❀❀❀   WARM TIPS!!!   ❀❀❀ 🌑 🌒 🌓 🌔
 *
 * Please contact me on GitHub,if there are any problems encountered in use.
 * GitHub Issues : https://github.com/AAChartModel/AAChartCore-Kotlin/issues
 * -------------------------------------------------------------------------------
 * And if you want to contribute for this project, please contact me as well
 * GitHub        : https://github.com/AAChartModel
 * StackOverflow : https://stackoverflow.com/users/7842508/codeforu
 * JianShu       : http://www.jianshu.com/u/f1e6753d4254
 * SegmentFault  : https://segmentfault.com/u/huanghunbieguan
 *
 * -------------------------------------------------------------------------------

 */
package com.github.aachartmodel.aainfographics.demo.basiccontent

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.*
import com.github.aachartmodel.aainfographics.demo.chartcomposer.BasicChartComposer

open class BasicChartActivity : ComponentActivity() {

    protected open val useAdvancedUpdating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chartType = intent.getStringExtra("chartType") ?: AAChartType.Column.value
        val position = intent.getIntExtra("position", 0)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BasicChartScreen(
                        chartType = chartType,
                        position = position,
                        useAdvancedUpdating = useAdvancedUpdating
                    )
                }
            }
        }
    }
}

@Composable
fun BasicChartScreen(
    chartType: String,
    position: Int,
    useAdvancedUpdating: Boolean = false
) {
    val context = LocalContext.current
    val initialModel = remember { configureAAChartModel(chartType, position) }
    var aaChartModel by remember { mutableStateOf(initialModel) }
    var chartViewRef by remember { mutableStateOf<AAChartView?>(null) }

    // Stacking type state
    var stackingType by remember { mutableStateOf(AAChartStackingType.False) }

    // Corner style state (for Column/Bar)
    var borderRadius by remember { mutableFloatStateOf(1f) }

    // Marker symbol state (for other chart types)
    var markerSymbol by remember { mutableStateOf(AAChartSymbolType.Circle) }

    // Switch states
    var xReversed by remember { mutableStateOf(false) }
    var yReversed by remember { mutableStateOf(false) }
    var polar by remember { mutableStateOf(false) }
    var inverted by remember { mutableStateOf(false) }
    var dataLabelsEnabled by remember { mutableStateOf(false) }
    var markerHidden by remember { mutableStateOf(false) }

    val isBarOrColumn = chartType == AAChartType.Column.value || chartType == AAChartType.Bar.value

    // Helper function to update chart
    fun updateChart(options: Any? = null) {
        if (useAdvancedUpdating && options != null) {
            chartViewRef?.aa_updateChartWithOptions(options, true)
        } else {
            chartViewRef?.aa_refreshChartWithChartModel(aaChartModel)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Chart View
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { ctx ->
                AAChartView(ctx).apply {
                    setBackgroundColor(0)
                    layoutParams = android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    chartViewRef = this
                }
            },
            update = { view ->
                view.post {
                    view.aa_drawChartWithChartModel(aaChartModel)
                }
            }
        )

        // Controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Stacking Type
            Text("Stacking Type", style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StackingOption.entries.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = stackingType == option.type,
                            onClick = {
                                stackingType = option.type
                                aaChartModel = aaChartModel.stacking(stackingType)
                                val opts = AAPlotOptions().series(AASeries().stacking(stackingType))
                                updateChart(opts)
                            }
                        )
                        Text(option.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Corner Style (for Column/Bar) or Marker Symbol (for others)
            if (isBarOrColumn) {
                Text("Corner Style", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CornerOption.entries.forEach { option ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = borderRadius == option.radius,
                                onClick = {
                                    borderRadius = option.radius
                                    aaChartModel = aaChartModel.borderRadius(borderRadius)
                                    val opts = if (chartType == AAChartType.Column.value) {
                                        AAPlotOptions().column(AAColumn().borderRadius(borderRadius))
                                    } else {
                                        AAPlotOptions().bar(AABar().borderRadius(borderRadius))
                                    }
                                    updateChart(opts)
                                }
                            )
                            Text(option.label, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                Text("Marker Symbol", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MarkerOption.entries.forEach { option ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = markerSymbol == option.symbol,
                                onClick = {
                                    markerSymbol = option.symbol
                                    aaChartModel = aaChartModel.markerSymbol(markerSymbol)
                                    val opts = AAPlotOptions().series(AASeries().marker(AAMarker().symbol(markerSymbol.value)))
                                    updateChart(opts)
                                }
                            )
                            Text(option.label, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Switches
            SwitchRow("X Reversed", xReversed) {
                xReversed = it
                aaChartModel = aaChartModel.xAxisReversed(it)
                updateChart(AAXAxis().reversed(it))
            }
            SwitchRow("Y Reversed", yReversed) {
                yReversed = it
                aaChartModel = aaChartModel.yAxisReversed(it)
                updateChart(AAYAxis().reversed(it))
            }
            SwitchRow("Polar", polar) {
                polar = it
                aaChartModel = aaChartModel.polar(it)
                if (useAdvancedUpdating) {
                    val aaChart = AAChart().polar(it).inverted(inverted)
                    val options: Any = when (chartType) {
                        AAChartType.Column.value -> {
                            val p = if (it) 0f else 0.1f
                            val g = if (it) 0.005f else 0.2f
                            AAOptions().chart(aaChart).plotOptions(AAPlotOptions().column(AAColumn().pointPadding(p).groupPadding(g)))
                        }
                        AAChartType.Bar.value -> {
                            val p = if (it) 0f else 0.1f
                            val g = if (it) 0.005f else 0.2f
                            AAOptions().chart(aaChart).plotOptions(AAPlotOptions().bar(AABar().pointPadding(p).groupPadding(g)))
                        }
                        else -> aaChart
                    }
                    updateChart(options)
                } else {
                    updateChart()
                }
            }
            SwitchRow("Inverted", inverted) {
                inverted = it
                aaChartModel = aaChartModel.inverted(it)
                if (useAdvancedUpdating && chartType == AAChartType.Bar.value) {
                    Toast.makeText(context, "⚠️ inverted is useless for Bar Chart", Toast.LENGTH_SHORT).show()
                }
                updateChart(AAChart().inverted(it).polar(polar))
            }
            SwitchRow("Data Labels", dataLabelsEnabled) {
                dataLabelsEnabled = it
                aaChartModel = aaChartModel.dataLabelsEnabled(it)
                val opts = AAPlotOptions().series(AASeries().dataLabels(AADataLabels().enabled(it)))
                updateChart(opts)
            }
            if (!isBarOrColumn) {
                SwitchRow("Hide Marker", markerHidden) {
                    markerHidden = it
                    aaChartModel = aaChartModel.markerRadius(if (it) 0f else 6f)
                    val marker = if (it) AAMarker().enabled(false) else AAMarker().enabled(true).radius(6f)
                    val opts = AAPlotOptions().series(AASeries().marker(marker))
                    updateChart(opts)
                }
            }
        }
    }
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

private fun configureAAChartModel(chartType: String, position: Int): AAChartModel {
    val chartTypeEnum = convertStringToEnum(chartType)

    return when {
        (chartType == AAChartType.Area.value || chartType == AAChartType.Line.value) && (position == 4 || position == 5) ->
            BasicChartComposer.configureStepAreaChartAndStepLineChart().chartType(chartTypeEnum)
        chartType == AAChartType.Column.value || chartType == AAChartType.Bar.value ->
            BasicChartComposer.configureColumnChartAndBarChart().chartType(chartTypeEnum)
        chartType == AAChartType.Area.value || chartType == AAChartType.Areaspline.value ->
            BasicChartComposer.configureAreaChartAndAreasplineChartStyle(chartType).chartType(chartTypeEnum)
        chartType == AAChartType.Line.value || chartType == AAChartType.Spline.value ->
            BasicChartComposer.configureLineChartAndSplineChartStyle(chartType).chartType(chartTypeEnum)
        else ->
            BasicChartComposer.configureAreaChart().chartType(chartTypeEnum)
    }
}

private fun convertStringToEnum(chartTypeStr: String): AAChartType {
    return when (chartTypeStr) {
        AAChartType.Column.value -> AAChartType.Column
        AAChartType.Bar.value -> AAChartType.Bar
        AAChartType.Area.value -> AAChartType.Area
        AAChartType.Areaspline.value -> AAChartType.Areaspline
        AAChartType.Line.value -> AAChartType.Line
        AAChartType.Spline.value -> AAChartType.Spline
        else -> AAChartType.Column
    }
}

private enum class StackingOption(val label: String, val type: AAChartStackingType) {
    None("None", AAChartStackingType.False),
    Normal("Normal", AAChartStackingType.Normal),
    Percent("Percent", AAChartStackingType.Percent)
}

private enum class CornerOption(val label: String, val radius: Float) {
    Square("Square", 1f),
    Rounded("Rounded", 10f),
    Wedge("Wedge", 1000f)
}

private enum class MarkerOption(val label: String, val symbol: AAChartSymbolType) {
    Circle("●", AAChartSymbolType.Circle),
    Square("■", AAChartSymbolType.Square),
    Diamond("◆", AAChartSymbolType.Diamond),
    Triangle("▲", AAChartSymbolType.Triangle),
    TriangleDown("▼", AAChartSymbolType.TriangleDown)
}
