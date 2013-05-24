package filtre;

import java.util.Iterator;

public class Filter<E extends Filtrable> {

	private SlidingWindow<E> values = new SlidingWindow<E>(4);
	
	private float mediumDistance(E element) {
		float res = 0;
		for (E i : values) {
			if (!i.equals(element)) {
			res = res + i.away(element);
			}
		}
		return (res/values.size());
	}
	
	private float mediumDistance() {
		float res = 0;
		for (E i : values) {
			res = res + this.mediumDistance(i);
		}
		res = res / (values.size());
		return res;
	}

	private float difference(){
		float med = this.mediumDistance();
		float res = 0;
		for(E i : values){
			for(E j : values){
				if (!i.equals(j)) {
				res = (i.away(j) - med)*(i.away(j) - med);
				}
			}
		}
		res = (float) Math.sqrt(res);
		return res;
	}

	public void add(E element) {
		values.add(element);
		if (mediumDistance(element) > (mediumDistance() + difference()/2) ){
			element.setFalse();
		}
		else element.setTrue();
	}

}
