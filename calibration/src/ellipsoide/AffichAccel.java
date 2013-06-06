package ellipsoide;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import filtre.VecteurFiltrable;

public class AffichAccel {
	private Sphere sp;
	private JProgressBar progressBar;
	private JPanel panelInst;
	private JTextArea inst;
	private JLabel labelPhoto;
	
	
	public AffichAccel(){
		sp=new Sphere(20,10);
		progressBar=new JProgressBar(0,100);
		panelInst=new JPanel();
		panelInst.setLayout(null);
		panelInst.setBounds(0, 0, 320, 420);
		inst = new JTextArea();
		inst.setEditable(false);
		inst.setBounds(20,20,280,280);
		inst.setLineWrap(true);
		
		panelInst.add(inst);
		labelPhoto = new JLabel();
		labelPhoto.setBounds(20, 20, 280, 100);
		panelInst.add(labelPhoto);
		
		
	
	}
	
	
	public void setInstruction(String text, String urlPhoto){
		inst.setText(text);
		labelPhoto.setIcon((Icon) new ImageIcon(urlPhoto));			
	}
	
	public void setValueProgressBar(int value){
		progressBar.setValue(value);
	}
	
	public JProgressBar getProgressBar(){
		return progressBar;
	}


	public void update(double radius,VecteurFiltrable<Double> newcenter,VecteurFiltrable<Double> v ,VecteurFiltrable<Double> vcourant, int nbCorrectOK){
		sp.update(radius, newcenter, v, vcourant);
		setValueProgressBar(nbCorrectOK);
	}
	
	public void changedStates(){
		setValueProgressBar(0);
		inst.setText("Maintenez votre drone dans une position stable non encore exploré");	
	}
	public JPanel getLabel(){
		return panelInst;
	}
	
}
