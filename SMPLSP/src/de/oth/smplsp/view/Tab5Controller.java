package de.oth.smplsp.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;

import de.oth.smplsp.Main;
import de.oth.smplsp.model.ProductionProcess;
import de.oth.smplsp.util.MyGanttChartFactory;
import de.oth.smplsp.util.Task;
import de.oth.smplsp.util.TaskSeries;
import de.oth.smplsp.util.TaskSeriesCollection;

public class Tab5Controller {

    // References
    @FXML
    private AnchorPane chartPane;
    @FXML
    private StackPane myStackPane;
    @FXML
    private Canvas myCanvas;

    // Reference to the main application.
    private Main main;
    private RootLayoutController root;

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

    public void showChart() {
	JFreeChart chart = createChart(createInitDataset());
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	// viewer.addChartMouseListener(this);
	myStackPane.getChildren().add(viewer);
    }

    public void showChart(List<ProductionProcess> processes) {
	JFreeChart chart = createChart(createDataset(processes));
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	// viewer.addChartMouseListener(this);
	myStackPane.getChildren().add(viewer);
    }

    // Create emptydataset when no Data available
    public TaskSeriesCollection createInitDataset() {
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	return taskseriescollection;
    }

    // Create Dataset for Chart
    public TaskSeriesCollection createDataset(List<ProductionProcess> processes) {
	TaskSeries taskseries = new TaskSeries("Schedule");
	Task tmpTask = new Task("desc", 0, 0);
	for (ProductionProcess a : processes) {
	    if (a.getK() != null) {
		tmpTask = new Task("desc", 0, 0);
		tmpTask.setDescription(a.getK().getValue().toString());
		tmpTask.setStart(a.getStart().doubleValue());
		tmpTask.setEnd(a.getEnde().doubleValue());
	    } else {
		Task subTask1 = new Task("Rüstzeit", tmpTask.getStart(),
			tmpTask.getEnd());
		Task subTask2 = new Task("Produktion", a.getStart()
			.doubleValue(), a.getEnde().doubleValue());
		tmpTask.setEnd(tmpTask.getEnd() + a.getEnde().doubleValue());
		tmpTask.addSubtask(subTask1);
		tmpTask.addSubtask(subTask2);
		taskseries.add(tmpTask);
	    }

	}
	TaskSeriesCollection taskseriescollection = new TaskSeriesCollection();
	taskseriescollection.add(taskseries);
	return taskseriescollection;
    }

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
