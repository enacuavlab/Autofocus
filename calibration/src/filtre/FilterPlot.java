package filtre;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import common.TypeCalibration;
import ihm.GUIHelper;
import ihm.Plotter;

public class FilterPlot extends Filter {

	private Plotter plot;

	public FilterPlot(int windowSize, TypeCalibration type) {
		super(windowSize, type);
		plot = new Plotter();
	}

	public FilterPlot(Plotter plot, int windowSize, TypeCalibration type) {
		super(windowSize, type);
		this.plot = plot;
	}

	public FilterPlot(Plotter plot) {
		this(10, TypeCalibration.MAGNETOMETER);
		this.plot = plot;
	}


	@Override
	public void add(VecteurFiltrable<Double> v) {
		Collection<Double> toAdd = v.setArray();
		Iterator<Double> a = toAdd.iterator();
		if (variables != null) {
			// System.out.println("variables non null");
			Iterator<DescriptiveStatistics> d = variables.iterator();
			while (a.hasNext() && d.hasNext()) {
				d.next().addValue(a.next());
				// System.out.println("ajoute les donnees");
			}
			window.add(v);
			plot.add(v);
			for (VecteurFiltrable<Double> vec : window) {
				if (update(vec))
					plot.add(vec);
			}
			// System.out.println("ajout d'un vecteur");
		} else {
			// System.out.println("cree variabless");
			this.variables = new ArrayList<DescriptiveStatistics>(toAdd.size());
			for (int i = 0; i < toAdd.size(); i++) {
				// System.out.println("cree variable");
				variables.add(new DescriptiveStatistics(windowSize));
			}
			this.add(v);
		}
		/*
		 * if (v.isCorrect()) { ellipse.fitEllipsoid(v);
		 * plot.add(abs(ellipse.getXrad()),abs(ellipse.getYrad())
		 * ,ellipse.getCenter()); ellipse.printLog(); plot.printMinMax();
		 */
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
