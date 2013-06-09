package tests;

import obsolete.Plotter;
import common.TypeCalibration;
import filtre.Filter;
import filtre.VecteurFiltrable;

public class FilterPlot extends Filter {

	private Plotter plot;

	/**
	 * Creates a filter who plots the vector in a two dimensional window
	 * (simple orthogonal projection on xy)
	 * @param windowSize
	 * @param type
	 */
	public FilterPlot(int windowSize, TypeCalibration type) {
		super(windowSize, type);
		plot = new Plotter();
	}

	/**
	 * Creates a filter who uses the plot given as an argument to plot in
	 * a two dimensional graph (simple orthogonal projection on xy)
	 * @param plot
	 * @param windowSize
	 * @param type
	 */
	public FilterPlot(Plotter plot, int windowSize, TypeCalibration type) {
		super(windowSize, type);
		this.plot = plot;
	}

	/**
	 * Creates a filter with a default window size and type (magnetometer)
	 * use for test only
	 * @param plot
	 */
	@Deprecated
	public FilterPlot(Plotter plot) {
		this(10, TypeCalibration.MAGNETOMETER);
		this.plot = plot;
	}

	/**
	 * Add the vector given in arguement to the filter and the plotting window
	 * @param v vector to add
	 */
	@Override
	public void add(VecteurFiltrable<Double> v) {
		super.add(v);
		plot.add(v);
	}

	/**
	 * The test function of the class, uses the default class test to plot few
	 * points and test it's working
	 * @param args
	 */
	public static void main(String args[]) {
		Plotter plot = new Plotter();
		FilterPlot f = new FilterPlot(plot);
		EmulData e = new EmulData(f);
		Test t = new Test(e);
		t.run();
		GUIHelper.showOnFrame(plot, "test");
	}

}
