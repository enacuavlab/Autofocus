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
	fenetre.setSize(240,240);
	fenetre.setText("Pour Canvas");
	fenetre.setLayout(new BorderLayout(0, 0));
	}
	
	public void drawPoint(int x, int y){
		gc1.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		gc1.drawOval(x,y,20,20);
		gc1.dispose();
	}
	
	public void execute() {
		fenetre.open();
		canvas = new Canvas(fenetre, SWT.NONE); 
		canvas.setSize(200,200);
		canvas.setLayoutData(BorderLayout.CENTER);
		gc1 = new GC(canvas);
		
		gc1.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		gc1.drawOval(100,100,20,20);
		gc1.dispose();
		
		while (!fenetre.isDisposed()) {
			if (!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
	
}
