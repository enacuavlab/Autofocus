package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Plotter {
	  public static void main(String[] args) {
		    Display display = new Display();
		    Shell shell = new Shell(display);
		    shell.setText("Canvas Example");
		    shell.setLayout(new FillLayout());

		    Canvas canvas = new Canvas(shell, SWT.NONE);

		    canvas.addPaintListener(new PaintListener() {
		      public void paintControl(PaintEvent e) {
		        e.gc.drawRoundRectangle(10, 10, 200, 200, 30, 30);
		      }
		    });
	    
		    canvas.redraw();
	
		    shell.open();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();
		  }
}
