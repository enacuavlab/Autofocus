package filtre;

import java.util.Random;

/**This class aims to simulate the IMU in order to test the filtering*/
class AleatGen {

	private int value = 100;
	private int noise = 20;
	private Random rd = new Random();
	
	/**generate generator able to give number around value impacted with a noise of noise*/
	protected AleatGen(int value, int noise) {
		this.value = value;
		this.noise = noise;
	}
	
	/**gives the next noisy value*/
	protected int next() {
		return value + rd.nextInt(noise);
	}
	
	protected void store() {
		(EmulData.CreateStorage()).store(this.next());
	}
}
