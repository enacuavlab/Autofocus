package ihm;



import filtre.Filter;
import filtre.VecteurFiltrable;

public class FilterPlot extends Filter{
	
	Plotter plot;
	
	public FilterPlot(Plotter plot, int windowSize, common.TypeCalibration t){
		super(windowSize,t);
		this.plot = plot;
	}
	
	@Override
	public void add(VecteurFiltrable<Double> v){
		super.add(v);
		plot.add(v);
	}
	
}
