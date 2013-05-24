package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Plotter {

	public static void execute() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setSize(420, 420);

		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setSize(200, 200);
		canvas.setLocation(10, 10);
		shell.pack();
		shell.open();

		GC gc = new GC(canvas);
		gc.drawText("Bonjour", 20, 20);
		gc.drawLine(10, 10, 10, 100);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.drawOval(60, 60, 60, 60);
		gc.dispose();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
