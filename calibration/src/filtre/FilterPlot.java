package filtre;

import common.TypeCalibration;
import ihm.GUIHelper;
import ihm.Plotter;

public class FilterPlot extends Filter{

	private Plotter plot;
	
	public FilterPlot(int windowSize, TypeCalibration type) {
		super(windowSize,type);
		plot = new Plotter();
	}
	
	public FilterPlot(Plotter plot, int windowSize, TypeCalibration type) {
		super(windowSize,type);
		this.plot = plot;
	}
	
	public FilterPlot(Plotter plot) {
		this(10,TypeCalibration.MAGNETOMETER);
		this.plot = plot;
	}
	
	@Override
	public void add(VecteurFiltrable<Double> v) {
		super.add(v);
		plot.add(v);
	}
	
	public static void main(String args[]) {
		Plotter plot = new Plotter();
		FilterPlot f = new FilterPlot(plot);
		EmulData e = new EmulData(f);
		Test t = new Test(e);
		t.run();
		GUIHelper.showOnFrame(plot, "test");
	}
	
}
