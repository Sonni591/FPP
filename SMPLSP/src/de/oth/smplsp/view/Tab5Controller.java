package de.oth.smplsp.view;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import de.oth.smplsp.Main;
import de.oth.smplsp.util.MyGanttChartFactory;

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
	final IntervalCategoryDataset dataset = createDataset();
	JFreeChart chart = createChart(dataset);

	// ChartViewer for displaying JFreeChart with JavaFX
	ChartViewer viewer = new ChartViewer(chart);
	viewer.prefWidthProperty().bind(myStackPane.widthProperty());
	viewer.prefHeightProperty().bind(myStackPane.heightProperty());
	// viewer.addChartMouseListener(this);

	myStackPane.getChildren().add(viewer);

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
	this.main = main;
    }

    public TaskSeriesCollection createDataset() {
	final TaskSeries s1 = new TaskSeries("Scheduled");
	Double d = new Double(10.5);
	int i = d.intValue();
	Double e = new Double(20.7);
	int j = e.intValue();
	s1.add(new Task("Signoff", new SimpleTimePeriod(new Date(10), new Date(
		j))));
	s1.add(new Task("Task1", new SimpleTimePeriod(new Date(10),
		new Date(20))));
	s1.add(new Task("Task2",
		new SimpleTimePeriod(new Date(0), new Date(15))));
	s1.add(new Task("Task33", new SimpleTimePeriod(new Date(15), new Date(
		30))));
	final TaskSeriesCollection collection = new TaskSeriesCollection();
	collection.add(s1);

	return collection;

    }

    private JFreeChart createChart(final IntervalCategoryDataset dataset) {
	// final JFreeChart chart = MyGanttChartFactory.createGanttChart(
	final JFreeChart chart = MyGanttChartFactory.createGanttChart(
		"Gantt Chart Demo", // chart title
		"Task", // domain axis label
		"Time", // range axis label
		dataset, // data
		true, // include legend
		true, // tooltips
		false // urls
		);
	return chart;

    }

}
