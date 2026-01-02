package com.github.aachartmodel.aainfographics.demo.basiccontent

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.github.aachartmodel.aainfographics.demo.additionalcontent.*
import com.github.aachartmodel.aainfographics.demo.compose.ExpandableListCompose

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdgeUI()

        setContent {
            MaterialTheme {
                Scaffold(
                    containerColor = Color.White
                ) { paddingValues ->
                    MainExpandableListScreen(
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
        controller.isAppearanceLightStatusBars = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            controller.isAppearanceLightNavigationBars = true
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

    companion object {
        const val kChartTypeKey = "chartType"
    }
}

@Composable
fun MainExpandableListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Group titles
    val groupTitleList = listOf(
        "Basic Type Chart ---基础类型图表",
        "Special Type Chart ---特殊类型图表",
        "Mixed Chart ---混合图形",
        "Custom Style Chart---一些自定义风格样式图表",
        "Draw Chart With AAOptions---通过Options绘图",
        "Only Refresh data ---即时刷新图表数据",
        "JS Function For AAOptions ---通过带有 JS 函数的 Options 绘图",
        "Evaluate JS String Function---执行js函数",
        "Double Charts Linked Work---双表联动",
        "Scrollable chart ---可滚动の图表",
        "Chart Options Advanced Updating---图表高级更新",
        "JS Function For AAAxis Labels | 通过带有 JS 函数的自定义 AAAxis 的文字标签",
        "JS Function For AALegend | 通过带有 JS 函数的自定义 AALegend",
        "JS Function For AAChartEvents---通过 JSFunction 自定义 AAChartEvents 的事件",
        "JS Function For AAOptions---通过 JSFunction 自定义 AAOptions 内容",
        "Compose 示例"
    )


    // Child data - chart type names
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
        ),
        listOf(
            "customLegendStyle",
            "drawChartWithOptionsOne",
            "AAPlotLinesForChart",
            "customAATooltipWithJSFunction",
            "customXAxisCrosshairStyle",
            "XAxisLabelsFontColorWithHTMLString",
            "XAxisLabelsFontColorAndFontSizeWithHTMLString",
            "_DataLabels_XAXis_YAxis_Legend_Style",
            "XAxisPlotBand",
            "configureTheMirrorColumnChart",
            "configureDoubleYAxisChartOptions",
            "configureTripleYAxesMixedChart",
            "customLineChartDataLabelsFormat",
            "configureDoubleYAxesAndColumnLineMixedChart",
            "configureDoubleYAxesMarketDepthChart",
            "customAreaChartTooltipStyleLikeHTMLTable",
            "simpleGaugeChart",
            "gaugeChartWithPlotBand",
            "doubleLayerHalfPieChart"
        ),
        listOf(
            "Column Chart---柱形图",
            "Bar Chart---条形图",
            "Area Chart---折线填充图",
            "Areaspline Chart---曲线填充图",
            "Step Area Chart---直方折线填充图",
            "Step Line Chart---直方折线图",
            "Line Chart---折线图",
            "Spline Chart---曲线图",
            "Scatter Chart---散点图"
        ),
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
            "eval JS function 1",
            "eval JS function 2",
            "eval JS function 3"
        ),
        listOf(
            "doubleChartsLinkedWork"
        ),
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
            "customLegendItemClickEvent---自定义图例 legend 的点击事件"
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
        ),
        listOf(
            "Compose AAChartView (AndroidView)"
        )
    )


    // Chart type data for navigation
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
        ),
        listOf(
            "customLegendStyle",
            "AAPlotBandsForChart",
            "AAPlotLinesForChart",
            "customAATooltipWithJSFunction",
            "customXAxisCrosshairStyle",
            "XAxisLabelsFontColorWithHTMLString",
            "XAxisLabelsFontColorAndFontSizeWithHTMLString",
            "_DataLabels_XAXis_YAxis_Legend_Style",
            "XAxisPlotBand",
            "configureTheMirrorColumnChart",
            "configureDoubleYAxisChartOptions",
            "configureTripleYAxesMixedChart",
            "customLineChartDataLabelsFormat",
            "configureDoubleYAxesAndColumnLineMixedChart",
            "configureDoubleYAxesMarketDepthChart",
            "customAreaChartTooltipStyleLikeHTMLTable",
            "simpleGaugeChart",
            "gaugeChartWithPlotBand",
            "doubleLayerHalfPieChart"
        ),
        listOf(
            AAChartType.Column.value,
            AAChartType.Bar.value,
            AAChartType.Area.value,
            AAChartType.Areaspline.value,
            "stepArea",
            "stepLine",
            AAChartType.Line.value,
            AAChartType.Spline.value,
            AAChartType.Scatter.value
        ),
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
            "evalJSFunction1",
            "evalJSFunction2",
            "evalJSFunction3"
        ),
        listOf(
            "doubleChartsLinkedWork"
        ),
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
            AAChartType.Area.value,
            AAChartType.Areaspline.value,
            AAChartType.Area.value,
            AAChartType.Line.value,
            AAChartType.Line.value,
            AAChartType.Spline.value
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
            "customLegendItemClickEvent"
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
        ),
        listOf(
            AAChartType.Spline.value
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
                4 -> {
                    val intent = Intent(context, DrawChartWithAAOptionsActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                5 -> {
                    val intent = Intent(context, OnlyRefreshChartDataActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                6 -> {
                    val intent = Intent(context, JSFormatterFunctionActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                7 -> {
                    val intent = Intent(context, EvaluateJSStringFunctionActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                8 -> {
                    val intent = Intent(context, DoubleChartsLinkedWorkActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                9 -> {
                    val intent = Intent(context, ScrollableChartActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                10 -> {
                    val intent = Intent(context, AdvancedUpdatingFeatureActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                11 -> {
                    val intent = Intent(context, JSFunctionForAAAxisActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                12 -> {
                    val intent = Intent(context, JSFunctionForAALegendActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                13 -> {
                    val intent = Intent(context, JSFunctionForAAChartEventsActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                14 -> {
                    val intent = Intent(context, JSFunctionForAAOptionsActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                15 -> {
                    val intent = Intent(context, com.github.aachartmodel.aainfographics.demo.compose.ComposeScrollHighlightActivity::class.java)
                    context.startActivity(intent)
                }
            }
        },
        modifier = modifier
    )
}
