package filtre;

public interface filtrable<E> {
	
	/**gives the distance between to filtering object*/
	public float away(filtrable<E> test);
	
	/**return the boolean stating if the object is valid or not*/
	public abstract boolean isCorrect();
	
	/**set the boolean value of correctness to true*/
	public abstract void setTrue();
	
	/**set the boolean value of correctness to false*/
	public abstract void setFalse();
	
	/**return the value of the object with its correctness*/
	public abstract String toString();

}
