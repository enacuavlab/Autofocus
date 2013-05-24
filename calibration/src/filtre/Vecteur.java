package filtre;

public class Vecteur implements Filtrable<Integer> {

	private int value;
	private boolean correct;

	public float away(Filtrable<Integer> test) {
		float res = (value - test.getObject()) > 0 ? (value - test.getObject()) : (test.getObject() - value);
		return (res);
	}

	protected Vecteur(int x) {
		value = x;
		correct = false;
	}

	public Integer getObject() {
		return value;
	}
	
	public void set(Integer x) {
		value = x;
		correct = false;
	}


	public boolean isCorrect() {
		return correct;
	}

	public void setTrue() {
		correct = true;
	}

	public void setFalse() {
		correct = false;
	}

	public String toString() {
		return ("" + value + " " + correct);
	}

	@Override
	public boolean equals(Filtrable<Integer> test) {
		return (this.away(test)==0);
	}

}
