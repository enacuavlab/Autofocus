package ellipsoide;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import filtre.VecteurFiltrable;

public class AffichAccel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1632196092075420985L;

	private Sphere sp;
	private JProgressBar progressBar;
	private JPanel panelInst;
	private JTextArea inst;
	private JLabel labelPhoto;
	
	
	public AffichAccel(Sphere s){
		this.setPreferredSize(new Dimension(1000,600));
		this.setLayout(null);
		sp=s;
		progressBar=new JProgressBar(0,100);
		progressBar.setBounds(240, 400, 300, 25);
		this.add(progressBar);
		sp.getAffichage().setBounds(0, 0, 800, 400);
		this.add(sp.getAffichage());
		panelInst=new JPanel();
		panelInst.setLayout(null);
		panelInst.setBounds(850, 50, 320, 420);
		this.add(panelInst);
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
	
	public Sphere getSphere(){
		return sp;
	}


	public void update(final double radius,
			final VecteurFiltrable<Double> newcenter,
			final VecteurFiltrable<Double> v ,
			final VecteurFiltrable<Double> vcourant, 
			final int nbCorrectOK){
		sp.update(radius, newcenter, v, vcourant);
		setValueProgressBar(nbCorrectOK);
	}
	
	public void changedStates(){
		setValueProgressBar(0);
		inst.setText("Maintain your drone in a stable position not yet explored");	
	}
	
	public JPanel getLabel(){
		return panelInst;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
	}
	
}
