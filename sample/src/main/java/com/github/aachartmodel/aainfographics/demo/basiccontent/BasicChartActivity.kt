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
package com.github.aachartmodel.aainfographics.demo.basiccontent

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.demo.R
import com.github.aachartmodel.aainfographics.demo.chartcomposer.BasicChartComposer
import com.google.gson.Gson

open class BasicChartActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener,
    CompoundButton.OnCheckedChangeListener, AAChartView.AAChartViewCallBack {

    var aaChartModel = AAChartModel()
    var aaChartView: AAChartView? = null
    var chartType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_chart)

        setUpAAChartView()
        setUpRadioButtonsAndSwitches()
    }

    private fun setUpAAChartView() {
        aaChartView = findViewById(R.id.AAChartView)
        aaChartView?.setBackgroundColor(0)
        aaChartView?.callBack = this
        aaChartModel = configureAAChartModel()
        aaChartView?.aa_drawChartWithChartModel(aaChartModel)

    }


    private fun configureAAChartModel(): AAChartModel {
        val intent = intent
        chartType = intent.getStringExtra("chartType").toString()
        val position = intent.getIntExtra("position", 0)
        aaChartModel = BasicChartComposer.configureAreaChart()
        val chartTypeEnum = convertStringToEnum(chartType)
        aaChartModel.chartType(chartTypeEnum)

        configureTheStyleForDifferentTypeChart(chartType,position)
        configureViewsVisibility(chartType)

        return aaChartModel
    }

    private fun configureTheStyleForDifferentTypeChart(chartType: String, position: Int) {
        if ((chartType == AAChartType.Area.value || chartType == AAChartType.Line.value)
            && (position == 4 || position == 5)) {
            aaChartModel = BasicChartComposer.configureStepAreaChartAndStepLineChart()
        } else if (chartType == AAChartType.Column.value || chartType == AAChartType.Bar.value) {
            aaChartModel = BasicChartComposer.configureColumnChartAndBarChart()
        } else if (chartType == AAChartType.Area.value || chartType == AAChartType.Areaspline.value) {
            aaChartModel = BasicChartComposer.configureAreaChartAndAreasplineChartStyle(chartType)
        } else if (chartType == AAChartType.Line.value || chartType == AAChartType.Spline.value) {
            aaChartModel = BasicChartComposer.configureLineChartAndSplineChartStyle(chartType)
        }
    }

    private fun configureViewsVisibility(chartType: String) {
        val squareCornersRadio: RadioGroup = findViewById(R.id.cornerStyleTypeRadioGroup)
        val markerSymbolTypeRadioGroup: RadioGroup = findViewById(R.id.markerSymbolTypeRadioGroup)
        if (chartType == AAChartType.Column.value || chartType == AAChartType.Bar.value) {
            squareCornersRadio.visibility = View.VISIBLE
            markerSymbolTypeRadioGroup.visibility = View.GONE

            val markerHideSwitch: Switch = findViewById(R.id.markerHideSwitch)
            markerHideSwitch.visibility = View.GONE
            val markerHideTextView: TextView = findViewById(R.id.markerHideTextView)
            markerHideTextView.visibility = View.GONE
        } else {
            squareCornersRadio.visibility = View.GONE
            markerSymbolTypeRadioGroup.visibility = View.VISIBLE
        }
    }




    private fun convertStringToEnum(chartTypeStr: String): AAChartType {
        var chartTypeEnum = AAChartType.Column
        when (chartTypeStr) {
            AAChartType.Column.value -> chartTypeEnum = AAChartType.Column
            AAChartType.Bar.value -> chartTypeEnum = AAChartType.Bar
            AAChartType.Area.value -> chartTypeEnum = AAChartType.Area
            AAChartType.Areaspline.value -> chartTypeEnum = AAChartType.Areaspline
            AAChartType.Line.value -> chartTypeEnum = AAChartType.Line
            AAChartType.Spline.value -> chartTypeEnum = AAChartType.Spline
        }
        return chartTypeEnum
    }


    private fun setUpRadioButtonsAndSwitches() {
        val stackingTypeRadioGroup = findViewById<RadioGroup>(R.id.stackingTypeRadioGroup)
        stackingTypeRadioGroup.setOnCheckedChangeListener(this)

        val cornerStyleTypeRadioGroup = findViewById<RadioGroup>(R.id.cornerStyleTypeRadioGroup)
        cornerStyleTypeRadioGroup.setOnCheckedChangeListener(this)

        val markerSymbolTypeRadioGroup: RadioGroup = findViewById(R.id.markerSymbolTypeRadioGroup)
        markerSymbolTypeRadioGroup.setOnCheckedChangeListener(this)

        val boolSwitch1: Switch = findViewById(R.id.xReversedSwitch)
        boolSwitch1.setOnCheckedChangeListener(this)

        val boolSwitch2: Switch = findViewById(R.id.yReversedSwitch)
        boolSwitch2.setOnCheckedChangeListener(this)

        val boolSwitch3: Switch = findViewById(R.id.polarSwitch)
        boolSwitch3.setOnCheckedChangeListener(this)

        val boolSwitch4: Switch = findViewById(R.id.xInvertedSwitch)
        boolSwitch4.setOnCheckedChangeListener(this)

        val boolSwitch5: Switch = findViewById(R.id.dataShowSwitch)
        boolSwitch5.setOnCheckedChangeListener(this)

        val boolSwitch6: Switch = findViewById(R.id.markerHideSwitch)
        boolSwitch6.setOnCheckedChangeListener(this)
    }

    /**
     * 重写的状态改变的事件的方法
     * @param group 单选组合框
     * @param checkedId 其中的每个RadioButton的Id
     */
    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        if (group.id == R.id.stackingTypeRadioGroup) {
            when (group.checkedRadioButtonId) {
                R.id.noStackingRadio -> aaChartModel.stacking(AAChartStackingType.False)
                R.id.normalStackingRadio -> aaChartModel.stacking(AAChartStackingType.Normal)
                R.id.percentStackingRadio -> aaChartModel.stacking(AAChartStackingType.Percent)
            }
        } else {
            if (aaChartModel.chartType == AAChartType.Bar
                || aaChartModel.chartType == AAChartType.Column
            ) {
                when (group.checkedRadioButtonId) {
                    R.id.squareCornersRadio -> aaChartModel.borderRadius(1f)
                    R.id.roundedCornersRadio -> aaChartModel.borderRadius(10f)
                    R.id.wedgeCornersRadio -> aaChartModel.borderRadius(1000f)
                }
            } else {
                when (group.checkedRadioButtonId) {
                    R.id.circleSymbolRadio -> aaChartModel.markerSymbol(AAChartSymbolType.Circle)
                    R.id.squareSymbolRadio -> aaChartModel.markerSymbol(AAChartSymbolType.Square)
                    R.id.diamondSymbolRadio -> aaChartModel.markerSymbol(AAChartSymbolType.Diamond)
                    R.id.triangleSymbolRadio -> aaChartModel.markerSymbol(AAChartSymbolType.Triangle)
                    R.id.triangleDownSymbolRadio -> aaChartModel.markerSymbol(AAChartSymbolType.TriangleDown)
                }
            }
        }

        aaChartView?.aa_refreshChartWithChartModel(aaChartModel)
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.xReversedSwitch -> aaChartModel.xAxisReversed(isChecked)
            R.id.yReversedSwitch -> aaChartModel.yAxisReversed(isChecked)
            R.id.xInvertedSwitch -> aaChartModel.inverted(isChecked)
            R.id.polarSwitch -> aaChartModel.polar(isChecked)
            R.id.dataShowSwitch -> aaChartModel.dataLabelsEnabled(isChecked)
            R.id.markerHideSwitch -> aaChartModel.markerRadius(if (isChecked) 0f else 6f)
        }

        aaChartView?.aa_refreshChartWithChartModel(aaChartModel)
    }

    override fun chartViewDidFinishLoad(aaChartView: AAChartView) {
        println("🔥图表加载完成回调方法 ")
    }

    override fun chartViewMoveOverEventMessage(
        aaChartView: AAChartView,
        messageModel: AAMoveOverEventMessageModel
    ) {
        val gson = Gson()
        println("🚀move over event message " + gson.toJson(messageModel))
    }
}

