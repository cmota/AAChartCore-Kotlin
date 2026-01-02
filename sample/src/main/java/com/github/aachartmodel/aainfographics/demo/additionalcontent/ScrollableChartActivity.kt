package com.github.aachartmodel.aainfographics.demo.additionalcontent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.*
import com.github.aachartmodel.aainfographics.aatools.AAJSStringPurer
import kotlin.math.sin

class ScrollableChartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chartType = intent.getStringExtra("chartType") ?: AAChartType.Column.value
        val position = intent.getIntExtra("position", 0)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScrollableChartScreen(chartType = chartType, position = position)
                }
            }
        }
    }
}

@Composable
fun ScrollableChartScreen(chartType: String, position: Int) {
    val chartTypeEnum = remember { convertStringToEnum(chartType) }
    val aaOptions = remember { configureAAOptions(chartTypeEnum, position) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx -> AAChartView(ctx) },
        update = { view ->
            view.post { view.aa_drawChartWithChartOptions(aaOptions) }
        }
    )
}

private fun configureAAOptions(chartType: AAChartType, position: Int): AAOptions {
    // Bar 类型有特殊配置
    if (chartType == AAChartType.Bar) {
        return configureBarChartOptions()
    }

    val aaChartModel = AAChartModel()
        .chartType(chartType)
        .title("")
        .yAxisTitle("")
        .legendEnabled(false)
        .yAxisGridLineWidth(0f)
        .scrollablePlotArea(
            AAScrollablePlotArea()
                .minWidth(3000)
                .scrollPositionX(1f)
        )
        .series(arrayOf(
            AASeriesElement()
                .name("Tokyo")
                .data(configureSeriesDataArray() as Array<Any>)
        ))

    configureStyleForChartType(aaChartModel, chartType, position)
    return aaChartModel.aa_toAAOptions()
}

private fun configureBarChartOptions(): AAOptions {
    val pureJSStr = AAJSStringPurer.pureJavaScriptFunctionString(
        "Source: <a href=\"https://highcharts.uservoice.com/forums/55896-highcharts-javascript-api\">UserVoice</a>"
    )
    val element = AASeriesElement()
        .data(arrayOf(
            arrayOf("Gantt chart", 1000),
            arrayOf("Autocalculation and plotting of trend lines", 575),
            arrayOf("Allow navigator to have multiple data series", 523),
            arrayOf("Implement dynamic font size", 427),
            arrayOf("Multiple axis alignment control", 399),
            arrayOf("Stacked area (spline etc) in irregular datetime series", 309),
            arrayOf("Adapt chart height to legend height", 278),
            arrayOf("Export charts in excel sheet", 239),
            arrayOf("Toggle legend box", 235),
            arrayOf("Venn Diagram", 203),
            arrayOf("Add ability to change Rangeselector position", 182),
            arrayOf("Draggable legend box", 157),
            arrayOf("Sankey Diagram", 149),
            arrayOf("Add Navigation bar for Y-Axis in Highstock", 144),
            arrayOf("Grouped x-axis", 143),
            arrayOf("ReactJS plugin", 137),
            arrayOf("3D surface charts", 134),
            arrayOf("Draw lines over a stock chart, for analysis purpose", 118),
            arrayOf("Data module for database tables", 118),
            arrayOf("Draggable points", 117)
        ))

    return AAOptions()
        .chart(AAChart()
            .type(AAChartType.Bar)
            .scrollablePlotArea(AAScrollablePlotArea().minHeight(900)))
        .title(AATitle().text("Most popular ideas by April 2016"))
        .subtitle(AASubtitle().text(pureJSStr))
        .xAxis(AAXAxis().type(AAChartAxisType.Category))
        .series(arrayOf(element))
}

private fun configureStyleForChartType(model: AAChartModel, chartType: AAChartType, position: Int) {
    when {
        (chartType == AAChartType.Area || chartType == AAChartType.Line) && (position == 4 || position == 5) -> {
            configureStepChart(model)
        }
        chartType == AAChartType.Column -> {
            configureColumnChart(model)
        }
        chartType == AAChartType.Area || chartType == AAChartType.Areaspline -> {
            configureAreaChart(model, chartType)
        }
        chartType == AAChartType.Line || chartType == AAChartType.Spline -> {
            configureLineChart(model, chartType)
        }
    }
}

private fun configureStepChart(model: AAChartModel) {
    model.series(arrayOf(
        AASeriesElement().name("Tokyo").step(true)
            .data(arrayOf(149.9, 171.5, 106.4, 129.2, 144.0, 176.0, 135.6, 188.5, 276.4, 214.1, 95.6, 54.4)),
        AASeriesElement().name("NewYork").step(true)
            .data(arrayOf(83.6, 78.8, 188.5, 93.4, 106.0, 84.5, 105.0, 104.3, 131.2, 153.5, 226.6, 192.3)),
        AASeriesElement().name("London").step(true)
            .data(arrayOf(48.9, 38.8, 19.3, 41.4, 47.0, 28.3, 59.0, 69.6, 52.4, 65.2, 53.3, 72.2))
    ))
}

private fun configureColumnChart(model: AAChartModel) {
    model
        .categories(arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
        .legendEnabled(true)
        .colorsTheme(arrayOf("#fe117c", "#ffc069", "#06caf4", "#7dffc0"))
        .animationType(AAChartAnimationType.EaseOutCubic)
        .animationDuration(1200)
}

private fun configureAreaChart(model: AAChartModel, chartType: AAChartType) {
    model
        .animationType(AAChartAnimationType.EaseOutQuart)
        .legendEnabled(true)
        .markerRadius(5f)
        .markerSymbol(AAChartSymbolType.Circle)
        .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)

    if (chartType == AAChartType.Areaspline) {
        model
            .animationType(AAChartAnimationType.EaseFrom)
            .series(arrayOf(
                AASeriesElement().name("Predefined symbol")
                    .data(arrayOf(0.45, 0.43, 0.50, 0.55, 0.58, 0.62, 0.83, 0.39, 0.56, 0.67, 0.50, 0.34, 0.50, 0.67, 0.58, 0.29, 0.46, 0.23, 0.47, 0.46, 0.38, 0.56, 0.48, 0.36)),
                AASeriesElement().name("Image symbol")
                    .data(arrayOf(0.38, 0.31, 0.32, 0.32, 0.64, 0.66, 0.86, 0.47, 0.52, 0.75, 0.52, 0.56, 0.54, 0.60, 0.46, 0.63, 0.54, 0.51, 0.58, 0.64, 0.60, 0.45, 0.36, 0.67)),
                AASeriesElement().name("Base64 symbol (*)")
                    .data(arrayOf(0.46, 0.32, 0.53, 0.58, 0.86, 0.68, 0.85, 0.73, 0.69, 0.71, 0.91, 0.74, 0.60, 0.50, 0.39, 0.67, 0.55, 0.49, 0.65, 0.45, 0.64, 0.47, 0.63, 0.64)),
                AASeriesElement().name("Custom symbol")
                    .data(arrayOf(0.60, 0.51, 0.52, 0.53, 0.64, 0.84, 0.65, 0.68, 0.63, 0.47, 0.72, 0.60, 0.65, 0.74, 0.66, 0.65, 0.71, 0.59, 0.65, 0.77, 0.52, 0.53, 0.58, 0.53))
            ))
    }
}

private fun configureLineChart(model: AAChartModel, chartType: AAChartType) {
    model
        .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank)
        .markerRadius(6f)

    when (chartType) {
        AAChartType.Line -> {
            model.series(arrayOf(
                AASeriesElement().name("Hestavollane")
                    .data(arrayOf(0.2, 0.8, 0.8, 0.8, 1, 1.3, 1.5, 2.9, 1.9, 2.6, 1.6, 3, 4, 3.6,
                        5.5, 6.2, 5.5, 4.5, 4, 3.1, 2.7, 4, 2.7, 2.3, 2.3, 4.1, 7.7, 7.1,
                        5.6, 6.1, 5.8, 8.6, 7.2, 9, 10.9, 11.5, 11.6, 11.1, 12, 12.3, 10.7,
                        9.4, 9.8, 9.6, 9.8, 9.5, 8.5, 7.4, 7.6)),
                AASeriesElement().name("Vik")
                    .data(arrayOf(0, 0, 0.6, 0.9, 0.8, 0.2, 0, 0, 0, 0.1, 0.6, 0.7, 0.8, 0.6, 0.2,
                        0, 0.1, 0.3, 0.3, 0, 0.1, 0, 0, 0, 0.2, 0.1, 0, 0.3, 0, 0.1, 0.2,
                        0.1, 0.3, 0.3, 0, 3.1, 3.1, 2.5, 1.5, 1.9, 2.1, 1, 2.3, 1.9, 1.2,
                        0.7, 1.3, 0.4, 0.3))
            ))
        }
        AAChartType.Spline -> {
            model
                .animationType(AAChartAnimationType.SwingFromTo)
                .series(arrayOf(
                    AASeriesElement().name("Tokyo").lineWidth(7f).data(arrayOf(50, 320, 230, 370, 230, 400)),
                    AASeriesElement().name("Berlin").lineWidth(7f).data(arrayOf(80, 390, 210, 340, 240, 350)),
                    AASeriesElement().name("New York").lineWidth(7f).data(arrayOf(100, 370, 180, 280, 260, 300)),
                    AASeriesElement().name("London").lineWidth(7f).data(arrayOf(130, 350, 160, 310, 250, 268))
                ))
        }
        else -> {}
    }
}

private fun configureSeriesDataArray(): Array<AADataElement?> {
    val maxRange = 388
    val numberArr = arrayOfNulls<AADataElement>(maxRange)
    val random = (Math.random() * 37 + 1).toInt()

    for (i in 0 until maxRange) {
        val y = sin(random * (i * Math.PI / 180)) + i * 2 * 0.01
        numberArr[i] = AADataElement().y(y.toFloat())
    }
    return numberArr
}

private fun convertStringToEnum(chartTypeStr: String): AAChartType = when (chartTypeStr) {
    AAChartType.Column.value -> AAChartType.Column
    AAChartType.Bar.value -> AAChartType.Bar
    AAChartType.Area.value -> AAChartType.Area
    AAChartType.Areaspline.value -> AAChartType.Areaspline
    AAChartType.Line.value -> AAChartType.Line
    AAChartType.Spline.value -> AAChartType.Spline
    else -> AAChartType.Column
}
