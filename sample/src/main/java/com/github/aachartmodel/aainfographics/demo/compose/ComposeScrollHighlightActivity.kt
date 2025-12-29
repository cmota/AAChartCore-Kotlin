package com.github.aachartmodel.aainfographics.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.launch
import kotlin.math.sin

private const val POINTS_COUNT = 50

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScrollHighlightScreen() {
    var pointInput by remember { mutableStateOf(TextFieldValue("12")) }
    var chartView by remember { mutableStateOf<AAChartView?>(null) }
    var showPicker by remember { mutableStateOf(false) }
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
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    TextButton(onClick = { showPicker = true }) {
                        Text("选择")
                    }
                }
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

    // 数字选择器 BottomSheet
    if (showPicker) {
        NumberPickerBottomSheet(
            currentValue = pointInput.text.toIntOrNull() ?: 0,
            range = 0 until POINTS_COUNT,
            onDismiss = { showPicker = false },
            onConfirm = { selected ->
                pointInput = TextFieldValue(selected.toString())
                showPicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NumberPickerBottomSheet(
    currentValue: Int,
    range: IntRange,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var selectedValue by remember { mutableIntStateOf(currentValue.coerceIn(range)) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // 初始滚动到当前值
    LaunchedEffect(Unit) {
        listState.scrollToItem(maxOf(0, selectedValue - 2))
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 标题栏
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("取消")
                }
                Text("选择 Point Index", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { onConfirm(selectedValue) }) {
                    Text("确定")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Picker 列表
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(range.count()) { index ->
                        val value = range.first + index
                        val isSelected = value == selectedValue
                        Text(
                            text = value.toString(),
                            style = if (isSelected) {
                                MaterialTheme.typography.headlineMedium
                            } else {
                                MaterialTheme.typography.bodyLarge
                            },
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedValue = value
                                    scope.launch {
                                        listState.animateScrollToItem(maxOf(0, value - 2))
                                    }
                                }
                                .padding(vertical = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun createChartOptions(): AAOptions {
    val categories = (0 until POINTS_COUNT).map { it.toString() }.toTypedArray()
    val sineData = (0 until POINTS_COUNT).map { i ->
        val rad = (i.toDouble() / POINTS_COUNT) * Math.PI * 2
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

            // 先隐藏 tooltip 并清除之前的 hover 状态
            chart.tooltip.hide();
            if (chart.hoverPoint) {
                chart.hoverPoint.setState('');
                chart.hoverPoint = null;
            }
            if (chart.hoverPoints) {
                chart.hoverPoints.forEach(function(p) { p.setState(''); });
                chart.hoverPoints = null;
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
            chart.hoverPoint = point;
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
