package filtre;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
public class Filter<E> {

	private Collection<DescriptiveStatistics> variables = null;
	private int windowSize;
	
	public Filter(int windowSize){
		this.windowSize=windowSize;
	}
	
	public Filter() {
		this.windowSize = 40;
	}

	
	public void add(VecteurFiltrable<E> v){
		Collection<E> toAdd = v.setArray();
		if (variables != null) {}	
	}
}

	