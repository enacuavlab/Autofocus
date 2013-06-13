package ihm;

public class TestScreen {
	int pointX;
	int pointY;
	
	public static void main(String[] args){
		ScreenHelper screen = new ScreenHelper();
		System.out.println(" X : " + screen.getWidth() + " Y : " + screen.getHeight());
		System.out.println(" essaiX : " + screen.convertX(1600) + " essaiY : " + screen.convertY(800));
	}

}
