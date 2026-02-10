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
import com.github.aachartmodel.aainfographics.aatools.AAGradientColor
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class OnlyRefreshChartDataActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chartType = intent.getStringExtra("chartType") ?: AAChartType.Column.value

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnlyRefreshChartDataScreen(chartType = chartType)
                }
            }
        }
    }
}

@Composable
fun OnlyRefreshChartDataScreen(chartType: String) {
    var chartViewRef by remember { mutableStateOf<AAChartView?>(null) }
    var updateTimes by remember { mutableIntStateOf(0) }

    val chartTypeEnum = remember { convertStringToEnum(chartType) }
    val aaOptions = remember { configureAAOptions(chartTypeEnum) }

    // 定时刷新数据
    LaunchedEffect(Unit) {
        delay(2000)
        while (true) {
            val seriesArr = configureChartSeriesArray()
            chartViewRef?.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(seriesArr)
            updateTimes++
            println("图表数据正在刷新,刷新次数为:$updateTimes")
            delay(1000)
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            AAChartView(ctx).apply {
                chartViewRef = this
            }
        },
        update = { view ->
            view.post {
                view.aa_drawChartWithChartOptions(aaOptions)
            }
        }
    )
}

private fun configureAAOptions(chartType: AAChartType): AAOptions {
    val aaChartModel = AAChartModel()
        .chartType(chartType)
        .xAxisVisible(true)
        .yAxisVisible(false)
        .title("")
        .yAxisTitle("摄氏度")
        .colorsTheme(arrayOf(
            AAGradientColor.Sanguine,
            AAGradientColor.DeepSea,
            AAGradientColor.NeonGlow,
            AAGradientColor.WroughtIron
        ))
        .stacking(AAChartStackingType.Normal)
        .series(configureChartSeriesArray() as Array<Any>)

    val aaOptions = aaChartModel.aa_toAAOptions()

    when (chartType) {
        AAChartType.Column -> {
            aaOptions.plotOptions?.column
                ?.groupPadding(0f)
                ?.pointPadding(0f)
                ?.borderRadius(5f)
        }
        AAChartType.Bar -> {
            aaOptions.plotOptions?.bar
                ?.groupPadding(0f)
                ?.pointPadding(0f)
                ?.borderRadius(5f)
        }
        else -> {}
    }

    return aaOptions
}

@Suppress("UNCHECKED_CAST")
private fun configureChartSeriesArray(): Array<AASeriesElement> {
    val maxRange = 40
    val numberArr1 = arrayOfNulls<Any>(maxRange)
    val numberArr2 = arrayOfNulls<Any>(maxRange)

    val random = (Math.random() * 37 + 1).toInt()

    for (i in 0 until maxRange) {
        numberArr1[i] = sin(random * (i * Math.PI / 180)) + i * 2 * 0.01
        numberArr2[i] = cos(random * (i * Math.PI / 180)) + i * 3 * 0.01
    }

    return arrayOf(
        AASeriesElement().name("2017").data(numberArr1 as Array<Any>),
        AASeriesElement().name("2018").data(numberArr2 as Array<Any>),
        AASeriesElement().name("2019").data(numberArr1 as Array<Any>),
        AASeriesElement().name("2020").data(numberArr2 as Array<Any>)
    )
}

private fun convertStringToEnum(chartTypeStr: String): AAChartType = when (chartTypeStr) {
    AAChartType.Column.value -> AAChartType.Column
    AAChartType.Bar.value -> AAChartType.Bar
    AAChartType.Area.value -> AAChartType.Area
    AAChartType.Areaspline.value -> AAChartType.Areaspline
    AAChartType.Line.value -> AAChartType.Line
    AAChartType.Spline.value -> AAChartType.Spline
    AAChartType.Scatter.value -> AAChartType.Scatter
    else -> AAChartType.Column
}
