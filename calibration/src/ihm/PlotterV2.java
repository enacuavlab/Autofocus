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




public class PlotterV2 {
	private Display display;
	private Shell shell;
	private Canvas canvas;
	private Point point;
	
	
	public Canvas getCanvas(){
		return this.canvas;
	}
	
	public PlotterV2(){
		display=new Display();
		shell=new Shell(display,SWT.SHELL_TRIM);
		shell.setText("Canvas Example");
	    shell.setLayout(new FillLayout());
	    point=new Point(0,0);
	    
	    Canvas canvas = new Canvas(shell, SWT.NONE);
	    
	    
	    canvas.addPaintListener(new PaintListener() {
	      public void paintControl(PaintEvent e) {
	        e.gc.drawPoint(point.x,point.y);
	      }
	    });
	}
	
	public void setPoint(int x, int y){
		point.x=x;
		point.y=y;
	}
	public Shell getShell(){
		return shell;
	}
	
	public void execute(){
		shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
	 }
	}
	

