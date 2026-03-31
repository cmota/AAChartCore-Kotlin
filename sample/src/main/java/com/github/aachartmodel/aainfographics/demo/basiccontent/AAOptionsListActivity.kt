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

class AAOptionsListActivity : ComponentActivity() {

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
                    AAOptionsListScreen(
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
fun AAOptionsListScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val groupTitleList = listOf(
        "Draw Chart With AAOptions---通过Options绘图"
    )

    val chartTypeNameList = listOf(
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
        )
    )

    val chartTypeList = listOf(
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

            val intent = Intent(context, DrawChartWithAAOptionsActivity::class.java)
            intent.putExtra(MainActivity.kChartTypeKey, chartType)
            context.startActivity(intent)
        },
        modifier = modifier
    )
}
