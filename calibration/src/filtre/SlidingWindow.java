package filtre;

import java.util.concurrent.ArrayBlockingQueue;

public class SlidingWindow<E> extends ArrayBlockingQueue<E> {
	
	public SlidingWindow(int capacity){
		super(capacity);
	}
	
	/**add the element at the end of the queue
	 * return always true
	 */
	
	
	public boolean add(E element){
		if (!super.offer(element)){
			super.poll();
			super.offer(element);
		}
		return true;
	}
	
	@Override
	public <T> T[] toArray(T[] a) { 
		return super.toArray(a);
	}
	/**
	 * Added by default to allow serializable 
	 */
	private static final long serialVersionUID = 1L;

	
}
