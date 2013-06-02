package testData;

public class TestSender {
	
	public static void main(String args[]) {
		try {
			new Sender("C:\\Users\\Alinoé\\Desktop\\13_05_29__10_15_23.data").start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
