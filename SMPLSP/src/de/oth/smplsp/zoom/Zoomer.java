package de.oth.smplsp.zoom;

import de.oth.smplsp.view.RootLayoutController;
import de.oth.smplsp.view.SettingsDialogController;
import de.oth.smplsp.view.Tab1Controller;
import de.oth.smplsp.view.Tab2Controller;
import de.oth.smplsp.view.Tab3Controller;
import de.oth.smplsp.view.Tab4Controller;
import de.oth.smplsp.view.Tab5Controller;

public class Zoomer {

    // reference to all layouts
    private RootLayoutController root;
    private Tab1Controller tab1Controller;
    private Tab2Controller tab2Controller;
    private Tab3Controller tab3Controller;
    private Tab4Controller tab4Controller;
    private Tab5Controller tab5Controller;
    private SettingsDialogController settingsDialogController;

    // global fontsize
    private static int fontsize = 13;
    private static int latexFontSize = 22;
    private static int chartFontSize = 12;

    // default font size
    private final int defaultFontsize = 13;
    private final int defaultLatexFontSize = 22;
    private final int defaultChartFontSize = 12;

    // TODO: unused now, remove if unnecessary
    // // difference the different fontsizes
    // private int tableFontsize = 20;
    // private int buttonFontsize = 20;
    // private int explanationComponentFontsize = 20;
    // private int southBarLabelFontsize = 20;
    // private int menuFontsize = 20;
    // private int tabLabelFontsize = 20;
    // private int chartLegendFontsize = 20;
    // private int chartAxisFontsize = 20;
    // private int chartAxisLabelFontsize = 20;
    // private int applicationHeaderFontsize = 20;

    public void initializeFontsizes() {
	// TODO
    }

    /**
     * @return the fontsize
     */
    public int getFontsize() {
	return fontsize;
    }

    /**
     * @param fontsize
     *            the fontsize to set
     */
    public void setFontsize(int fontsize) {
	if (fontsize > 1) {
	    this.fontsize = fontsize;
	}
    }

    /**
     * @return the latexFontSize
     */
    public int getLatexFontSize() {
	return latexFontSize;
    }

    /**
     * @param latexFontSize
     *            the latexFontSize to set
     */
    public void setLatexFontSize(int latexFontSize) {
	if (fontsize > 1) {
	    this.latexFontSize = latexFontSize;
	}
    }

    /**
     * @return the chartFontSize
     */
    public int getChartFontSize() {
	return chartFontSize;
    }

    /**
     * @param chartFontSize
     *            the chartFontSize to set
     */
    public void setChartFontSize(int chartFontSize) {
	if (chartFontSize > 1) {
	    this.chartFontSize = chartFontSize;
	}
    }

    /**
     * increases all fontsizes and rescales the application
     */
    public void handleZoomIn() {
	fontsize++;
	latexFontSize += 2;
	chartFontSize += 2;
	rescaleMainApplication();
	rescaleSettingsDialog();
    }

    /**
     * decreases all fontsizes and rescales the application
     */
    public void handleZoomOut() {
	if (fontsize > 1) {
	    fontsize--;
	}
	if (latexFontSize > 2) {
	    latexFontSize -= 2;
	}
	if (chartFontSize > 2) {
	    chartFontSize -= 2;
	}
	rescaleMainApplication();
	rescaleSettingsDialog();
    }

    /**
     * resizes every font in the application
     */
    public void rescaleMainApplication() {
	if (root != null) {
	    // zoom operations on the root layout
	    root.handleZoomEveryCSSStyle();
	    root.showExplanationComponent();
	    root.handleZoomSouthBarButtons(fontsize);

	    // zoom operations on tab 1
	    tab1Controller.handleZoomButtons(fontsize);

	    // zoom operations on tab 2

	    // zoom operations on tab 3
	    tab3Controller.handleZoom();

	    // zoom operations on tab 4
	    if (!System.getProperty("os.name").equals("Mac OS X")) {
		tab4Controller.showTOptAndTMinFormulas();
	    }

	    // zoom operations on tab 5
	    tab5Controller.handleZoom();

	}
    }

    public void rescaleSettingsDialog() {
	if (settingsDialogController != null) {
	    settingsDialogController.handleZoomCSSStyle();
	}
    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
	tab1Controller = root.getTab1Controller();
	tab2Controller = root.getTab2Controller();
	tab3Controller = root.getTab3Controller();
	tab4Controller = root.getTab4Controller();
	tab5Controller = root.getTab5Controller();
    }

    public void init(SettingsDialogController settingsDialogController) {
	this.settingsDialogController = settingsDialogController;
    }

    /**
     * modify the font size of the used stylesheet
     * 
     * @return
     */
    public String getStyleFXFontSize() {
	String style = "-fx-font-size: " + fontsize + ";";
	return style;
    }

    public void resetZoomLevel() {
	fontsize = defaultFontsize;
	latexFontSize = defaultLatexFontSize;
	chartFontSize = defaultChartFontSize;
	rescaleMainApplication();
	rescaleSettingsDialog();
    }

}
