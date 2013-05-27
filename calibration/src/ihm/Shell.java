package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.color.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Shell extends JFrame {
		private JButton btn_accelero,btn_magneto,btn_gyro;
		private JLabel titre;
		private Plotter plot;
		
		public Shell(Plotter plot){
			super();
			this.plot=plot;
			initialise();//On initialise notre fenêtre
			
		}
	 
		private void initialise(){
			//Fenetre
			setTitle("Autofocus"); //On donne un titre à l'application
			setSize(1000,600); //On donne une taille à notre fenêtre
			setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
			setResizable(true); //On autorise le redimensionnement de la fenêtre
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
			setLayout(new BorderLayout());
			//Panel titre
			JPanel panel_titre= new JPanel();
			panel_titre.setBackground(Color.blue);
			getContentPane().add(panel_titre,"North");
			titre= new JLabel("Veuillez remplir les champs");
			panel_titre.add(titre);
			
			//Panel options
			JPanel panel_options=new JPanel();
			panel_options.setBackground(Color.red);
			getContentPane().add(panel_options,"West");
			GridLayout gd_options=new GridLayout(8,1);
			gd_options.setVgap(15);
			panel_options.setLayout(gd_options);
			
			//Button
			btn_accelero=new JButton("Accelerometers");
			btn_magneto=new JButton("Magnetometers");
			btn_gyro=new JButton("Gyrometers");
			
			//Button insertion
			JLabel titre_options= new JLabel("Type de calibration");
			panel_options.add(titre_options);
			panel_options.add(new JLabel());
			panel_options.add(btn_accelero);
			panel_options.add(new JLabel());
			panel_options.add(btn_magneto);
			panel_options.add(new JLabel());
			panel_options.add(btn_gyro);
			panel_options.add(new JLabel());
			
			//Center Panel
			getContentPane().add(plot,"Center");
			
		}
}


