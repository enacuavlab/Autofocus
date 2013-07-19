package ihm;

import java.awt.Toolkit;

/**
 * Give the resolution of the screen
 * @author Guillaume
 * @version 1.0
 */
public class ScreenHelper {
	/** Creates new ScreenHelper */
	private int height;
	private int width;
	
	public ScreenHelper(){
		height = getHeight();
		width = getWidth();
	}
	public  int convertX(int aConvertir){
		return (int)(((double)width / 1366) * aConvertir); 
	}

	public int convertY(int aConvertir){
		return (int)(((double)height/768) * aConvertir) ;
	}
	
	public int getHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	public int getWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
}
