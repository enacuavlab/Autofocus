package filtre;

import java.util.Iterator;

public class Filter<E extends Filtrable> {

	private SlidingWindow<E> values = new SlidingWindow<E>(40);

	private float tolerance = 2;
	private float md;
	private float diff;
	private float noiseThreshold = 1000;
	
	private float mediumDistance(E element) {
		float res = 0;
		for (E i : values) {
			if (!i.equals(element)) {
				res = res + i.away(element);
			}
		}
		return (res / values.size());
	}

	private float mediumDistance() {
		float res = 0;
		for (E i : values) {
			res = res + this.mediumDistance(i);
		}
		res = res / (values.size());
		return res;
	}

	private float difference() {
		float med = this.mediumDistance();
		float res = 0;
		for (E i : values) {
			for (E j : values) {
				if (!i.equals(j)) {
					res = (i.away(j) - med) * (i.away(j) - med);
				}
			}
		}
		res = (float) Math.sqrt(res);
		return res;
	}

	public void add(E element) {
		values.add(element);
		md = mediumDistance();
		diff = difference();
		float mde;
		int i = 0;
		for (E elem : values) {
			mde = mediumDistance(elem);
			i++;
			if (mde > (md + diff / tolerance) || mde < (md - diff / tolerance)) {
				if (i<values.getCapacity()/2) {element.setFalse();}
			} else
				if (i<values.getCapacity()/2) {element.setTrue();}
		}
	}
}
