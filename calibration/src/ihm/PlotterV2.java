package ihm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import filtre.Test;
import filtre.Vecteur;

public class PlotterV2 {
	private static Display display;
	private static Shell shell;
	private static Canvas canvas;
	private static Point point;

	private static void setPoint(int x, int y) {
		point.x = x;
		point.y = y;
	}
	
	public static void main(String args[]) {
		//Initialisation des attributs
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setText("Canvas Example");
		shell.setLayout(new FillLayout());
		Canvas canvas = new Canvas(shell, SWT.NONE);
		GC gc= new GC(canvas);
		point = new Point(0,0); //sera modifier juste apres
		//ajout d'un evenement sur le canvas pour rafraichir l'affichage
		canvas.addListener(SWT.Paint,new Listener() {
			public void handleEvent(Event e) {
				System.out.println("Redraw ok");
				e.gc.drawLine(point.x, point.y, point.x+50, point.y+50);
			}
		});
		
		//creation des points a afficher
		Iterable<Vecteur> db = (new Test()).test();
		//ajout des points sur le canvas
		int i = 0;
		shell.open();
		for (Vecteur e : db) {
			i= i+ 20;
			System.out.println("Boucle");
			setPoint(i, e.getObject());
			canvas.redraw();
			canvas.update();
		}
		gc.dispose();
		
		//ouverture et affichage de la fenetre
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}


	public void trace(Iterable<Integer> toTrace) {

	}
}
