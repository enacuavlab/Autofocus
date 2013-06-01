package ihm;

import java.awt.BorderLayout;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;

import filtre.Test;
import filtre.EmulData;

public class PlotterV3 {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout L = new BorderLayout();
        f.setLayout(L);
        Plotter plot = new Plotter();
        L.addLayoutComponent(plot,BorderLayout.CENTER);
        f.setSize(250,250);
        f.setVisible(true);
        
        FilterPlot p = new FilterPlot(plot,40,common.TypeCalibration.MAGNETOMETER);
        EmulData e = new EmulData(p);
        Test t = new Test(e);
        t.run();
        System.out.println(e);
    }
    
}