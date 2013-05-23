package filtre;

public interface Filtrable<E> {
	
	/**gives the attached object*/
	public E getObject();
	
	/**gives the distance between to filtering object*/
	public float away(Filtrable<E> test);
	
	/**return the boolean stating if the object is valid or not*/
	public boolean isCorrect();
	
	/**set the boolean value of correctness to true*/
	public void setTrue();
	
	/**set the boolean value of correctness to false*/
	public void setFalse();
	
	/**return the value of the object with its correctness*/
	public String toString();
	
	/**return true if test equals the calling Filtrable<E>*/
	public boolean equals(Filtrable<E> test);

}
