package filtre;

public class Vecteur implements filtrable<Integer> {

	private int value;
	private boolean correct;

	public float away(Vecteur test) {
		return ((value - test.getValue()) > 0 ? (value - test.getValue()) : (test.getValue() - value));
	}

	protected Vecteur(int x) {
		value = x;
		correct = false;
	}

	public void set(Integer x) {
		value = x;
		correct = false;
	}

	protected Integer getValue() {
		return value;
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
	public float away(filtrable<Integer> test) {
		// TODO Auto-generated method stub
		return 0;
	}

}
