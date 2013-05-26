package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PlotterV3 {

	private static Display display;
	private static Shell shell;
	private static Canvas canvas;
	
	private static int i = 0;
	
	public static void increment() {
		i++;
		canvas.redraw();
		canvas.update();
	}
	
	public PlotterV3() {

		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setText("Canvas Example");
		shell.setLayout(new FillLayout());
		canvas = new Canvas(shell, SWT.NONE);
		
		class dessin_canvas implements PaintListener {
	          public void paintControl(PaintEvent event) {
	        	  GC gc = event.gc;
	        	  System.out.println("PaintEvent");	
	        	  gc.drawString((new Integer(i)).toString(),i*10,i*10);
	          }
		}
		
		canvas.addPaintListener(new dessin_canvas());


	}
	
	public void execute() {
		shell.open();
		for(int i=0; i<4; i++){
			increment();
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
