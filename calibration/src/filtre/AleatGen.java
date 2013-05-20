package filtre;

import java.util.Random;

/** This class aims to simulate the IMU in order to test the filtering */
class AleatGen {

	private int value = 100;
	private int noise = 20;
	private Random rd = new Random();

	/**
	 * generate generator able to give number around value impacted with a noise
	 * of noise
	 */
	protected AleatGen(int value, int noise) {
		this.value = value;
		this.noise = noise;
	}

	/** gives the next noisy value */
	protected int next() {
		if (rd.nextBoolean()) {
			return value + rd.nextInt(noise);
		} else
			return value - rd.nextInt(noise);
	}

	/**orders the value to be stored in the storage class*/
	protected void store() {
		(EmulData.CreateStorage()).store(this.next());
	}
}
