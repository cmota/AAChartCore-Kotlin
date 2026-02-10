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
package com.github.aachartmodel.aainfographics.aachartcreator

import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAMarker
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAZonesElement


/**
 * Created by anan on 2018/4/16.
 */


open class AASeriesElement {

    var type: String? = null
    var allowPointSelect: Boolean? = null
    var name: String? = null
    var data: Array<Any>? = null
    var lineWidth //折线图、曲线图、直方折线图、折线填充图、曲线填充图、直方折线填充图的线条宽度
            : Number? = null
    var borderColor //The border color, It is only valid for column, bar, pie, columnrange, pyramid and funnel chart types
            : String? = null
    var borderWidth //The border width, It is only valid for column, bar, pie, columnrange, pyramid and funnel chart types
            : Number? = null
    var borderRadius //The corner radius of the border surrounding each column or bar.
            : Number? = null
    var borderRadiusTopLeft: Any? = null
    var borderRadiusTopRight: Any? = null
    var borderRadiusBottomLeft: Any? = null
    var borderRadiusBottomRight: Any? = null
    var color: Any? = null
    var fillColor: Any? = null
    var fillOpacity //折线填充图、曲线填充图、直方折线填充图等填充图类型的填充颜色透明度
            : Float? = null
    var threshold //The threshold, also called zero level or base level. For line type series this is only used in conjunction with negativeColor. default：0.
            : Number? = null
    var negativeColor // The color for the parts of the graph or points that are below the threshold
            : String? = null
    var negativeFillColor: Any? = null
    var size: Any? = null
    var innerSize: Any? = null
    var dashStyle: String? = null
    var yAxis: Int? = null
    var dataLabels: AADataLabels? = null
    var marker: AAMarker? = null
    var step: Any? = null
    var states: Any? = null
    var colorByPoint: Boolean? = null
    var zIndex: Int? = null
    var zones: Array<AAZonesElement>? = null
    var zoneAxis: String? = null
    var shadow: AAShadow? = null
    var stack: String? = null
    var tooltip: AATooltip? = null
    var showInLegend: Boolean? = null
    var enableMouseTracking: Boolean? = null
    var reversed: Boolean? = null
    var id: String? = null
    var connectNulls: Boolean? = null


    fun type(prop: AAChartType?): AASeriesElement {
        type = prop?.value
        return this
    }

    fun allowPointSelect(prop: Boolean?): AASeriesElement {
        allowPointSelect = prop
        return this
    }

    fun name(prop: String?): AASeriesElement {
        name = prop
        return this
    }

    fun data(prop: Array<Any>?): AASeriesElement {
        data = prop
        return this
    }

    fun lineWidth(prop: Number?): AASeriesElement {
        lineWidth = prop
        return this
    }

    fun borderColor(prop: String?): AASeriesElement {
        borderColor = prop
        return this
    }

    fun borderWidth(prop: Number?): AASeriesElement {
        borderWidth = prop
        return this
    }

    fun borderRadius(prop: Number?): AASeriesElement {
        borderRadius = prop
        return this
    }

    fun borderRadiusTopLeft(prop: Any?): AASeriesElement {
        borderRadiusTopLeft = prop
        return this
    }

    fun borderRadiusTopRight(prop: Any?): AASeriesElement {
        borderRadiusTopRight = prop
        return this
    }

    fun borderRadiusBottomLeft(prop: Any?): AASeriesElement {
        borderRadiusBottomLeft = prop
        return this
    }

    fun borderRadiusBottomRight(prop: Any?): AASeriesElement {
        borderRadiusBottomRight = prop
        return this
    }

    fun color(prop: Any?): AASeriesElement {
        color = prop
        return this
    }

    fun fillColor(prop: Any?): AASeriesElement {
        fillColor = prop
        return this
    }

    fun fillOpacity(prop: Float?): AASeriesElement {
        fillOpacity = prop
        return this
    }

    fun threshold(prop: Number?): AASeriesElement {
        threshold = prop
        return this
    }

    fun negativeColor(prop: String?): AASeriesElement {
        negativeColor = prop
        return this
    }

    fun negativeFillColor(prop: Any?): AASeriesElement {
        negativeFillColor = prop
        return this
    }

    fun size(prop: Any?): AASeriesElement {
        size = prop
        return this
    }

    fun innerSize(prop: Any?): AASeriesElement {
        innerSize = prop
        return this
    }

    fun dashStyle(prop: AAChartLineDashStyleType?): AASeriesElement {
        dashStyle = prop?.value
        return this
    }

    fun yAxis(prop: Int?): AASeriesElement {
        yAxis = prop
        return this
    }

    fun dataLabels(prop: AADataLabels?): AASeriesElement {
        dataLabels = prop
        return this
    }

    fun marker(prop: AAMarker?): AASeriesElement {
        marker = prop
        return this
    }

    fun step(prop: Any?): AASeriesElement {
        step = prop
        return this
    }

    fun states(prop: Any?): AASeriesElement {
        states = prop
        return this
    }

    fun colorByPoint(prop: Boolean?): AASeriesElement {
        colorByPoint = prop
        return this
    }

    fun zIndex(prop: Int?): AASeriesElement {
        zIndex = prop
        return this
    }

    fun zones(prop: Array<AAZonesElement>): AASeriesElement {
        zones = prop
        return this
    }

    fun zoneAxis(prop: String?): AASeriesElement {
        zoneAxis = prop
        return this
    }

    fun shadow(prop: AAShadow?): AASeriesElement {
        shadow = prop
        return this
    }

    fun stack(prop: String?): AASeriesElement {
        stack = prop
        return this
    }

    fun tooltip(prop: AATooltip?): AASeriesElement {
        tooltip = prop
        return this
    }

    fun showInLegend(prop: Boolean?): AASeriesElement {
        showInLegend = prop
        return this
    }

    fun enableMouseTracking(prop: Boolean?): AASeriesElement {
        enableMouseTracking = prop
        return this
    }

    fun reversed(prop: Boolean?): AASeriesElement {
        reversed = prop
        return this
    }

    fun id(prop: String?): AASeriesElement {
        id = prop
        return this
    }

    fun connectNulls(prop: Boolean?): AASeriesElement {
        connectNulls = prop
        return this
    }

}

class AADataElement {
    private var name: String? = null
    private var x: Number? = null
    private var y: Number? = null
    private var color: Any? = null
    private var dataLabels: AADataLabels? = null
    private var marker: AAMarker? = null

    fun name(prop: String): AADataElement {
        name = prop
        return this
    }

    fun x(prop: Number): AADataElement {
        x = prop
        return this
    }

    fun y(prop: Number?): AADataElement {
        y = prop
        return this
    }

    fun color(prop: Any): AADataElement {
        color = prop
        return this
    }

    fun dataLabels(prop: AADataLabels): AADataElement {
        dataLabels = prop
        return this
    }

    fun marker(prop: AAMarker): AADataElement {
        marker = prop
        return this
    }
}

class AAShadow {
    private var color: String? = null
    private var offsetX: Number? = null
    private var offsetY: Number? = null
    private var opacity: Float? = null
    private var width: Number? = null

    fun color(prop: String): AAShadow {
        color = prop
        return this
    }

    fun offsetX(prop: Number?): AAShadow {
        offsetX = prop
        return this
    }

    fun offsetY(prop: Number?): AAShadow {
        offsetY = prop
        return this
    }

    fun opacity(prop: Float?): AAShadow {
        opacity = prop
        return this
    }

    fun width(prop: Number?): AAShadow {
        width = prop
        return this
    }

}
