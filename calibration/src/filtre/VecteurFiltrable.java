package filtre;

import java.lang.reflect.Array;

public interface VecteurFiltrable<E> {
	
	/**transforms the vector in an array*/
	public Array toArray();
	
	/**return the boolean stating if the object is valid or not*/
	public boolean isCorrect();
	
	/**set the boolean value of correctness to true*/
	public void setTrue();
	
	/**set the boolean value of correctness to false*/
	public void setFalse();
	
	/**return the value of the object with its correctness*/
	public String toString();
	

}
