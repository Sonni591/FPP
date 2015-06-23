package de.oth.smplsp.util;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.urls.StandardCategoryURLGenerator;

public class MyGanttChartFactory extends ChartFactory {

    protected static ChartTheme currentTheme = new StandardChartTheme("JFree");

    public static JFreeChart createGanttChart(String title,
	    String categoryAxisLabel, String valueAxisLabel,
	    TaskSeriesCollection dataset, boolean legend, boolean tooltips,
	    boolean urls) {

	CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
	NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
	// CategoryItemRenderer renderer = new GanttRenderer();
	MyRenderer renderer = new MyRenderer(dataset);
	if (tooltips) {
	    renderer.setBaseToolTipGenerator(new IntervalCategoryToolTipGenerator(
		    "{1}: {3} - {4}", NumberFormat.getNumberInstance()));
	}
	if (urls) {
	    renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator());
	}
	renderer.setSeriesPaint(0, Color.GRAY);
	renderer.setSeriesPaint(0, Color.GRAY);
	renderer.setShadowVisible(false);

	LegendItemCollection chartLegend = new LegendItemCollection();

	Shape shape = new Rectangle(10, 10);
	chartLegend.add(new LegendItem("Rüstzeit", null, null, null, shape,
		new Color(50, 50, 50)));
	chartLegend.add(new LegendItem("Produktion", null, null, null, shape,
		new Color(150, 150, 150)));

	CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
		renderer);

	plot.setOrientation(PlotOrientation.HORIZONTAL);
	plot.setFixedLegendItems(chartLegend);

	JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
		plot, legend);
	return chart;
    }
}
