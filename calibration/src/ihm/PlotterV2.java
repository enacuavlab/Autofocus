package ihm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;

import filtre.Test;
import data.Vecteur;

public class PlotterV2 extends Canvas {
	
	public PlotterV2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static LinkedList<Point> formes;

	//private static Point point;
	/*private static void setPoint(int x, int y) {
		point.x = x;
		point.y = y;
	}*/
	
	
	public static void main(String args[]) {
		//Initialisation des attributs
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setText("Canvas Example");
		shell.setLayout(new FillLayout());
		//point= new Point(0,0);
		final Canvas canvas = new Canvas(shell, SWT.NONE);
		final GC gc= new GC(canvas);
		//ajout d'un evenement sur le canvas pour rafraichir l'affichage
		/*canvas.addListener(SWT.Paint,new Listener() {
			public void handleEvent(Event e) {
				System.out.println("Redraw ok");
				e.gc.drawLine(point.x, point.y, point.x+50, point.y+50);
			}
		});*/
		
		class dessin_canvas implements PaintListener {
	 
	          public void paintControl(PaintEvent event) {
	        	  System.out.println("PaintEvent");	
	        	  GC mongc=event.gc;
	                if (!points.isEmpty()){
	                	//creation des points a afficher
	            		//Iterable<Vecteur> db = (new Test()).test();
	            		//ajout des points sur le canvas
	            		//int i = 0;
	            		//for (Vecteur e : db) {
	            			//i= i+ 20;
	            			//System.out.println("Boucle");
	                	
	            			//points.add(new Point(i,(int)db.iterator().next().getX()))
	                		for (Point e : points) {
	                			
		                		mongc.drawLine(e.x, e.y, e.x+50, e.y+50);
	                		}
	            			
	            			mongc.dispose();
	            	} else System.out.println("Liste de points vide");
	          }
		}
		canvas.addPaintListener(new dessin_canvas());
		//creation des points a afficher
		Iterable<Vecteur> db = (new Test()).test();
		//ajout des points sur le canvas
		int i = 0;
		shell.open();
		for (Vecteur e : db) {
			i= i+ 20;
			System.out.println("Boucle");
			points.add(new Point(i,(int)db.iterator().next().getX()));
			//setPoint(i,(int)db.iterator().next().getX());
			canvas.redraw();
			canvas.update();
		}
		gc.dispose();
		
		//ouverture et affichage de la fenetre
		//canvas.redraw();
		//canvas.update();
		
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
