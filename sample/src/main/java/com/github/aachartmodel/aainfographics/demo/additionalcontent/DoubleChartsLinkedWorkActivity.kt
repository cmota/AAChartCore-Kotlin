package com.github.aachartmodel.aainfographics.demo.additionalcontent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AALabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.github.aachartmodel.aainfographics.aatools.AAColor
import com.github.aachartmodel.aainfographics.aatools.AAGradientColor
import kotlin.math.sin

class DoubleChartsLinkedWorkActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F7FA)) {
                    DoubleChartsLinkedWorkScreen()
                }
            }
        }
    }
}

private val gradientColorNamesArr = arrayOf(
    "oceanBlue", "sanguine", "lusciousLime", "purpleLake", "freshPapaya",
    "ultramarine", "pinkSugar", "lemonDrizzle", "victoriaPurple", "springGreens",
    "mysticMauve", "reflexSilver", "neonGlowColor", "berrySmoothieColor",
    "newLeaf", "cottonCandy", "pixieDust", "fizzyPeach", "sweetDream",
    "firebrick", "wroughtIron", "deepSea", "coastalBreeze", "eveningDelight"
)

private val gradientColorsArr = arrayOf<Any>(
    AAGradientColor.OceanBlue, AAGradientColor.Sanguine, AAGradientColor.LusciousLime,
    AAGradientColor.PurpleLake, AAGradientColor.FreshPapaya, AAGradientColor.Ultramarine,
    AAGradientColor.PinkSugar, AAGradientColor.LemonDrizzle, AAGradientColor.VictoriaPurple,
    AAGradientColor.SpringGreens, AAGradientColor.MysticMauve, AAGradientColor.ReflexSilver,
    AAGradientColor.NewLeaf, AAGradientColor.CottonCandy, AAGradientColor.PixieDust,
    AAGradientColor.FizzyPeach, AAGradientColor.SweetDream, AAGradientColor.Firebrick,
    AAGradientColor.WroughtIron, AAGradientColor.DeepSea, AAGradientColor.CoastalBreeze,
    AAGradientColor.EveningDelight, AAGradientColor.NeonGlow, AAGradientColor.BerrySmoothie
)

@Composable
private fun DoubleChartsLinkedWorkScreen() {
    var chartView2 by remember { mutableStateOf<AAChartView?>(null) }
    var selectedGradientColor by remember { mutableStateOf<Any>(AAColor.Red) }
    var selectedColorName by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📊 双图表联动",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A2E)
            )
        )
        Text(
            text = "点击或滑动上方图表，下方图表会联动更新",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 上方图表 - 颜色选择器
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                factory = { ctx ->
                    AAChartView(ctx).apply {
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        callBack = object : AAChartView.AAChartViewCallBack {
                            override fun chartViewDidFinishLoad(aaChartView: AAChartView) {}

                            override fun chartViewClickEventMessage(
                                aaChartView: AAChartView,
                                clickEventMessage: AAClickEventMessageModel
                            ) {
                                handleChartInteraction(clickEventMessage.index) { color, name ->
                                    selectedGradientColor = color
                                    selectedColorName = name
                                    updateChart2(chartView2, color, name)
                                }
                            }

                            override fun chartViewMoveOverEventMessage(
                                aaChartView: AAChartView,
                                moveOverEventMessage: AAMoveOverEventMessageModel
                            ) {
                                handleChartInteraction(moveOverEventMessage.index) { color, name ->
                                    selectedGradientColor = color
                                    selectedColorName = name
                                    updateChart2(chartView2, color, name)
                                }
                            }
                        }
                    }
                },
                update = { view ->
                    view.post {
                        view.aa_drawChartWithChartOptions(configureChartOptions1())
                    }
                }
            )
        }

        // 下方图表 - 联动显示
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                factory = { ctx ->
                    AAChartView(ctx).apply {
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        chartView2 = this
                    }
                },
                update = { view ->
                    view.post {
                        view.aa_drawChartWithChartOptions(configureChartOptions2())
                    }
                }
            )
        }
    }
}

private inline fun handleChartInteraction(
    index: Int?,
    onUpdate: (color: Any, name: String) -> Unit
) {
    index?.let { idx ->
        if (idx in gradientColorsArr.indices) {
            onUpdate(gradientColorsArr[idx], gradientColorNamesArr[idx])
        }
    }
}

private fun updateChart2(chartView: AAChartView?, color: Any, name: String?) {
    chartView?.let { view ->
        val seriesData = configureSeriesDataArray(color)
        val categories = configureXAxisCategoresDataArray(name)
        view.aa_updateXAxisCategories(categories, false)
        view.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(
            arrayOf(AASeriesElement().data(seriesData as Array<Any>)),
            false
        )
    }
}

private fun configureSeriesDataArray(selectedColor: Any): Array<AADataElement> {
    val maxRange = 40
    val random = (Math.random() * 37 + 1).toInt()
    return Array(maxRange) { i ->
        val y = sin(random * (i * Math.PI / 180)) + i * 2 * 0.01
        AADataElement().color(selectedColor).y(y.toFloat())
    }
}

private fun configureXAxisCategoresDataArray(colorName: String?): Array<String> {
    return Array(40) { "${colorName ?: ""}$it" }
}

private fun configureChartOptions1(): AAOptions {
    val aaChartModel = AAChartModel()
        .chartType(AAChartType.Column)
        .dataLabelsEnabled(false)
        .categories(gradientColorNamesArr)
        .borderRadius(4f)
        .legendEnabled(false)
        .colorsTheme(gradientColorsArr)
        .clickEventEnabled(true)
        .touchEventEnabled(true)
        .yAxisTitle("Random Number")
        .yAxisMax(210)
        .series(arrayOf(
            AASeriesElement()
                .name("")
                .data(arrayOf(149.9, 154, 106.4, 129.2, 144.0, 154, 135.6, 154, 154, 154, 95.6, 54.4))
                .colorByPoint(true)
                .borderWidth(2f)
                .dataLabels(
                    AADataLabels()
                        .enabled(true)
                        .verticalAlign(AAChartVerticalAlignType.Middle)
                        .x(0f)
                        .y(-10f)
                        .style(
                            AAStyle()
                                .color(AAColor.Red)
                                .fontSize(12f)
                                .fontWeight(AAChartFontWeightType.Thin)
                        )
                )
        ))

    val aaOptions = aaChartModel.aa_toAAOptions()
    val aaLabels = AALabels()
        .autoRotation(false)
        .style(AAStyle().fontSize(12).color("#999999"))

    aaOptions.xAxis?.apply {
        labels(aaLabels)
            .lineColor("#EEEEEE")
            .lineWidth(0.5)
    }
    aaOptions.yAxis?.apply {
        minorGridLineColor("#EEEEEE")
            .minorGridLineWidth(0.5)
            .labels(aaLabels)
            .startOnTick(false)
            .endOnTick(false)
    }
    return aaOptions
}

private fun configureChartOptions2(): AAOptions {
    val aaChartModel = AAChartModel()
        .chartType(AAChartType.Column)
        .title("")
        .yAxisTitle("")
        .legendEnabled(false)
        .yAxisGridLineWidth(0f)
        .series(arrayOf(
            AASeriesElement()
                .name("Tokyo")
                .data(arrayOf(
                    211, 183, 157, 133, 111, 91, 73, 57, 43, 31, 21, 13,
                    211, 183, 157, 133, 111, 91, 73, 57, 43, 31, 21, 13
                ))
        ))

    val aaOptions = aaChartModel.aa_toAAOptions()
    aaOptions.plotOptions?.column?.groupPadding = 0f
    return aaOptions
}
