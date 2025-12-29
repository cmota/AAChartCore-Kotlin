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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AAOptions
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAChart
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AASubtitle
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATitle
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAXAxis
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAYAxis
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
    var pointInput by remember { mutableStateOf(TextFieldValue("12")) }
    var chartView by remember { mutableStateOf<AAChartView?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Compose + AndroidView 示例：滚动并高亮指定点",
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 输入框和滚动按钮
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = pointInput,
                onValueChange = { pointInput = it },
                label = { Text("Point index (0-based)") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Button(onClick = {
                val index = pointInput.text.toIntOrNull() ?: 0
                chartView?.scrollToPoint(index)
            }) {
                Text("滚动并高亮")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // 快捷按钮
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(5, 25, 40).forEach { target ->
                Button(onClick = { chartView?.scrollToPoint(target) }) {
                    Text("跳到 $target")
                }
            }
            Button(onClick = { chartView?.resetZoom() }) {
                Text("重置缩放")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 图表视图
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp),
            factory = { ctx ->
                AAChartView(ctx).apply {
                    layoutParams = android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // 保存引用以便按钮点击时使用
                    chartView = this
                }
            },
            update = { view ->
                view.post {
                    view.aa_drawChartWithChartOptions(createChartOptions())
                    view.scrollToPoint(5)
                }
            }
        )
    }
}

private fun createChartOptions(): AAOptions {
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
        .chart(AAChart().type(AAChartType.Column))
        .title(AATitle().text("Weekly Temperature"))
        .subtitle(AASubtitle().text("Scroll to a point then highlight it"))
        .xAxis(
            AAXAxis()
                .categories(categories)
                .tickLength(0)
        )
        .yAxis(AAYAxis().title(AATitle().text("Temperature (°C)")))
        .tooltip(AATooltip().shared(false))
        .series(arrayOf(series) as Array<Any>)
}

private fun AAChartView.scrollToPoint(targetIndex: Int) {
    val js = """
        (function() {
            const chart = aaGlobalChart;
            if (!chart) return;
            
            const series = chart.series[0];
            const point = series.points[targetIndex];
            if (!point) {
                alert("Point does not exist. Try a smaller index.");
                return;
            }

            const pad = (series.closestPointRange || 1) * 0.6;
            const min = point.x - pad;
            const max = point.x + pad;

            chart.xAxis[0].setExtremes(min, max, true, { duration: 300 });

            // 重置所有点颜色，选中点变红色
            series.points.forEach(function(p) { 
                p.setState("");
                p.update({ color: null }, false);
            });
            point.update({ color: '#FF0000' }, false);
            point.setState("hover");
            chart.tooltip.refresh(point);
            chart.redraw();
        })();
    """.trimIndent().replace("targetIndex", targetIndex.toString())

    evaluateJavascript(js, null)
}

private fun AAChartView.resetZoom() {
    val js = """
        (function() {
            const chart = aaGlobalChart;
            if (!chart) return;
            chart.xAxis[0].setExtremes(null, null, true, { duration: 200 });
        })();
    """.trimIndent()

    evaluateJavascript(js, null)
}
