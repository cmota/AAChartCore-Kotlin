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
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.demo.compose.ExpandableListCompose

class AAChartModelListActivity : ComponentActivity() {

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
                    AAChartModelListScreen(
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
fun AAChartModelListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val groupTitleList = listOf(
        "Basic Type Chart ---基础类型图表",
        "Special Type Chart ---特殊类型图表",
        "Mixed Chart ---混合图形",
        "Custom Style Chart---一些自定义风格样式图表"
    )

    val chartTypeNameList = listOf(
        listOf(
            "Column Chart---柱形图",
            "Bar Chart---条形图",
            "Area Chart---折线填充图",
            "Areaspline Chart---曲线填充图",
            "Step Area Chart---直方折线填充图",
            "Step Line Chart---直方折线图",
            "Line Chart---折线图",
            "Spline Chart---曲线图"
        ),
        listOf(
            "Polar Column Chart---玫瑰图",
            "Polar Bar Chart---径向条形图",
            "Polar Line Chart---蜘蛛图",
            "Polar Area Chart---雷达图",
            "Step Line Chart---直方折线图",
            "Step Area Chart---直方折线填充图",
            "Pie Chart---扇形图",
            "Bubble Chart---气泡图",
            "Scatter Chart---散点图",
            "Arearange Chart---区域范围图",
            "Columnrange Chart---柱形范围图",
            "Boxplot Chart---箱线图",
            "Waterfall Chart---瀑布图",
            "Pyramid Chart---金字塔图",
            "Funnel Chart---漏斗图",
            "Errorbar Chart---误差图",
            "Gauge Chart---仪表图",
            "Polygon Chart---多边形图"
        ),
        listOf(
            "arearangeMixedLine",
            "columnrangeMixedLine",
            "stackingColumnMixedLine",
            "dashStyleTypeMixed",
            "negativeColorMixed",
            "scatterMixedLine",
            "negativeColorMixedBubble",
            "polygonMixedScatter",
            "polarChartMixed",
            "configurePieMixedLineMixedColumnChart",
            "configureNegativeColorMixedAreasplineChart"
        ),
        listOf(
            "colorfulChart",
            "gradientColorfulChart",
            "discontinuousDataChart",
            "colorfulColumnChart",
            "nightingaleRoseChart",
            "chartWithShadowStyle",
            "colorfulGradientAreaChart",
            "colorfulGradientSplineChart",
            "gradientColorAreasplineChart",
            "SpecialStyleMarkerOfSingleDataElementChart",
            "SpecialStyleColumnOfSingleDataElementChart",
            "AreaChartThreshold",
            "customScatterChartMarkerSymbolContent",
            "customLineChartMarkerSymbolContent",
            "TriangleRadarChart",
            "QuadrangleRadarChart",
            "PentagonRadarChart",
            "HexagonRadarChart",
            "adjustYAxisMaxAndMinValues---调整 X 轴和 Y 轴最大值",
            "custom Special Style DataLabel Of Single Data Element Chart---指定单个数据元素的 DataLabel 为特殊样式",
            "custom Bar Chart Hover Color and Select Color---自定义条形图手指滑动颜色和单个长条被选中颜色",
            "custom Line Chart Chart Hover And Select Halo Style---自定义直线图手指略过和选中的 Halo 样式",
            "custom Spline Chart Marker States Hover Style---自定义曲线图手指略过时的 Marker 样式",
            "splineChartHoverLineWithNoChangeAndCustomMarkerStatesHoverStyle---曲线图手指掠过时的 Hover 线不变形,并且自定义 Marker 样式",
            "customNormalStackingChartDataLabelsContentAndStyle---自定义堆积柱状图 DataLabels 的内容及样式",
            "upsideDownPyramidChart---倒立的金字塔图",
            "doubleLayerPieChart---双层嵌套扇形图",
            "disableSomeOfLinesMouseTrackingEffect---针对部分数据列关闭鼠标或手指跟踪行为",
            "configureColorfulShadowChart---彩色阴影效果的曲线图",
            "configureColorfulDataLabelsStepLineChart---彩色 DataLabels 的直方折线图",
            "configureColorfulGradientColorAndColorfulDataLabelsStepAreaChart---彩色渐变效果且彩色 DataLabels 的直方折线填充图",
            "disableSplineChartMarkerHoverEffect---禁用曲线图的手指滑动 marker 点的光圈变化放大的效果",
            "configureMaxAndMinDataLabelsForChart---为图表最大值最小值添加 DataLabels 标记",
            "customVerticalXAxisCategoriesLabelsByHTMLBreakLineTag---通过 HTML 的换行标签来实现图表的 X 轴的 分类文字标签的换行效果",
            "noMoreGroupingAndOverlapEachOtherColumnChart---不分组的相互重叠柱状图📊",
            "noMoreGroupingAndNestedColumnChart---不分组的嵌套柱状图📊",
            "topRoundedCornersStackingColumnChart---顶部为圆角的堆积柱状图📊",
            "freeStyleRoundedCornersStackingColumnChart---各个圆角自由独立设置的堆积柱状图📊",
            "customColumnChartBorderStyleAndStatesHoverColor---自定义柱状图 border 样式及手指掠过图表 series 元素时的柱形颜色",
            "customLineChartWithColorfulMarkersAndLines---彩色连接点和连接线的折线图📈",
            "customLineChartWithColorfulMarkersAndLines2---彩色连接点和连接线的多组折线的折线图📈",
            "drawLineChartWithPointsCoordinates---通过点坐标来绘制折线图",
            "configureSpecialStyleColumnForNegativeDataMixedPositiveData---为正负数混合的柱形图自定义特殊样式效果",
            "configureMultiLevelStopsArrGradientColorAreasplineMixedLineChart---多层次半透明渐变效果的曲线填充图混合折线图📈",
            "connectNullsForSingleAASeriesElement---为单个 AASeriesElement 单独设置是否断点重连",
            "lineChartsWithLargeDifferencesInTheNumberOfDataInDifferentSeriesElement---不同数据列数据量差异较大的折线图",
            "customAreasplineChartWithColorfulGradientColorZones---彩色渐变填充区域曲线图",
            "colorfulMarkerWithZonesChart---高饱和度波浪图 — 实心与空心 Marker 对比"
        )
    )

    val chartTypeList = listOf(
        listOf(
            AAChartType.Column.value,
            AAChartType.Bar.value,
            AAChartType.Area.value,
            AAChartType.Areaspline.value,
            AAChartType.Area.value,
            AAChartType.Line.value,
            AAChartType.Line.value,
            AAChartType.Spline.value
        ),
        listOf(
            AAChartType.Column.value,
            AAChartType.Bar.value,
            AAChartType.Line.value,
            AAChartType.Area.value,
            AAChartType.Spline.value,
            AAChartType.Areaspline.value,
            AAChartType.Pie.value,
            AAChartType.Bubble.value,
            AAChartType.Scatter.value,
            AAChartType.Arearange.value,
            AAChartType.Columnrange.value,
            AAChartType.Boxplot.value,
            AAChartType.Waterfall.value,
            AAChartType.Pyramid.value,
            AAChartType.Funnel.value,
            AAChartType.Errorbar.value,
            AAChartType.Gauge.value,
            AAChartType.Polygon.value
        ),
        listOf(
            "arearangeMixedLine",
            "columnrangeMixedLine",
            "stackingColumnMixedLine",
            "dashStyleTypeMixed",
            "negativeColorMixed",
            "scatterMixedLine",
            "negativeColorMixedBubble",
            "polygonMixedScatter",
            "polarChartMixed",
            "configurePieMixedLineMixedColumnChart",
            "configureNegativeColorMixedAreasplineChart"
        ),
        listOf(
            "colorfulChart",
            "gradientColorfulChart",
            "discontinuousDataChart",
            "colorfulColumnChart",
            "nightingaleRoseChart",
            "chartWithShadowStyle",
            "colorfulGradientAreaChart",
            "colorfulGradientSplineChart",
            "gradientColorAreasplineChart",
            "SpecialStyleMarkerOfSingleDataElementChart",
            "SpecialStyleColumnOfSingleDataElementChart",
            "AreaChartThreshold",
            "customScatterChartMarkerSymbolContent",
            "customLineChartMarkerSymbolContent",
            "TriangleRadarChart",
            "QuadrangleRadarChart",
            "PentagonRadarChart",
            "HexagonRadarChart",
            "adjustYAxisMaxAndMinValues",
            "customSpecialStyleDataLabelOfSingleDataElementChart",
            "customBarChartHoverColorAndSelectColor",
            "customChartHoverAndSelectHaloStyle",
            "customSplineChartMarkerStatesHoverStyle",
            "splineChartHoverLineWithNoChangeAndCustomMarkerStatesHoverStyle",
            "customNormalStackingChartDataLabelsContentAndStyle",
            "upsideDownPyramidChart",
            "doubleLayerPieChart",
            "disableSomeOfLinesMouseTrackingEffect",
            "configureColorfulShadowSplineChart",
            "configureColorfulDataLabelsStepLineChart",
            "configureColorfulGradientColorAndColorfulDataLabelsStepAreaChart",
            "disableSplineChartMarkerHoverEffect",
            "configureMaxAndMinDataLabelsForChart",
            "customVerticalXAxisCategoriesLabelsByHTMLBreakLineTag",
            "noMoreGroupingAndOverlapEachOtherColumnChart",
            "noMoreGroupingAndNestedColumnChart",
            "topRoundedCornersStackingColumnChart",
            "freeStyleRoundedCornersStackingColumnChart",
            "customColumnChartBorderStyleAndStatesHoverColor",
            "customLineChartWithColorfulMarkersAndLines",
            "customLineChartWithColorfulMarkersAndLines2",
            "drawLineChartWithPointsCoordinates",
            "configureSpecialStyleColumnForNegativeDataMixedPositiveData",
            "configureMultiLevelStopsArrGradientColorAreasplineMixedLineChart",
            "connectNullsForSingleAASeriesElement",
            "lineChartsWithLargeDifferencesInTheNumberOfDataInDifferentSeriesElement",
            "customAreasplineChartWithColorfulGradientColorZones",
            "colorfulMarkerWithZonesChart"
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
                    val intent = Intent(context, BasicChartActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, SpecialChartListActivity::class.java)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, MixedChartListActivity::class.java)
                    context.startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context, CustomStyleChartActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
            }
        },
        modifier = modifier
    )
}
