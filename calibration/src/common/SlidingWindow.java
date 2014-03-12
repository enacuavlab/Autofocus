/**Package grouping all classes used to filter data*/
package common;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class implementing a sliding window, basically add a vector at the beginning
 * while throwing the first in the window
 * 
 * @author florent
 * 
 * @param <E>
 */
public class SlidingWindow<E> extends ArrayBlockingQueue<E> {

	public SlidingWindow(int capacity) {
		super(capacity);
	}

	/**
	 * add the element at the end of the queue return always true
	 */
	public boolean add(E element) {
		if (!super.offer(element)) {
			super.poll();
			super.offer(element);
		}
		return true;
	}

	/**
	 * @return the array containing the elements of the sphere
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return super.toArray(a);
	}

	/**
	 * Added by default to allow serializable
	 */
	private static final long serialVersionUID = 1L;

}
