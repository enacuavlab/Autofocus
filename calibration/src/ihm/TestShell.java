package ihm;

import javax.swing.SwingUtilities;
import filtre.EmulData;
import filtre.Plotter;
import filtre.Test;

public class TestShell {
	
	public static void main(String[] args){
		final Plotter plot= new Plotter();
		//FilterPlot fplot= new FilterPlot(plot);
		//EmulData edata=new EmulData(fplot);
		//final Test test =new Test(edata);
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				Shell shell = new Shell(plot);
				shell.setVisible(true);//On la rend visible
				//test.run();
			}
		});
	}
}


