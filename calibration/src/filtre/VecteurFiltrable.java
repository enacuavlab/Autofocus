package filtre;

import java.util.Collection;

public interface VecteurFiltrable<E> {
	
	/**getter for coordinates*/
	public double getX();
	
	/**getter for coordinates*/
	public double getY();
	
	/**getter for coordinates*/
	public double getZ();
	
	/**transforms the vector in an array*/
	public Collection<E> setArray();
	
	/**return the boolean stating if the object is valid or not*/
	public boolean isCorrect();
	
	/**set the boolean value of correctness to true*/
	public void setTrue();
	
	/**set the boolean value of correctness to false*/
	public void setFalse();
	
	/**return the value of the object with its correctness*/
	public String toString();
	

}
