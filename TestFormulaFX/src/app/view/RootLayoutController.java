package app.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTextPane;

import model.LotSchedulingResult;
import model.Product;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import algorithm.IBasicLotSchedulingAlgorithm;
import algorithm.MehrproduktLosgroessen;
import app.MainApp;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

public class RootLayoutController {

    @FXML
    private TextFlow textFlow;

    @FXML
    private SwingNode swingNode;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label label;

    @FXML
    private Button buttonPlus;

    @FXML
    private Button buttonMinus;

    // global parameters for the font size
    private int fontsize = 20;

    // Reference to the main application.
    private MainApp mainApp;

    // Formatter to disable the scientific notation
    private NumberFormat formatter = DecimalFormat.getInstance();

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public RootLayoutController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        // show sample LaTeX
        showLatex();
        // limit the fraction digits of the numbers to 8
        formatter.setMaximumFractionDigits(8);

    }

    public void showLatex() {
        // TeX in the center of the Border Pane
        // will be displayed in a ScrollPane

        // String latex = " A_1 \\\\ " + "\\sqrt{2} \\\\ "
        // + "\\sqrt{ \\frac{-b \\pm \\sqrt {b^2-4ac}} {2a} } \\\\"
        // + "\\textrm{das ist unser Text der Erklärkomponente } \\\\"
        // + "noch \\\\" + "ein \\\\" + "paar \\\\"
        // + "Zeilen \\\\ im Mathemodus";

        List<Product> products = getTestProducts();
        IBasicLotSchedulingAlgorithm algorithm = new MehrproduktLosgroessen(
                products);
        LotSchedulingResult result = algorithm.calculateInTotal();

        // Farbkodierung: D = blau, p = Plum, s = Red, h = Green, q =
        // OliveGreen, rho = RubineRed, topt = Dandelion
        String latex = "\\textrm{Statische Formeln: } \\\\";
        latex += getLosgroessenFormel();
        latex += "\\\\";
        latex += getProduktionsdauerFormel();
        latex += "\\\\";
        latex += getProduktspezifischeProdukzionszyklusFormel();
        latex += "\\\\";
        latex += "\\textrm{mit }" + getAuslastungFormel();
        latex += "\\\\";
        latex += getGemeinsameProduktionszyklusFormel();
        latex += "\\\\";
        latex += getMinimalenProduktionszyklusFormel();
        latex += "\\\\";
        latex += getLosgroesseMehrproduktFormel();
        latex += "\\\\ \\textrm{Dynamische Formeln: } \\\\";
        latex += getAuslastungMitParameterFormel(10.4, 128.5714);
        latex += "\\\\";
        latex += getLosgroessenFormel(10.4, 190, 0.000689, 128.5714);
        latex += "\\\\";
        latex += getProduktionsdauerFormel(2498.13, 128.5714);
        latex += "\\\\";
        latex += getProduktspezifischeProdukzionszyklusFormel(190, 0.000689,
                10.4, 0.08089);
        latex += "\\\\";
        latex += getLosgroesseMehrproduktFormel(10.4, 103.488);
        latex += "\\\\";

        latex += getGemeinsameProduktionszyklusFormel(result.getProducts());
        latex += "\\\\";
        latex += getMinimalenProduktionszyklusFormel(result.getProducts());

        TeXFormula tex = new TeXFormula(latex);

        // Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, 40);
        Icon icon = tex.createTeXIcon(TeXConstants.ALIGN_CENTER, fontsize);

        // generate a JTextPane that will be displayed in a SwingNode in JavaFX
        JTextPane pane = new JTextPane();
        pane.insertIcon(icon);
        swingNode.setContent(pane);
    }

    public List<Product> getTestProducts() {
        List<Product> products = new ArrayList<Product>();
        Product product1 = new Product(1, 10.4, 128.5714, 2.0, 190.0, 0.000689);
        products.add(product1);
        Product product2 = new Product(2, 0.8, 32.43243, 2.0, 210.0, 0.010280);
        products.add(product2);
        Product product3 = new Product(3, 3.6, 40.44944, 2.0, 140.0, 0.021853);
        products.add(product3);
        Product product4 = new Product(4, 12.22222, 51.42857, 2.0, 100.0,
                0.005105);
        products.add(product4);
        Product product5 = new Product(5, 2.666667, 55.38462, 2.0, 150.0,
                0.005920);
        products.add(product5);
        Product product6 = new Product(6, 4, 40.90909, 2.0, 110.0, 0.004753);
        products.add(product6);
        Product product7 = new Product(7, 12.44444, 45.56962, 2.0, 160.0,
                0.003530);
        products.add(product7);
        return products;
    }

    private String getAuslastungFormel() {
        String formel = "\\textcolor{RubineRed}{\\rho} = \\frac{ \\textcolor{Blue} D}{ \\textcolor{Plum}p}";
        return formel;
    }

    private String getAuslastungMitParameterFormel(double d, double p) {
        String formel = "\\textcolor{RubineRed}{\\rho} = \\frac{ \\textcolor{Blue}{"
                + d + "}}{ \\textcolor{Plum}{" + formatter.format(p) + "}}";
        return formel;
    }

    private String getLosgroessenFormel() {
        String formel = "\\textcolor{OliveGreen}{q_{k}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{Blue} {D_k} \\cdot \\textcolor{Red}{s_k}}{\\textcolor{Green}{h_k} \\cdot (1 - \\frac{\\textcolor{Blue}  {D_k}}{ \\textcolor{Plum}{p_k}})}}";
        return formel;
    }

    private String getLosgroessenFormel(double d, double s, double h, double p) {

        String formel = "\\textcolor{OliveGreen}{q_{k}^{opt}} = \\sqrt{\\frac{2 \\cdot \\textcolor{Blue}{"
                + formatter.format(d)
                + "}\\cdot \\textcolor{Red}{"
                + formatter.format(s)
                + "}}{\\textcolor{Green}{"
                + formatter.format(h)
                + "}\\cdot (1 - \\frac{ \\textcolor{Blue}{"
                + formatter.format(d)
                + "}}{ \\textcolor{Plum}{"
                + formatter.format(p) + "}})}}";
        return formel;
    }

    private String getProduktionsdauerFormel() {
        String formel = "t_p = \\frac{\\textcolor{OliveGreen}q}{ \\textcolor{Plum}p}";
        return formel;
    }

    private String getProduktionsdauerFormel(double q, double p) {
        String formel = "t_p = \\frac{\\textcolor{OliveGreen}{"
                + formatter.format(q) + "}}{ \\textcolor{Plum}{"
                + formatter.format(p) + "}}";
        return formel;
    }

    private String getProduktspezifischeProdukzionszyklusFormel() {
        String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{Red}s}{\\textcolor{Green}h \\cdot \\textcolor{Blue} D \\cdot (1 - \\textcolor{RubineRed}{\\rho})}}";
        return formel;
    }

    private String getProduktspezifischeProdukzionszyklusFormel(double s,
            double h, double d, double rho) {
        String formel = "t_{opt} = \\sqrt{\\frac{2 \\cdot \\textcolor{Red}{"
                + formatter.format(s) + "}}{\\textcolor{Green}{"
                + formatter.format(h) + "} \\cdot \\textcolor{Blue}{"
                + formatter.format(d) + "} \\cdot (1 - \\textcolor{RubineRed}{"
                + formatter.format(rho) + "})}}";
        return formel;
    }

    private String getGemeinsameProduktionszyklusFormel() {
        String formel = "\\textcolor{Dandelion}{T_{opt}} = \\sqrt{\\frac{2 \\cdot \\sum_{k=1}^{K} \\textcolor{Red}{s_k}}{\\sum_{k=1}^{K}{\\textcolor{Green}{h_k} \\cdot \\textcolor{Blue}{D_k} \\cdot (1- \\textcolor{RubineRed}{{\\rho}_k})}}}";
        return formel;
    }

    private String getGemeinsameProduktionszyklusFormel(List<Product> products) {
        String formel = "\\textcolor{Dandelion}{T_{opt}} = \\sqrt{\\frac{2 \\cdot ( ";
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (i == products.size() - 1) {
                formel += " \\textcolor{Red}{"
                        + formatter.format(product.getS()) + "})}";
            } else {
                formel += " \\textcolor{Red}{"
                        + formatter.format(product.getS()) + "} + ";
            }

        }
        formel += "{";

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (i == products.size() - 1) {
                formel += "\\textcolor{Green}{"
                        + formatter.format(product.getH())
                        + "} \\cdot \\textcolor{Blue}{"
                        + formatter.format(product.getD())
                        + "} \\cdot (1 - \\textcolor{RubineRed}{"
                        + formatter.format(product.getRoh()) + "})";
            } else {
                formel += "\\textcolor{Green}{"
                        + formatter.format(product.getH())
                        + "} \\cdot \\textcolor{Blue}{"
                        + formatter.format(product.getD())
                        + "} \\cdot (1 - \\textcolor{RubineRed}{"
                        + formatter.format(product.getRoh()) + "}) + ";
            }
        }

        formel += "}}";
        return formel;
    }

    private String getMinimalenProduktionszyklusFormel() {
        String formel = "\\frac{\\sum_{k=1}^{K} {\\tau}_k}{1 - \\sum_{k=1}^{K} {\\textcolor{RubineRed}{{\\rho}_k}}} \\leq T";
        return formel;
    }

    private String getMinimalenProduktionszyklusFormel(List<Product> products) {
        String formel = "T_{min} = \\frac{";
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (i == products.size() - 1) {
                formel += formatter.format(product.getTau()) + "}";
            } else {
                formel += formatter.format(product.getTau()) + " + ";
            }
        }
        formel += "{1 - (";
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (i == products.size() - 1) {
                formel += "\\textcolor{RubineRed}{"
                        + formatter.format(product.getRoh()) + "})}";
            } else {
                formel += " \\textcolor{RubineRed}{"
                        + formatter.format(product.getRoh()) + "} + ";
            }
        }
        return formel;
    }

    private String getLosgroesseMehrproduktFormel() {
        String formel = "\\textcolor{OliveGreen}{q_k} = \\textcolor{Blue}{D_k} \\cdot \\textcolor{Dandelion}{T_{opt}}";
        return formel;
    }

    private String getLosgroesseMehrproduktFormel(double d, double topt) {
        String formel = "\\textcolor{OliveGreen}{q_k} = \\textcolor{Blue}{"
                + formatter.format(d) + "} \\cdot \\textcolor{Dandelion}{"
                + formatter.format(topt) + "}";
        return formel;
    }

    @FXML
    public void handleZoomIn() {
        fontsize++;

        // regenerate LaTeX image
        showLatex();

        // zoomTestSouthBar();
    }

    @FXML
    public void handleZoomOut() {
        fontsize--;

        // regenerate LaTeX image
        showLatex();

        // zoomTestSouthBar();

    }

    /**
     * Sets the font size according of zoom factor on a touch based zoom event
     * 
     * @param event
     */
    public void handleZoom(ZoomEvent event) {

        Double zoomFactorDouble = event.getZoomFactor();
        Integer zoomFactorInteger = zoomFactorDouble.intValue();

        if (zoomFactorInteger >= 1) { // zoom in
            fontsize += zoomFactorInteger;
        } else { // zoom out
            fontsize -= 1;
        }

        // Debugging Printouts
        // System.out.println(zoomFactorDouble);
        // System.out.println(zoomFactorInteger);

        // stop further propagation of the event
        event.consume();
    }

    /**
     * Redraws the image with the LaTeX code based on the new fontsize
     * 
     * @param event
     */
    public void handleZoomFinished(ZoomEvent event) {

        // regenerate LaTeX image
        showLatex();

        // stop further propagation of the event
        event.consume();

        // zoomTestSouthBar();
    }

    /**
     * Experimental Zoom Method !!!WARNING - does not do the zoom properly!!!
     * 
     * @param content
     */
    public void scalableZoom(Node content) {

        double scaleValue = 4.0;
        Scale scaleTransform = new Scale(scaleValue, scaleValue, 0, 0);
        content.getTransforms().add(scaleTransform);

    }

    /**
     * Test Zoom for the south bar
     */
    public void zoomTestSouthBar() {
        buttonPlus.setFont(new Font(fontsize));
        buttonMinus.setFont(new Font(fontsize));
        label.setFont(new Font(fontsize));

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
