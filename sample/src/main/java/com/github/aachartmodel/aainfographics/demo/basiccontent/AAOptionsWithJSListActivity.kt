package com.github.aachartmodel.aainfographics.demo.basiccontent

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.github.aachartmodel.aainfographics.demo.additionalcontent.*
import com.github.aachartmodel.aainfographics.demo.compose.ExpandableListCompose

class AAOptionsWithJSListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdgeUI()

        setContent {
            MaterialTheme {
                val isDark = isSystemInDarkTheme()
                val surfaceColor = if (isDark) Color(0xFF121212) else Color.White
                Scaffold(
                    containerColor = surfaceColor
                ) { paddingValues ->
                    AAOptionsWithJSListScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }

    private fun enableEdgeToEdgeUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
            window.isStatusBarContrastEnforced = false
        }
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        val isDarkMode = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        controller.isAppearanceLightStatusBars = !isDarkMode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            controller.isAppearanceLightNavigationBars = !isDarkMode
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
        }
    }
}

@Composable
fun AAOptionsWithJSListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val groupTitleList = listOf(
        "JS Function For AAOptions ---通过带有 JS 函数的 Options 绘图",
        "JS Function For AAAxis Labels | 通过带有 JS 函数的自定义 AAAxis 的文字标签",
        "JS Function For AALegend | 通过带有 JS 函数的自定义 AALegend",
        "JS Function For AAChartEvents---通过 JSFunction 自定义 AAChartEvents 的事件",
        "JS Function For AAOptions---通过 JSFunction 自定义 AAOptions 内容"
    )

    val chartTypeNameList = listOf(
        listOf(
            "简单字符串拼接",
            "自定义不同单位后缀",
            "自定义多彩颜色文字",
            "值为0时,在tooltip中不显示",
            "自定义箱线图的浮动提示框头部内容",
            "自定义Y轴文字",
            "自定义Y轴文字2",
            "自定义分组堆积柱状图tooltip内容",
            "双 X 轴镜像图表",
            "customArearangeChartTooltip---自定义折线范围图的tooltip",
            "调整折线图的 X 轴左边距",
            "通过来自外部的数据源来自定义 tooltip (而非常规的来自图表的 series)"
        ),
        listOf(
            "customYAxisLabels---自定义Y轴文字",
            "customYAxisLabels2---自定义Y轴文字2",
            "customAreaChartXAxisLabelsTextUnitSuffix1---自定义X轴文字单位后缀(通过 formatter 函数)",
            "customAreaChartXAxisLabelsTextUnitSuffix2---自定义X轴文字单位后缀(不通过 formatter 函数)",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart---配置双 Y 轴图表的 Y 轴文字标签的 Formatter 函数 示例 1",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart2---配置双 Y 轴图表的 Y 轴文字标签的 Formatter 函数 示例 2",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart3---配置双 Y 轴图表的 Y 轴文字标签的 Formatter 函数 示例 3",
            "customColumnChartXAxisLabelsTextByInterceptTheFirstFourCharacters---通过截取前四个字符来自定义 X 轴 labels",
            "customSpiderChartStyle---自定义蜘蛛🕷🕸图样式",
            "customizeEveryDataLabelSinglelyByDataLabelsFormatter---通过 DataLabels 的 formatter 函数来实现单个数据标签🏷自定义",
            "customXAxisLabelsBeImages---自定义 X轴 labels 为一组图片"
        ),
        listOf(
            "disableLegendClickEventForNormalChart---禁用常规图表 legend 点击事件",
            "disableLegendClickEventForPieChart---禁用饼图 legend 点击事件",
            "customLegendItemClickEvent---自定义图例 legend 的点击事件",
            "plotLinesWithVirtualSeriesLegendProxy---plotLines + 虚拟 series 图例联动"
        ),
        listOf(
            "setCrosshairAndTooltipToTheDefaultPositionAfterLoadingChart---图表加载完成后设置 crosshair 和 tooltip 到默认位置",
            "generalDrawingChart---普通绘图",
            "advancedTimeLineChart---高级时间轴绘图",
            "configureBlinkMarkerChart---配置闪烁特效的 marker 图表",
            "configureSpecialStyleMarkerOfSingleDataElementChartWithBlinkEffect---配置闪烁特效的 marker 图表2",
            "configureScatterChartWithBlinkEffect---配置闪烁特效的散点图",
            "automaticallyHideTooltipAfterItIsShown---在浮动提示框显示后自动隐藏",
            "dynamicHeightGridLineAreaChart---动态高度的网格线区域填充图",
            "customizeYAxisPlotLinesLabelBeSpecialStyle---自定义 Y 轴轴线上面的标签文字特殊样式",
            "configureECGStyleChart---配置心电图样式图表"
        ),
        listOf(
            "customDoubleXAxesChart---自定义双 X 轴图表",
            "disableColumnChartUnselectEventEffectBySeriesPointEventClickFunction---通过 Series 的 Point 的选中事件函数来禁用条形图反选效果",
            "customizeEveryDataLabelSinglelyByDataLabelsFormatter---通过 formatter 来自定义单个 dataLabels 元素",
            "configureColorfulDataLabelsForPieChart---为饼图配置多彩 dataLabels"
        )
    )

    val chartTypeList = listOf(
        listOf(
            "customAreaChartTooltipStyleWithSimpleFormatString",
            "customAreaChartTooltipStyleWithDifferentUnitSuffix",
            "customAreaChartTooltipStyleWithColorfulHtmlLabels",
            "customLineChartTooltipStyleWhenValueBeZeroDoNotShow",
            "customBoxplotTooltipContent",
            "customYAxisLabels",
            "customYAxisLabels2",
            "customStackedAndGroupedColumnChartTooltip",
            "customDoubleXAxesChart",
            "customArearangeChartTooltip",
            "customLineChartOriginalPointPositionByConfiguringXAxisFormatterAndTooltipFormatter",
            "customTooltipWhichDataSourceComeFromOutSideRatherThanSeries"
        ),
        listOf(
            "customYAxisLabels",
            "customYAxisLabels2",
            "customAreaChartXAxisLabelsTextUnitSuffix1",
            "customAreaChartXAxisLabelsTextUnitSuffix2",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart2",
            "configureTheAxesLabelsFormattersOfDoubleYAxesChart3",
            "customColumnChartXAxisLabelsTextByInterceptTheFirstFourCharacters",
            "customSpiderChartStyle",
            "customizeEveryDataLabelSinglelyByDataLabelsFormatter",
            "customXAxisLabelsBeImages"
        ),
        listOf(
            "disableLegendClickEventForNormalChart",
            "disableLegendClickEventForPieChart",
            "customLegendItemClickEvent",
            "plotLinesWithVirtualSeriesLegendProxy"
        ),
        listOf(
            "setCrosshairAndTooltipToTheDefaultPositionAfterLoadingChart",
            "generalDrawingChart",
            "advancedTimeLineChart",
            "configureBlinkMarkerChart",
            "configureSpecialStyleMarkerOfSingleDataElementChartWithBlinkEffect",
            "configureScatterChartWithBlinkEffect",
            "automaticallyHideTooltipAfterItIsShown",
            "dynamicHeightGridLineAreaChart",
            "customizeYAxisPlotLinesLabelBeSpecialStyle",
            "configureECGStyleChart"
        ),
        listOf(
            "customDoubleXAxesChart",
            "disableColumnChartUnselectEventEffectBySeriesPointEventClickFunction",
            "customizeEveryDataLabelSinglelyByDataLabelsFormatter",
            "configureColorfulDataLabelsForPieChart"
        )
    )

    ExpandableListCompose(
        groupData = groupTitleList,
        childData = chartTypeNameList,
        onChildClick = { groupPosition, childPosition ->
            val chartType = chartTypeList.getOrNull(groupPosition)?.getOrNull(childPosition) ?: ""

            Toast.makeText(
                context,
                "你点击了：${chartTypeNameList.getOrNull(groupPosition)?.getOrNull(childPosition) ?: ""}",
                Toast.LENGTH_SHORT
            ).show()

            when (groupPosition) {
                0 -> {
                    val intent = Intent(context, JSFormatterFunctionActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, JSFunctionForAAAxisActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, JSFunctionForAALegendActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context, JSFunctionForAAChartEventsActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                4 -> {
                    val intent = Intent(context, JSFunctionForAAOptionsActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
            }
        },
        modifier = modifier
    )
}
