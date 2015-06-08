package de.oth.smplsp.util;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.renderer.category.GanttRenderer;

public class MyRenderer extends GanttRenderer {

    private static final int PASS = 2; // assumes two passes
    private final List<Color> clut = new ArrayList<Color>();
    private final TaskSeriesCollection model;
    private int row;
    private int col;
    private int index;

    public MyRenderer(TaskSeriesCollection model) {
	this.model = model;
	clut.add(0, new Color(50, 50, 50));
	clut.add(1, new Color(150, 150, 150));
	clut.add(2, new Color(50, 50, 50));
	clut.add(3, new Color(150, 150, 150));
    }

    @Override
    public Paint getItemPaint(int row, int col) {
	if (this.row != row || this.col != col) {
	    this.row = row;
	    this.col = col;
	    index = 0;
	}
	int clutIndex = index++ / PASS;
	return clut.get(clutIndex);
    }

}