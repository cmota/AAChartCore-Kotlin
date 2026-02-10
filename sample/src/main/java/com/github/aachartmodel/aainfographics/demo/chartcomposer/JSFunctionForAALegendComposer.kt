package com.github.aachartmodel.aainfographics.demo.chartcomposer

import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.*
import com.github.aachartmodel.aainfographics.aatools.AAColor
import com.github.aachartmodel.aainfographics.aatools.AAGradientColor
import com.github.aachartmodel.aainfographics.aatools.AAJSStringPurer

object JSFunctionForAALegendComposer {

    fun disableLegendClickEventForNormalChart(): AAOptions {
        val element1 = AASeriesElement()
            .name("Predefined symbol")
            .data(arrayOf(0.45, 0.43, 0.50, 0.55, 0.58, 0.62, 0.83, 0.39, 0.56, 0.67, 0.50, 0.34, 0.50, 0.67, 0.58, 0.29, 0.46, 0.23, 0.47, 0.46, 0.38, 0.56, 0.48, 0.36))

        val element2 = AASeriesElement()
            .name("Image symbol")
            .data(arrayOf(0.38, 0.31, 0.32, 0.32, 0.64, 0.66, 0.86, 0.47, 0.52, 0.75, 0.52, 0.56, 0.54, 0.60, 0.46, 0.63, 0.54, 0.51, 0.58, 0.64, 0.60, 0.45, 0.36, 0.67))

        val element3 = AASeriesElement()
            .name("Base64 symbol (*)")
            .data(arrayOf(0.46, 0.32, 0.53, 0.58, 0.86, 0.68, 0.85, 0.73, 0.69, 0.71, 0.91, 0.74, 0.60, 0.50, 0.39, 0.67, 0.55, 0.49, 0.65, 0.45, 0.64, 0.47, 0.63, 0.64))

        val element4 = AASeriesElement()
            .name("Custom symbol")
            .data(arrayOf(0.60, 0.51, 0.52, 0.53, 0.64, "null", "null", "null", "null", "null", "null", "null", 0.65, 0.74, 0.66, 0.65, 0.71, 0.59, 0.65, 0.77, 0.52, 0.53, 0.58, 0.53))

        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("CUSTOM LEGEND STYLE")
            .subtitle("LEGEND ON THE TOP_RIGHT SIDE WITH VERTICAL STYLE")
            .subtitleAlign(AAChartAlignType.Left)
            .markerRadius(0)
            .backgroundColor(AAColor.White)
            .dataLabelsEnabled(false)
            .yAxisGridLineWidth(0)
            .yAxisTitle("percent values")
            .stacking(AAChartStackingType.Normal)
            .colorsTheme(arrayOf("mediumspringgreen", "deepskyblue", "red", "sandybrown"))
            .series(arrayOf(element1, element2, element3, element4))

        val aaOptions = aaChartModel.aa_toAAOptions()

        //https://github.com/AAChartModel/AAChartCore-Kotlin/issues/61
        aaOptions.chart?.animation = false //turn off animation

        aaOptions.tooltip?.apply {
            backgroundColor(AAGradientColor.Firebrick)
                .style(AAStyle.style(AAColor.White))
        }

        aaOptions.yAxis?.labels?.format = "{value} %"//给y轴添加单位

        aaOptions.xAxis?.apply {
            gridLineColor(AAColor.DarkGray)
                .gridLineWidth(1)
                .minorGridLineColor(AAColor.LightGray)
                .minorGridLineWidth(0.5)
                .minorTickInterval("auto")
        }

        aaOptions.yAxis?.apply {
            gridLineColor(AAColor.DarkGray)
                .gridLineWidth(1)
                .minorGridLineColor(AAColor.LightGray)
                .minorGridLineWidth(0.5)
                .minorTickInterval("auto")
        }

        aaOptions.legend?.apply {
            enabled(true)
                .verticalAlign(AAChartVerticalAlignType.Top)
                .layout(AAChartLayoutType.Vertical)
                .align(AAChartAlignType.Right)
        }

        aaOptions.defaultOptions = AALang()
            .resetZoom("重置缩放比例")
            .thousandsSep(",")

        aaOptions.plotOptions?.series?.connectNulls(true)

        return aaOptions
    }

    //    //https://github.com/AAChartModel/AAChartKit-Swift/issues/391
    //    //https://github.com/AAChartModel/AAChartKit-Swift/issues/393
    fun disableLegendClickEventForPieChart(): AAOptions {
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor(AAColor.White)
            .title("LANGUAGE MARKET SHARES JANUARY,2020 TO MAY")
            .subtitle("virtual data")
            .dataLabelsEnabled(true) //是否直接显示扇形图数据
            .yAxisTitle("℃")
            .series(arrayOf(
                AASeriesElement()
                    .name("Language market shares")
                    .innerSize("20%") //内部圆环半径大小占比(内部圆环半径/扇形图半径),
                    .allowPointSelect(true)
                    .states(AAStates()
                        .hover(AAHover()
                            .enabled(false))) //禁用点击区块之后出现的半透明遮罩层
                    .data(arrayOf(
                        arrayOf("Java", 67),
                        arrayOf("Swift", 999),
                        arrayOf("Python", 83),
                        arrayOf("OC", 11),
                        arrayOf("Go", 30)))))
        val aaOptions = aaChartModel.aa_toAAOptions()
        aaOptions.legend?.labelFormat("{name} {percentage:.2f}%")

        //禁用饼图图例点击事件
        aaOptions.plotOptions?.series
            ?.point(AAPoint()
                .events(AAPointEvents()
                    .legendItemClick(
                        "" +
                                "function() { " +
                                "return false; " +
                                "}")))
        return aaOptions
    }


    //https://bbs.hcharts.cn/article-109-1.html
    //图表自带的图例点击事件是：
    //点击某个显示/隐藏的图例，该图例对应的serie就隐藏/显示。
    //个人觉得不合理，正常来说，有多条折线(或其他类型的图表)，点击某个图例是想只看该图例对应的数据；
    //于是修改了图例点击事件。
    //
    //实现的效果是(以折线图为例)：
    //1. 当某条折线隐藏时，点击该折线的图例 --> 该折线显示；
    //2. 当全部折线都显示时，点击某个图例 --> 该图例对应的折线显示，其他折线均隐藏；
    //3. 当只有一条折线显示时，点击该折线的图例 --> 全部折线均显示；
    //4. 其他情况，按默认处理：
    //显示 --> 隐藏；
    //隐藏 --> 显示；
    //Customized legengItemClick Event online: http://code.hcharts.cn/rencht/hhhhLv/share
    fun customLegendItemClickEvent(): AAOptions {
        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Column)
            .stacking(AAChartStackingType.Normal)
            .colorsTheme(arrayOf("#fe117c", "#ffc069", "#06caf4", "#7dffc0")) //设置主题颜色数组
            .markerRadius(0)
            .series(arrayOf(
                AASeriesElement()
                    .name("Tokyo")
                    .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),
                AASeriesElement()
                    .name("NewYork")
                    .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
                AASeriesElement()
                    .name("London")
                    .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
                AASeriesElement()
                    .name("Berlin")
                    .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))))

        val aaOptions = aaChartModel.aa_toAAOptions()

        aaOptions.legend?.apply {
            enabled(true)
            .align(AAChartAlignType.Right) //设置图例位于水平方向上的右侧
            .layout(AAChartLayoutType.Vertical) //设置图例排列方式为垂直排布
            .verticalAlign(AAChartVerticalAlignType.Top) //设置图例位于竖直方向上的顶部
        }

        //自定义图例点击事件
        aaOptions.plotOptions?.series?.events = AASeriesEvents()
            .legendItemClick(
                """function(event) {
        function getVisibleMode(series, serieName) {
            var allVisible = true;
            var allHidden = true;
            for (var i = 0; i < series.length; i++) {
                if (series[i].name == serieName)
                    continue;
                allVisible &= series[i].visible;
                allHidden &= (!series[i].visible);
            }
            if (allVisible && !allHidden)
                return 'all-visible';
            if (allHidden && !allVisible)
                return 'all-hidden';
            return 'other-cases';
        }
        
        var series = this.chart.series;
        var mode = getVisibleMode(series, this.name);
        var enableDefault = false;
        if (!this.visible) {
            enableDefault = true;
        }
        else if (mode == 'all-visible') {
            var seriesLength = series.length;
            for (var i = 0; i < seriesLength; i++) {
                var serie = series[i];
                serie.hide();
            }
            this.show();
        }
        else if (mode == 'all-hidden') {
            var seriesLength = series.length;
            for (var i = 0; i < seriesLength; i++) {
                var serie = series[i];
                serie.show();
            }
        }
        else {
            enableDefault = true;
        }
        return enableDefault;
    }"""
            )
        return aaOptions
    }

    // https://github.com/AAChartModel/AAChartCore-Kotlin/issues/265
    // plotLines + virtual legend proxy series (data: [null]) demo
    fun plotLinesWithVirtualSeriesLegendProxy(): AAOptions {
        val dateTickX = 1
        val dateLabel = "0622"

        val columnSeriesData: Array<Any> = arrayOf(
            linkedMapOf<String, Any>(
                "x" to 0.82,
                "y" to 49800,
                "color" to "#f3b300",
                "customName" to "营业额低值",
            ),
            linkedMapOf<String, Any>(
                "x" to 0.94,
                "y" to 590161.68,
                "color" to "#2563eb",
                "customName" to "营业额",
            ),
            linkedMapOf<String, Any>(
                "x" to 1.06,
                "y" to 9733.36,
                "color" to "#00c2a8",
                "customName" to "纯收",
            ),
            linkedMapOf<String, Any>(
                "x" to 1.18,
                "y" to 1744,
                "color" to "#d0b400",
                "customName" to "毛收",
            ),
        )

        val buildPlotLineJS = """
(function (plotLineId) {
    switch (plotLineId) {
        case "revenue-fixed-high":
            return {id:"revenue-fixed-high", value:900000, color:"#f39c12", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#f39c12;font-size:9px\">营业额固定定值900000.0</span>"}};
        case "revenue-avg":
            return {id:"revenue-avg", value:590161.68, color:"#ef9cd3", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#2f6fd9;font-size:9px\">营业额平均值590161.68</span>"}};
        case "revenue-fixed-mid":
            return {id:"revenue-fixed-mid", value:500000, color:"#d6b33e", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#f39c12;font-size:9px\">营业额固定定值500000.0</span>"}};
        case "net-fixed":
            return {id:"net-fixed", value:9733.36, color:"#2ec7ff", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#2ec7ff;font-size:9px\">纯收固定值9733.36</span>"}};
        case "net-avg":
            return {id:"net-avg", value:4987, color:"#4f8cff", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#4f8cff;font-size:9px\">纯收平均值4987</span>"}};
        case "gross-fixed":
            return {id:"gross-fixed", value:1744, color:"#20b486", dashStyle:"Solid", width:1, zIndex:6, label:{useHTML:true, align:"right", x:-20, y:-8, text:"<span style=\"color:#20b486;font-size:9px\">毛收固定值1744</span>"}};
        default:
            return null;
    }
})
""".trimIndent()

        val hasPlotLineJS = """
(function (axis, plotLineId) {
    var list = axis && axis.plotLinesAndBands ? axis.plotLinesAndBands : [];
    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        if (item && item.options && item.options.id === plotLineId) {
            return true;
        }
    }
    return false;
})
""".trimIndent()

        val proxyShowEventJSRaw = """
(function () {
    var plotLineId = this.options && this.options.customPlotLineId;
    if (!plotLineId) {
        return;
    }
    var axis = this.chart.yAxis && this.chart.yAxis[0];
    if (!axis) {
        return;
    }

    var buildPlotLine = $buildPlotLineJS;
    var hasPlotLine = $hasPlotLineJS;
    if (!hasPlotLine(axis, plotLineId)) {
        var line = buildPlotLine(plotLineId);
        if (line) {
            axis.addPlotLine(line);
        }
    }
})
""".trimIndent()
        val proxyShowEventJS = AAJSStringPurer.pureAnonymousJSFunctionString(proxyShowEventJSRaw)!!

        val proxyHideEventJSRaw = """
(function () {
    var plotLineId = this.options && this.options.customPlotLineId;
    if (!plotLineId) {
        return;
    }
    var axis = this.chart.yAxis && this.chart.yAxis[0];
    if (axis) {
        axis.removePlotLine(plotLineId);
    }
})
""".trimIndent()
        val proxyHideEventJS = AAJSStringPurer.pureAnonymousJSFunctionString(proxyHideEventJSRaw)!!

        val chartLoadJS = """
(function () {
    var axis = this.yAxis && this.yAxis[0];
    if (!axis) {
        return;
    }

    var buildPlotLine = $buildPlotLineJS;
    var hasPlotLine = $hasPlotLineJS;
    var plotLineIds = ["revenue-fixed-high", "revenue-avg", "revenue-fixed-mid", "net-fixed", "net-avg", "gross-fixed"];
    for (var i = 0; i < plotLineIds.length; i++) {
        var plotLineId = plotLineIds[i];
        if (!hasPlotLine(axis, plotLineId)) {
            var line = buildPlotLine(plotLineId);
            if (line) {
                axis.addPlotLine(line);
            }
        }
    }
})
""".trimIndent()

        val columnSeries = AASeriesElement()
            .id("single-column-series")
            .type(AAChartType.Column)
            .name("营业数据")
            .showInLegend(false)
            .zIndex(2)
            .data(columnSeriesData)

        val proxySeriesArr: Array<Any> = arrayOf(
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-revenue-fixed-high",
                "type" to AAChartType.Line.value,
                "name" to "营收固定900000",
                "data" to arrayOf<Any?>(null),
                "color" to "#f39c12",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "revenue-fixed-high",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-revenue-avg",
                "type" to AAChartType.Line.value,
                "name" to "营收平均值",
                "data" to arrayOf<Any?>(null),
                "color" to "#ef9cd3",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "revenue-avg",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-revenue-fixed-mid",
                "type" to AAChartType.Line.value,
                "name" to "营收固定500000",
                "data" to arrayOf<Any?>(null),
                "color" to "#d6b33e",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "revenue-fixed-mid",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-net-fixed",
                "type" to AAChartType.Line.value,
                "name" to "纯收固定值",
                "data" to arrayOf<Any?>(null),
                "color" to "#2ec7ff",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "net-fixed",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-net-avg",
                "type" to AAChartType.Line.value,
                "name" to "纯收平均值",
                "data" to arrayOf<Any?>(null),
                "color" to "#4f8cff",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "net-avg",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
            linkedMapOf<String, Any?>(
                "id" to "legend-proxy-gross-fixed",
                "type" to AAChartType.Line.value,
                "name" to "毛收固定值",
                "data" to arrayOf<Any?>(null),
                "color" to "#20b486",
                "dashStyle" to "Solid",
                "lineWidth" to 2,
                "showInLegend" to true,
                "enableMouseTracking" to false,
                "marker" to linkedMapOf("enabled" to false),
                "customPlotLineId" to "gross-fixed",
                "events" to linkedMapOf(
                    "show" to proxyShowEventJS,
                    "hide" to proxyHideEventJS,
                ),
            ),
        )

        val aaOptions = AAOptions()
            .chart(
                AAChart()
                    .type(AAChartType.Column)
                    .backgroundColor(AAColor.White)
                    .events(AAChartEvents().load(chartLoadJS))
                    .margin(arrayOf(10, 18, 90, 58))
            )
            .xAxis(
                AAXAxis()
                    .min(0.6)
                    .max(1.4)
                    .tickPositions(arrayOf<Any>(dateTickX))
                    .lineColor("#c6c6c6")
                    .lineWidth(1)
                    .tickLength(0)
                    .labels(
                        AALabels()
                            .style(AAStyle().color("#7a7a7a").fontSize(12))
                            .formatter(
                                """
function () {
    return Number(this.value) === $dateTickX ? '$dateLabel' : '';
}"""
                            )
                    )
            )
            .yAxis(
                AAYAxis()
                    .min(0)
                    .max(1250000)
                    .tickPositions(arrayOf<Any>(0, 250000, 500000, 750000, 1000000, 1250000))
                    .lineWidth(1)
                    .gridLineWidth(0)
                    .labels(
                        AALabels()
                            .style(AAStyle().color("#666666").fontSize(12))
                            .formatter(
                                """
function () {
    var value = Number(this.value);
    if (value === 0) return '0';
    if (value >= 1000000) {
        var million = value / 1000000;
        return (million % 1 === 0 ? million : million.toFixed(2)) + '百万';
    }
    return (value / 10000) + '万';
}"""
                            )
                    )
            )
            .tooltip(
                AATooltip()
                    .shared(false)
                    .formatter(
                        """
function () {
    var pointName = this.point && this.point.options && this.point.options.customName;
    var nameText = pointName ? pointName : '值';
    return '$dateLabel' + '<br/>' + nameText + ': <b>' + this.y + '</b>';
}"""
                    )
            )
            .legend(
                AALegend()
                    .enabled(true)
                    .layout(AAChartLayoutType.Horizontal)
                    .align(AAChartAlignType.Center)
                    .verticalAlign(AAChartVerticalAlignType.Bottom)
                    .y(8)
                    .itemMarginTop(4)
                    .itemStyle(AAItemStyle().fontSize(11))
            )
            .plotOptions(
                AAPlotOptions()
                    .column(
                        AAColumn()
                            .pointWidth(14)
                            .groupPadding(0.18f)
                            .pointPadding(0.1f)
                            .borderWidth(0)
                            .minPointLength(8)
                            .dataLabels(
                                AADataLabels()
                                    .enabled(true)
                                    .crop(false)
                                    .overflow("allow")
                                    .style(
                                        AAStyle()
                                            .color("#333333")
                                            .fontSize(11)
                                            .textOutline("none")
                                    )
                                    .formatter(
                                        """
function () {
    var y = Number(this.y);
    if (y === 49800) return '4.98万';
    if (y === 590161.68) return '590161.68';
    if (y === 9733.36) return '9733.36';
    if (y === 1744) return '1744';
    return String(y);
}"""
                                    )
                            )
                    )
                    .series(
                        AASeries()
                            .states(AAStates().inactive(AAInactive().opacity(1)))
                    )
            )
            .series(
                arrayOf(
                    columnSeries,
                    *proxySeriesArr
                )
            )

        return aaOptions
    }
}
