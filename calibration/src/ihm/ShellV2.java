package ihm;

import iddrone.IvyIdListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import fr.dgac.ivy.IvyException;


public class ShellV2 extends JFrame {
		private JButton btn_accelero,btn_magneto,btn_gyro;
		private JLabel titre;
		private Plotter plot;
		
		public ShellV2(Plotter plot){
			super();
			this.plot=plot;
			initialise();//On initialise notre fenêtre
			
		}
	 
		private void initialise(){
			//Fenetre
			setTitle("Autofocus"); //On donne un titre à l'application
			setSize(1600,800); //On donne une taille à notre fenêtre
			setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
			setResizable(true); //On autorise le redimensionnement de la fenêtre
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
			getContentPane().setLayout(new BorderLayout());
			//Panel titre
			JPanel panel_titre= new JPanel();
			panel_titre.setBackground(Color.blue);
			panel_titre.setPreferredSize(new Dimension(1600,100));
			getContentPane().add(panel_titre,"North");
			titre= new JLabel("<html><br>Veuillez remplir les champs</html>");
			titre.setFont(new Font("Calibri",Font.BOLD,28));
			panel_titre.add(titre);

			//Panel options
			JPanel panel_options=new JPanel();
			panel_options.setBackground(Color.red);
			getContentPane().add(panel_options,"West");
			GridLayout gd_options=new GridLayout(5,1);
			gd_options.setVgap(30);
			panel_options.setLayout(gd_options);
			
			
			//Button
			btn_accelero=new JButton("Accelerometers");
			btn_accelero.setEnabled(true);
			btn_accelero.addActionListener(new ActionListener(){
				  public void actionPerformed(ActionEvent event){
					  btn_magneto.setEnabled(false);
					  btn_gyro.setEnabled(false);
					  titre.setText("<html><br>Calibration des Accéléromètre</html>");
				  }
				});
			btn_magneto=new JButton("Magnetometers");
			btn_magneto.setEnabled(true);
			btn_magneto.addActionListener(new ActionListener(){
				  public void actionPerformed(ActionEvent event){
					  btn_accelero.setEnabled(false);
					  btn_gyro.setEnabled(false);
					  titre.setText("<html><br>Calibration des Magnétomètres</html>");
				  }
				});
			btn_gyro=new JButton("Gyrometers");
			btn_gyro.setEnabled(true);
			btn_gyro.addActionListener(new ActionListener(){
				  public void actionPerformed(ActionEvent event){
					  btn_accelero.setEnabled(false);
					  btn_magneto.setEnabled(false);
					  titre.setText("<html><br>Calibration des Gyromètres</html>");
				  }
				});
			
			//Button insertion
			Border border_type=BorderFactory.createTitledBorder("Type de calibration");
			panel_options.setBorder(border_type);
			//JLabel titre_options= new JLabel("Type decalibration");
			//panel_options.add(titre_options);
			//panel_options.add(new JLabel());
			panel_options.add(btn_accelero);
			//panel_options.add(new JLabel());
			panel_options.add(btn_magneto);
			//panel_options.add(new JLabel());
			panel_options.add(btn_gyro);
			
			
			JPanel panel_center = new JPanel();
			getContentPane().add(panel_center, BorderLayout.CENTER);
			panel_center.setLayout(new GridLayout(3,1));
			JPanel panel_up_center= new JPanel();
			JPanel panel_center_center= new JPanel();
			JPanel panel_down_center= new JPanel();
			
			panel_center.add(panel_up_center);
			panel_up_center.setLayout(null);
			
			JComboBox combo_id = new JComboBox();
			combo_id.setBounds(569, 83, 76, 24);
			panel_up_center.add(combo_id);
			
			JLabel lblChoissisezLidDe = new JLabel("Choissisez l'id de votre drone");
			lblChoissisezLidDe.setBounds(345, 83, 222, 24);
			panel_up_center.add(lblChoissisezLidDe);panel_center.add(panel_center_center);panel_center.add(panel_down_center);
			
			
			
			
			
			try {
				final IvyIdListener ivyid= new IvyIdListener();
				ArrayList<Integer> l=(ArrayList<Integer>) ivyid.getList();
				if (!l.isEmpty()){
					for (Integer i : ivyid.getList()){
						combo_id.addItem(i.toString());
					}
				}
			}catch (IvyException e){
				e.printStackTrace();
			}
			combo_id.addItem("Test");
			Border border_mod=BorderFactory.createRaisedBevelBorder();
			
			
			
			
			
			
			
		}
}