package de.oth.smplsp.zoom;

import de.oth.smplsp.view.RootLayoutController;
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

    // global fontsize
    private int fontsize = 13;
    private int latexFontSize = 22;

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
	this.fontsize = fontsize;
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
	this.latexFontSize = latexFontSize;
    }

    /**
     * increases all fontsizes and rescales the application
     */
    public void handleZoomIn() {
	fontsize++;
	latexFontSize += 2;
	rescaleEverything();
    }

    /**
     * decreases all fontsizes and rescales the application
     */
    public void handleZoomOut() {
	fontsize--;
	latexFontSize -= 2;
	rescaleEverything();
    }

    /**
     * resizes every font in the application
     */
    public void rescaleEverything() {

	// zoom operations on the root layout
	root.handleZoomEveryCSSStyle();
	root.showExplanationComponent();
	root.handleZoomSouthBarButtons(fontsize);
	// root.handleZoomMenuBar();

	// zoom operations on tab 1
	// tab1Controller.handleZoomTable(fontsize);
	tab1Controller.handleZoomButtons(fontsize);

	// zoom operations on tab 2
	// TODO

	// zoom operations on tab 3
	// TODO

	// zoom operations on tab 4
	if (!System.getProperty("os.name").equals("Mac OS X")) {
	    tab4Controller.showTOptAndTMinFormulas();
	}

	// zoom operations on tab 5
	// TODO

	// other zoom operations
	// TODO

    }

    public void init(RootLayoutController rootLayoutController) {
	root = rootLayoutController;
	tab1Controller = root.getTab1Controller();
	tab2Controller = root.getTab2Controller();
	tab3Controller = root.getTab3Controller();
	tab4Controller = root.getTab4Controller();
	tab5Controller = root.getTab5Controller();
    }

   /**
    * modify the font size of the used stylesheet
    * @return
    */
    public String getStyleFXFontSize() {
	String style = "-fx-font-size: " + fontsize + ";";
	return style;
    }

}
