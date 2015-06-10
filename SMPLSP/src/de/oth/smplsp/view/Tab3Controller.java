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

public class Tab3Controller {

    @FXML
    private AnchorPane chartPane;
    @FXML
    private StackPane myStackPane;
    @FXML
    private Canvas myCanvas;

    private List<ProductionProcess> processes;

    // Reference to the main application.
    private Main main;
    private RootLayoutController root;

    private int fontSize = 12;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public Tab3Controller() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	showChart();
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    public void showChart() {
	JFreeChart chart = createChart(createInitDataset());
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	myStackPane.getChildren().add(viewer);
    }

    public void showChart(List<ProductionProcess> processes) {
	this.processes = processes;
	JFreeChart chart = createChart(createDataset(processes));
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	myStackPane.getChildren().add(viewer);
    }

    // Create emptydataset when no Data available
    public TaskSeriesCollection createInitDataset() {
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	return taskseriescollection;
    }

    // Create DataSet for the Data in the Tab2
    public TaskSeriesCollection createDataset(List<ProductionProcess> processes) {
	TaskSeries taskseries = new TaskSeries("Schedule");
	Task tmpTask = new Task("desc", 0, 0);
	for (ProductionProcess a : processes) {
	    if (a.getK() != null) {
		tmpTask = new Task("desc", 0, 0);
		tmpTask.setDescription(a.getK().getValue().toString());
		tmpTask.setStart(a.getStart().doubleValue());
		tmpTask.setEnd(a.getEnde_zyklus2().doubleValue());
		Task subTask1 = new Task("Rüstzeit",
			a.getStart().doubleValue(), a.getEnde().doubleValue());
		Task subTask3 = new Task("Rüstzeit", a.getStart_zyklus2()
			.doubleValue(), a.getEnde_zyklus2().doubleValue());
		tmpTask.addSubtask(subTask1);
		tmpTask.addSubtask(subTask3);
	    } else {
		Task subTask2 = new Task("Produktion", a.getStart()
			.doubleValue(), a.getEnde().doubleValue());
		Task subTask4 = new Task("Produktion", a.getStart_zyklus2()
			.doubleValue(), a.getEnde_zyklus2().doubleValue());

		tmpTask.setEnd(a.getEnde_zyklus2().doubleValue());
		tmpTask.addSubtask(subTask2);
		tmpTask.addSubtask(subTask4);
		taskseries.add(tmpTask);
	    }

	}
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	taskseriescollection.add(taskseries);
	return taskseriescollection;
    }

    public void zoomIn() {
	fontSize += 2;
	JFreeChart chart = createChart(createDataset(processes));
	CategoryPlot plot = chart.getCategoryPlot();
	Font font = plot.getRangeAxis().getLabelFont();
	Font customFont = new Font(font.getFontName(), font.getStyle(),
		fontSize);
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

    public void zoomOut() {
	fontSize -= 2;
	JFreeChart chart = createChart(createDataset(processes));
	CategoryPlot plot = chart.getCategoryPlot();
	Font font = plot.getRangeAxis().getLabelFont();
	Font customFont = new Font(font.getFontName(), font.getStyle(),
		fontSize);
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

    private JFreeChart createChart(final TaskSeriesCollection dataset) {
	// final JFreeChart chart = MyGanttChartFactory.createGanttChart(
	final JFreeChart chart = MyGanttChartFactory.createGanttChart("", // chart
									  // title
		"k", // domain axis label
		"Zeit", // range axis label
		dataset, // data
		true, // include legend
		false, // tooltips
		false // urls
		);

	return chart;
    }
}
