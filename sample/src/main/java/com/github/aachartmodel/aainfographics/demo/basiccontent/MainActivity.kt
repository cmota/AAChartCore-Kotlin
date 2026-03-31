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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                MainTabScreen()
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

    companion object {
        const val kChartTypeKey = "chartType"
    }
}

@Composable
fun MainTabScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabItems = listOf("AAChartModel", "AAOptions", "JSFunction", "更多")
    val isDark = isSystemInDarkTheme()
    val surfaceColor = if (isDark) Color(0xFF121212) else Color.White
    val navBarColor = if (isDark) Color(0xFF1C1C1E) else Color.White

    Scaffold(
        containerColor = surfaceColor,
        bottomBar = {
            NavigationBar(
                containerColor = navBarColor
            ) {
                tabItems.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (index) {
                                    0 -> Icons.Default.List
                                    1 -> Icons.Default.DateRange
                                    2 -> Icons.Default.PlayArrow
                                    else -> Icons.Default.Settings
                                },
                                contentDescription = title
                            )
                        },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when (selectedTab) {
            0 -> AAChartModelListScreen(modifier = modifier)
            1 -> AAOptionsListScreen(modifier = modifier)
            2 -> AAOptionsWithJSListScreen(modifier = modifier)
            3 -> AdditionalChartListScreen(modifier = modifier)
        }
    }
}

@Composable
fun AdditionalChartListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val groupTitleList = listOf(
        "Only Refresh data ---即时刷新图表数据",
        "Evaluate JS String Function---执行js函数",
        "Double Charts Linked Work---双表联动",
        "Scrollable chart ---可滚动の图表",
        "Chart Options Advanced Updating---图表高级更新",
        "Compose 示例"
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
            "Spline Chart---曲线图",
            "Scatter Chart---散点图"
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
            "Compose AAChartView (AndroidView)"
        )
    )

    val chartTypeList = listOf(
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
                    val intent = Intent(context, OnlyRefreshChartDataActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, EvaluateJSStringFunctionActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, DoubleChartsLinkedWorkActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    context.startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context, ScrollableChartActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                4 -> {
                    val intent = Intent(context, AdvancedUpdatingFeatureActivity::class.java)
                    intent.putExtra(MainActivity.kChartTypeKey, chartType)
                    intent.putExtra("position", childPosition)
                    context.startActivity(intent)
                }
                5 -> {
                    val intent = Intent(context, com.github.aachartmodel.aainfographics.demo.compose.ComposeScrollHighlightActivity::class.java)
                    context.startActivity(intent)
                }
            }
        },
        modifier = modifier
    )
}
