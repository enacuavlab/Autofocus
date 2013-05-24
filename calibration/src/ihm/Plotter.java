package ihm;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.wb.swt.SWTResourceManager;
import swing2swt.layout.FlowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class Plotter {
	private Display display;
	private Shell fenetre;
	private Canvas canvas;
	GC gc1;
	public Plotter(){
		
	display= new Display();
	fenetre = new Shell(display, SWT.SHELL_TRIM);
	fenetre.setText("Pour Canvas");
	fenetre.setLayout(new BorderLayout(0, 0));
	canvas = new Canvas(fenetre, SWT.NONE); 
	canvas.setSize(200,200);
	canvas.setLayoutData(BorderLayout.CENTER);
	gc1 = new GC(canvas);
	}
	
	public void drawPoint(int x, int y){
		gc1.drawPoint(x,y);
	}
	public void execute() {
		fenetre.open(); 
		while (!fenetre.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
	
}
