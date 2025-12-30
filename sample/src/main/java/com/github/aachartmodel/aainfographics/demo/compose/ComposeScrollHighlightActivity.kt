package com.github.aachartmodel.aainfographics.demo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
private val PrimaryBlue = Color(0xFF2196F3)
private val PrimaryBlueDark = Color(0xFF1976D2)
private val LightBlue = Color(0xFFE3F2FD)
private val AccentOrange = Color(0xFFFF9800)
private val AccentOrangeDark = Color(0xFFF57C00)

@Composable
private fun ScaleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryBlue,
    pressedColor: Color = PrimaryBlueDark,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.92f else 1f, label = "scale")
    val bgColor = if (isPressed) pressedColor else containerColor

    Button(
        onClick = onClick,
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        interactionSource = interactionSource
    ) {
        content()
    }
}

@Composable
private fun ScaleTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFF0F4F8),
    pressedColor: Color = Color(0xFFD0D4D8),
    selectedColor: Color = PrimaryBlue,
    isSelected: Boolean = false,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.85f else 1f, label = "scale")
    val bgColor = when {
        isSelected -> selectedColor
        isPressed -> pressedColor
        else -> containerColor
    }

    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = bgColor),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        interactionSource = interactionSource
    ) {
        content()
    }
}


class ComposeScrollHighlightActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F7FA)) {
                    ScrollHighlightScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScrollHighlightScreen() {
    var selectedIndex by remember { mutableIntStateOf(12) }
    var chartView by remember { mutableStateOf<AAChartView?>(null) }
    var showPicker by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        // 标题
        Text(
            text = "📊 Chart Navigator",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold, color = Color(0xFF1A1A2E)
            )
        )
        Text(
            text = "滚动并高亮指定数据点",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 选择器卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LightBlue)
                        .clickable { showPicker = true }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Point Index", style = MaterialTheme.typography.labelSmall, color = Color(0xFF666666))
                            Text(
                                selectedIndex.toString(),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = PrimaryBlue)
                            )
                        }
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "选择", tint = PrimaryBlue)
                    }
                }
                ScaleButton(
                    onClick = { chartView?.scrollToPoint(selectedIndex) },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("滚动高亮", fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 快捷按钮
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("快捷跳转", style = MaterialTheme.typography.labelMedium, color = Color(0xFF999999))
                    Text("← 左右滑动 →", style = MaterialTheme.typography.labelSmall, color = Color(0xFFBBBBBB))
                }
                Spacer(modifier = Modifier.height(12.dp))
                val lazyRowState = rememberLazyListState()
                val quickIndices = listOf(0, 10, 20, 30, 40, 49)

                // 当选中索引变化时，滚动到对应按钮位置
                LaunchedEffect(selectedIndex) {
                    val index = quickIndices.indexOf(selectedIndex)
                    if (index >= 0) {
                        lazyRowState.animateScrollToItem(index)
                    }
                }

                LazyRow(
                    state = lazyRowState,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(quickIndices.size) { i ->
                        val isSelected = selectedIndex == quickIndices[i]
                        ScaleTonalButton(
                            onClick = {
                                selectedIndex = quickIndices[i]
                                chartView?.scrollToPoint(quickIndices[i])
                            },
                            isSelected = isSelected,
                            selectedColor = Color(0xFFFF0000)
                        ) {
                            Text(
                                quickIndices[i].toString(),
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) Color.White else Color(0xFF333333)
                            )
                        }
                    }
                    item {
                        ScaleTonalButton(
                            onClick = { chartView?.resetZoom() },
                            containerColor = AccentOrange.copy(alpha = 0.15f),
                            pressedColor = AccentOrange.copy(alpha = 0.3f)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "重置", tint = AccentOrange, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("重置", color = AccentOrange, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 图表卡片
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(20.dp),
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
                        chartView = this
                    }
                },
                update = { view ->
                    view.post {
                        view.aa_drawChartWithChartOptions(createChartOptions())
                    }
                }
            )
        }
    }

    if (showPicker) {
        NumberPickerBottomSheet(
            currentValue = selectedIndex,
            range = 0 until POINTS_COUNT,
            onDismiss = { showPicker = false },
            onConfirm = { selectedIndex = it; showPicker = false }
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

    LaunchedEffect(Unit) { listState.scrollToItem(maxOf(0, selectedValue - 2)) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 32.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) { Text("取消", color = Color(0xFF999999)) }
                Text("选择数据点", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
                TextButton(onClick = { onConfirm(selectedValue) }) { Text("确定", color = PrimaryBlue, fontWeight = FontWeight.SemiBold) }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFF8F9FA)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)).background(PrimaryBlue.copy(alpha = 0.1f))
                )
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 86.dp)
                ) {
                    items(range.count()) { index ->
                        val value = range.first + index
                        val isSelected = value == selectedValue
                        Text(
                            text = value.toString(),
                            fontSize = if (isSelected) 22.sp else 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) PrimaryBlue else Color(0xFF999999),
                            modifier = Modifier.fillMaxWidth().clickable {
                                selectedValue = value
                                scope.launch { listState.animateScrollToItem(maxOf(0, value - 2)) }
                            }.padding(vertical = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
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
        .xAxis(AAXAxis().categories(categories).tickLength(0))
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
