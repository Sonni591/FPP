package de.oth.smplsp.view;

import java.awt.Font;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.CategoryPlot;

import de.oth.smplsp.Main;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.MyGanttChartFactory;
import de.oth.smplsp.util.Task;
import de.oth.smplsp.util.TaskSeries;
import de.oth.smplsp.util.TaskSeriesCollection;

public class Tab5Controller {

    @FXML
    private AnchorPane chartPane;
    @FXML
    private StackPane myStackPane;
    @FXML
    private Canvas myCanvas;

    /**
     * reference to the main application
     */
    private Main main;
    private RootLayoutController root;

    private List<ProductionProcess> processes;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab5Controller() {

    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	showChart();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    /**
     * shows an empty chart, if there was no calculation made
     */
    public void showChart() {
	JFreeChart chart = createChart(createInitDataset());
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	// viewer.addChartMouseListener(this);
	myStackPane.getChildren().add(viewer);
    }

    /**
     * generates a chart for the data from tab 4 and shows it in the stackpane
     */
    public void showChart(List<ProductionProcess> processes) {
	JFreeChart chart = createChart(createDataset(processes));
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	// viewer.addChartMouseListener(this);
	myStackPane.getChildren().add(viewer);
    }

    /**
     * creates a empty dataset, if there was no calculation made
     */
    public TaskSeriesCollection createInitDataset() {
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	return taskseriescollection;
    }

    /**
     * creates a dataset from the data in tab 4. It generates one task for each
     * row, which contains 4 subtasks.
     */
    public TaskSeriesCollection createDataset(List<ProductionProcess> processes) {
	this.processes = processes;
	TaskSeries taskseries = new TaskSeries("Schedule");
	Task tmpTask = new Task("desc", 0, 0);
	for (ProductionProcess a : processes) {
	    if (a.getK() != null) {
		tmpTask = new Task("desc", 0, 0);
		tmpTask.setDescription(a.getK().getValue().toString());
		tmpTask.setStart(a.getStartCycle1().doubleValue());
		tmpTask.setEnd(a.getEndCycle2().doubleValue());
		Task subTask1 = new Task("Rüstzeit", a.getStartCycle1()
			.doubleValue(), a.getEndCycle1().doubleValue());
		Task subTask3 = new Task("Rüstzeit", a.getStartCycle2()
			.doubleValue(), a.getEndCycle2().doubleValue());
		tmpTask.addSubtask(subTask1);
		tmpTask.addSubtask(subTask3);
	    } else {
		Task subTask2 = new Task("Produktion", a.getStartCycle1()
			.doubleValue(), a.getEndCycle1().doubleValue());
		Task subTask4 = new Task("Produktion", a.getStartCycle2()
			.doubleValue(), a.getEndCycle2().doubleValue());

		tmpTask.setEnd(a.getEndCycle2().doubleValue());
		tmpTask.addSubtask(subTask2);
		tmpTask.addSubtask(subTask4);
		taskseries.add(tmpTask);

	    }

	}
	taskseries.add(new Task("∩", 0, 0));
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	taskseriescollection.add(taskseries);
	return taskseriescollection;
    }

    /**
     * Handles the zoom of the JFreeChart Gant Chart. The labels of the x- and
     * y-axis, the description of both axis as well as the legend will be scaled
     * to the zoomfactor defined in the @class Zoomer using the chartFontSize
     */
    public void handleZoom() {
	JFreeChart chart = null;
	if (processes == null) {
	    chart = createChart(createInitDataset());
	} else {
	    chart = createChart(createDataset(processes));
	}
	CategoryPlot plot = chart.getCategoryPlot();
	Font font = plot.getRangeAxis().getLabelFont();
	Font customFont = new Font(font.getFontName(), font.getStyle(), root
		.getZoomer().getChartFontSize());
	plot.getRangeAxis().setLabelFont(customFont);
	plot.getRangeAxis().setTickLabelFont(customFont);
	plot.getDomainAxis().setLabelFont(customFont);
	plot.getDomainAxis().setTickLabelFont(customFont);
	for (int i = 0; i < plot.getLegendItems().getItemCount(); i++) {
	    plot.getLegendItems().get(i).setLabelFont(customFont);
	}
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	myStackPane.getChildren().add(viewer);
    }

    /**
     * generates a chart with with the given dataset and names the x-axis zeit
     * and the y-axis k
     */
    private JFreeChart createChart(final TaskSeriesCollection dataset) {
	// final JFreeChart chart = MyGanttChartFactory.createGanttChart(
	final JFreeChart chart = MyGanttChartFactory.createGanttChart("", // chart
									  // title
		"k", // domain axis label
		"Zeit", // range axis label
		dataset, // data
		true, // include legend
		true, // tooltips
		false // urls
		);

	return chart;
    }

}
