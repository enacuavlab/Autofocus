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



public class Shell extends JFrame {
		private JButton btn_accelero,btn_magneto,btn_gyro;
		private JLabel titre;
		private Plotter plot;
		private JButton btnQuitter,btnStop;
		
		public Shell(Plotter plot){
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
			panel_center.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_north_center = new JPanel();
			panel_center.add(panel_north_center, BorderLayout.NORTH);
			panel_north_center.setPreferredSize(new Dimension(1600,100));
			GridBagLayout gbl_panel_north_center = new GridBagLayout();
			gbl_panel_north_center.columnWidths = new int[] {300, 300, 30};
			gbl_panel_north_center.rowHeights = new int[] {40, 40, 40};
			gbl_panel_north_center.columnWeights = new double[]{0.0, 0.0, 1.0};
			gbl_panel_north_center.rowWeights = new double[]{0.0, 0.0};
			panel_north_center.setLayout(gbl_panel_north_center);
			
			JLabel label_id= new JLabel("Choissisez l'id de votre drone");
			GridBagConstraints gbc_label_id = new GridBagConstraints();
			gbc_label_id.insets = new Insets(0, 0, 5, 5);
			gbc_label_id.anchor = GridBagConstraints.EAST;
			gbc_label_id.gridx = 1;
			gbc_label_id.gridy = 1;
			panel_north_center.add(label_id, gbc_label_id);
			
			
			final JComboBox combo_id=new JComboBox();
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.anchor = GridBagConstraints.WEST;
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 1;
			panel_north_center.add(combo_id, gbc_comboBox);
			
		
			JPanel panel_center_center = new JPanel();
			panel_center.add(panel_center_center, BorderLayout.CENTER);
			panel_center_center.setLayout(null);
			
			final JPanel panel_mod = new JPanel();
			Border border_mod=BorderFactory.createRaisedBevelBorder();
			panel_mod.setBackground(Color.ORANGE);
			panel_mod.setForeground(Color.BLACK);
			panel_mod.setBounds(282, 68, 508, 289);
			panel_mod.setBorder(border_mod);
			panel_center_center.add(panel_mod);
			panel_mod.setLayout(null);
			
			final JLabel label_mod = new JLabel();
			label_mod.setBounds(105, 45, 340, 42);
			panel_mod.add(label_mod);
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(105, 124, 284, 24);
			panel_mod.add(comboBox);
			panel_mod.setVisible(false);
			
			//Add drone id 
			combo_id.addItem(" ");
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
			combo_id.addItem("Test2");
			combo_id.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
				  if (combo_id.getSelectedItem().toString()==" "){
					  panel_mod.setVisible(false);
				  }
				  else {
					  label_mod.setText("Choissisez le mode du drone d'id " + combo_id.getSelectedItem().toString()+ " :");
					  panel_mod.setVisible(true);
				  }
				    }  
			});
		
			
			JPanel panel_south_center = new JPanel();
			panel_center.add(panel_south_center, BorderLayout.SOUTH);
			panel_south_center.setPreferredSize(new Dimension(1600,50));
			GridBagLayout gbl_panel_south_center = new GridBagLayout();
			gbl_panel_south_center.columnWidths = new int[] {300, 300, 300, 30, 100, 50, 100, 100};
			gbl_panel_south_center.rowHeights = new int[] {0, 25, 25};
			gbl_panel_south_center.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_panel_south_center.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_south_center.setLayout(gbl_panel_south_center);
			
			//Stop Button
			btnStop = new JButton("STOP");
			GridBagConstraints gbc_btnStop = new GridBagConstraints();
			gbc_btnStop.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnStop.insets = new Insets(0, 0, 0, 5);
			gbc_btnStop.gridx = 4;
			gbc_btnStop.gridy = 0;
			panel_south_center.add(btnStop, gbc_btnStop);
			btnStop.setVisible(false);
			
			//Quit Button
			btnQuitter = new JButton("Quitter");
			GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
			gbc_btnQuitter.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnQuitter.gridx = 6;
			gbc_btnQuitter.gridy = 0;
			panel_south_center.add(btnQuitter, gbc_btnQuitter);
			btnQuitter.setVisible(false);
			
			
			
	
			
		}
}