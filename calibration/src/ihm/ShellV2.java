package ihm;

import fr.dgac.ivy.IvyException;
import imu.GetConfigException;
import imu.IMU;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import rawmode.ExtractRawData;
import rawmode.IncorrectXmlException;

import common.StartUp;
import common.TypeCalibration;
/**
 * Graphic Interface of our application.
 *
 *@author SAAS Guillaume
 *@version 2.0
 */
public class ShellV2 extends JFrame {
	/**
	 * Options Button to choose the type of the calibration
	 */
    private JButton btnAccelero, btnMagneto, btnGyro;
    /**
     * Label title : title of our activity 
     */
    private JLabel title;
    /**
     * Button Quit Stop and Return in panels of calibration
     */
	private JButton btnQuit, btnStop,btnReturn;
	/**
	 * Drone's id
	 */
	private int id ;
	/**
	 * Drone's name and url of the drone's settings.xml
	 */
	private String name, url;
	/**
	 * Different panels of our Application : panelHome , panelGyro, panelMag -> background panels
	 * content contents all the background panels
	 * panelDessin, panelBar, panelInst to design calibration
	 */
	private JPanel panelHome, panelAccl, panelGyro, panelMag, content,
			panelDessin, panelBar, panelInst;
	/**
	 * listContent to get an access to the background panels
	 */
	private String[] listContent = { "HOME", "ACCL", "MAG", "GYRO" };
	/**
	 * CardLayout
	 */
	private CardLayout cl;
	/**
	 * To know the mode 
	 */
	private String mod;
	/**
	 * Button's actions
	 */
	private Action ac1, ac2, ac3;
	/**
	 * IMU
	 */
	private IMU imu;
	/**
	 * Result
	 */
	private Result result;
	
	/**
	 * Constructeur qui initialise la fenetre et met en place le cardLayout
	 */
	public ShellV2() {
		super();
		// Shell
		setTitle("Autofocus");
		setSize(1600, 800);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		cl = new CardLayout();
		content = new JPanel();
		content.setLayout(cl);
		panelHome = new JPanel();
		panelAccl = new JPanel();
		panelMag = new JPanel();
		panelGyro = new JPanel();
		panelHome.setLayout(new BorderLayout());
		panelAccl.setLayout(new BorderLayout());
		panelMag.setLayout(new BorderLayout());
		panelGyro.setLayout(new BorderLayout());
		content.add(panelHome, listContent[0]);
		content.add(panelAccl, listContent[1]);
		content.add(panelMag, listContent[2]);
		content.add(panelGyro, listContent[3]);
		getContentPane().add(content, BorderLayout.CENTER);
		addOptions();
		mod = "Home";
		initialise();// On initialise notre fenêtre

	}

	/**
	 * Fonction pour initialiser et réinitialiser l'accueil
	 */
	private void initialise() {
		// Pour test
		//btnAccelero.setEnabled(true);
		//btnMagneto.setEnabled(true);
		//IMU
		try {
			imu = new IMU();
		} catch (IvyException e) {
			JOptionPane.showMessageDialog(null, "Ivy Bus problem"
					+ name,"Error Bus ", JOptionPane.ERROR_MESSAGE);
		}
		// Active les boutons
		activateButton(mod);
		// Panel titre
		JPanel panelTitre = new JPanel();
		Border border_titre = BorderFactory.createBevelBorder(
				BevelBorder.LOWERED, Color.black, Color.white);
		panelTitre.setBackground(Color.LIGHT_GRAY);
		panelTitre.setBorder(border_titre);
		panelTitre.setPreferredSize(new Dimension(1600, 100));
		getContentPane().add(panelTitre, "North");
		title = new JLabel("<html><br>Veuillez remplir les champs</html>");
		title.setFont(new Font("Calibri", Font.BOLD, 28));
		panelTitre.add(title);

		// Panel qui va contenir la combobox pour le choix de l'id du drone
		JPanel panelNorth = new JPanel();
		panelHome.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setPreferredSize(new Dimension(1600, 100));
		GridBagLayout gblPanelNorth = new GridBagLayout();
		gblPanelNorth.columnWidths = new int[] { 300, 300, 30 };
		gblPanelNorth.rowHeights = new int[] { 40, 40, 40 };
		gblPanelNorth.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		gblPanelNorth.rowWeights = new double[] { 0.0, 0.0 };
		panelNorth.setLayout(gblPanelNorth);

		// Panel centre qui va contenir les panels mod et name
		JPanel panelCenter = new JPanel();
		panelHome.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(null);

		// Panel_name with a textfield and a label
		JPanel panelName = new JPanel();
		Border borderName = BorderFactory.createRaisedBevelBorder();
		panelName.setBorder(borderName);
		panelName.setBackground(new Color(202, 205, 236));
		panelName.setBounds(275, 38, 508, 99);
		panelCenter.add(panelName);
		panelName.setLayout(null);
		panelName.setVisible(false);

		JLabel labelName = new JLabel();
		labelName.setBounds(106, 12, 138, 60);
		panelName.add(labelName);

		// Panel_mod
		Border borderMod = BorderFactory.createRaisedBevelBorder();
		JPanel panelMod = new JPanel();
		panelMod.setBackground(new Color(202, 205, 236));
		panelMod.setForeground(Color.BLACK);
		panelMod.setBounds(275, 213, 508, 177);
		panelMod.setBorder(borderMod);
		panelCenter.add(panelMod);
		panelMod.setVisible(false);
		panelMod.setLayout(null);
		/*
		 * textField.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e){ name=textField.getText();
		 * addcombo_mod(panel_mod); } });
		 */

		addcombo_id(panelNorth, panelName, labelName, panelMod);

	}

	/**
	 * Function to add a Panel in order to choose the type of the calibration
	 */
	private void addOptions() {
		// Panel options
		JPanel panelOptions = new JPanel();
		panelOptions.setBackground(Color.gray);
		getContentPane().add(panelOptions, "West");
		GridLayout gdOptions = new GridLayout(5, 1);
		gdOptions.setVgap(30);
		panelOptions.setLayout(gdOptions);

		// Button
		// Accelerometers
		btnAccelero = new JButton("Accelerometers");
		btnAccelero.setEnabled(false);
		ac1 = new Action("Accl");

		// Magnetometers
		btnMagneto = new JButton("Magnetometers");
		btnMagneto.setEnabled(false);
		ac2 = new Action("Mag");

		// Gyrometers
		btnGyro = new JButton("Gyrometers");
		btnGyro.setEnabled(false);
		ac3 = new Action("Gyro");

		// Button insertion
		Border borderType = BorderFactory
				.createTitledBorder("Type de calibration");
		panelOptions.setBorder(borderType);
		// JLabel titreOptions= new JLabel("Type decalibration");
		// panelOptions.add(titre_options);
		// panelOptions.add(new JLabel());
		panelOptions.add(btnAccelero);
		// panelOptions.add(new JLabel());
		panelOptions.add(btnMagneto);
		// panelOptions.add(new JLabel());
		panelOptions.add(btnGyro);
	}

	private void activateButton(String mod) {
		if (mod.equals("Home")) {
			btnAccelero.addActionListener(ac1);
			btnMagneto.addActionListener(ac2);
			btnGyro.addActionListener(ac3);
		} else if (mod.equals("Accl")) {
			btnAccelero.addActionListener(ac1);
		} else if (mod.equals(("Mag"))) {
			btnMagneto.addActionListener(ac2);
		} else if (mod.equals(("Gyro"))) {
			btnGyro.addActionListener(ac3);
		}

	}

	/**
	 * Function to add some elements in order to obtain id+name of the drone
	 * 
	 * @param panel_north
	 * @param panel
	 * @param label
	 */
	private void addcombo_id(JPanel panelNorth, final JPanel panel,
			final JLabel label, final JPanel panelMod) {
		JLabel labelId = new JLabel("Choissisez l'id de votre drone");
		GridBagConstraints gbcLabelId = new GridBagConstraints();
		gbcLabelId.insets = new Insets(0, 0, 5, 5);
		gbcLabelId.anchor = GridBagConstraints.EAST;
		gbcLabelId.gridx = 1;
		gbcLabelId.gridy = 1;
		panelNorth.add(labelId, gbcLabelId);
		final JComboBox combo = new JComboBox();
		GridBagConstraints gbcComboBox = new GridBagConstraints();
		gbcComboBox.anchor = GridBagConstraints.WEST;
		gbcComboBox.insets = new Insets(0, 0, 5, 5);
		gbcComboBox.gridx = 2;
		gbcComboBox.gridy = 1;
		panelNorth.add(combo, gbcComboBox);

		// Label with the drone's name
		final JLabel dronesName = new JLabel();
		dronesName.setBounds(273, 35, 114, 29);
		dronesName.setBackground(Color.WHITE);
		panel.add(dronesName);

		// Add drone id detected
		combo.addItem(" ");
		// combo.addItem("1");
		try {
			System.out.println("IvyIdListener");
			imu.IvyIdListener();
			System.out.println("Passé");
			ArrayList<Integer> l = (ArrayList<Integer>) imu.getList();
			if (!l.isEmpty()) {
				for (Integer i : l) {
					combo.addItem(i.toString());
				}
			}
			else System.out.println("Florent est une merde");
			imu.stopIdListener();
		} catch (IvyException eivy) {
			eivy.printStackTrace();
		}

		// combo.addItem("1");
		// combo.addItem("2");
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (combo.getSelectedItem().toString() == " ") {
					panel.setVisible(false);
					panelMod.setVisible(false);
				} else {
					id = Integer.parseInt(combo.getSelectedItem().toString());
					imu.setId(id);
					try {
						imu.IvyConfigListener();
						name = imu.getAcName();
						url = imu.getSettingsURL();
						label.setText("<html>Le nom de votre drone d'id "
								+ Integer.toString(id) + " est : </html>");
						dronesName.setText(name);
						panel.setVisible(true);

						addcomboMod(panelMod, panel);
					} catch (GetConfigException eConf) {
						combo.setSelectedItem(" ");
						JOptionPane
								.showMessageDialog(
										null,
										"Bus problem, check if ground station is launched ",
										"Bus error", JOptionPane.ERROR_MESSAGE);
					} catch (IvyException e1){
						e1.printStackTrace();
					} catch (InterruptedException e1){
						e1.printStackTrace();
					}

				}
			}
		});
	}

	/**
	 * Function to add some elements in order to change drone mod
	 * 
	 * @param panel
	 */
	private void addcomboMod(JPanel panelMod, JPanel panelName) {
		final JLabel labelMod = new JLabel(
				"Veuillez choisir le mode de votre drone " + name + " :");
		labelMod.setBounds(80, 45, 360, 42);
		panelMod.add(labelMod);
		final JComboBox comboMod = new JComboBox();
		comboMod.setBounds(105, 124, 284, 24);
		panelMod.add(comboMod);
		try {
			ExtractRawData d = new ExtractRawData(url);
			List<String> listMod = d.extract();
			panelMod.setVisible(true);
			if (!listMod.isEmpty()) {
				for (String i : listMod) {
					comboMod.addItem(i.toString());
				}
			}
			imu.IvyRawListener(d.getIndex());
			int modeActuel = imu.getTelemetryMode();
			System.out.println("Mode actuel : " + modeActuel );
			comboMod.setSelectedIndex(modeActuel);
			comboMod.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (comboMod.getSelectedItem().toString() != " ") {
						// ("C:\\Users\\Alino�\\Desktop\\settings_booz2.xml");
						try {
							Integer mod = new Integer(comboMod
									.getSelectedIndex());
							imu.sendMode(id, mod.doubleValue());
						} catch (IvyException e1) {
							e1.printStackTrace();
						}
						if (imu.isRawOnBus()) {
							btnAccelero.setEnabled(true);
							btnMagneto.setEnabled(true);
						}

					}
				}
			});
			//ivyraw.finalize();

		} catch (IOException e) {
			panelMod.setVisible(false);
			labelMod.setText("");
			panelName.setVisible(false);
			JOptionPane.showMessageDialog(null, "Drone's name incorrect : "
					+ name, "Error name", JOptionPane.ERROR_MESSAGE);

		} catch (IncorrectXmlException e) {
			panelMod.setVisible(false);
			labelMod.setText("");
			panelName.setVisible(false);
			JOptionPane.showMessageDialog(null, "Incorrect file : " + name
					+ "/settings.xml", "File error", JOptionPane.ERROR_MESSAGE);
		} catch (IvyException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Function to add some elements in order to make accelerometers calibration
	 */
	private void modAccelero() {

		btnAccelero.removeActionListener(ac1);
		mod = "Accl";
		cl.show(content, listContent[1]);
		btnMagneto.setEnabled(false);
		btnGyro.setEnabled(false);
		title.setText("<html><br>Calibration des Accéléromètre</html>");
		addButton(panelAccl);
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(1600, 75));
		panelNorth.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(375, 0, 500, 75);
		inst.setText("<html>Suivez les instructions écrites à l'écran</html>");
		panelNorth.add(inst);
		panelAccl.add(panelNorth, BorderLayout.NORTH);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelAccl.add(panelCenter, BorderLayout.CENTER);
		// Panel_dessin
		JPanel panelDessin = new JPanel();
		Border borderMod = BorderFactory.createRaisedBevelBorder();
		panelDessin.setBackground(Color.WHITE);
		panelDessin.setBounds(50, 0, 625, 325);
		panelDessin.setBorder(borderMod);
		panelCenter.add(panelDessin);
		// Panel instruction
		panelInst = new JPanel();
		panelInst.setBounds(825, 0, 320, 420);
		panelInst.setBorder(borderMod);
		panelInst.setBackground(Color.WHITE);
		panelCenter.add(panelInst);
		// Panel progress Bar
		panelBar = new JPanel();
		panelBar.setBounds(50, 375, 625, 50);
		panelBar.setBackground(Color.WHITE);
		panelBar.setLayout(null);
		panelCenter.add(panelBar);

	}

	/**
	 * Function to add some elements in order to make magnetometers calibration
	 */
	private void modMagneto() {
		btnMagneto.removeActionListener(ac2);
		mod = "Mag";
		cl.show(content, listContent[2]);
		btnAccelero.setEnabled(false);
		btnGyro.setEnabled(false);
		title.setText("<html><br>Calibration des Magnétomètres</html>");
		addButton(panelMag);
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(1600, 100));
		panelNorth.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(275, 15, 500, 75);
		inst.setText("<html><br><br>Tournez le drone dans tous les sens afin de remplir les zones rouges</html>");
		panelNorth.add(inst);
		panelMag.add(panelNorth, BorderLayout.NORTH);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelMag.add(panelCenter, BorderLayout.CENTER);
		panelDessin = new JPanel();
		panelDessin.setBounds(125, 0, 775, 425);
		panelCenter.add(panelDessin);

	}

	/**
	 * Function to add some elements in order to make gyrometers calibration
	 */
	private void modGyro() {
		btnGyro.removeActionListener(ac3);
		mod = "Gyro";
		cl.show(content, listContent[3]);
		btnAccelero.setEnabled(false);
		btnMagneto.setEnabled(false);
		title.setText("<html><br>Calibration des Gyromètres</html>");
	}

	/**
	 * Funtion to add Quit and Stop button on the panel_center
	 */
	private void addButton(final JPanel panel) {
		final JPanel panelSouthCenter = new JPanel();
		panel.add(panelSouthCenter, BorderLayout.SOUTH);
		panelSouthCenter.setPreferredSize(new Dimension(1600, 50));
		GridBagLayout gblPanelSouthCenter = new GridBagLayout();
		gblPanelSouthCenter.columnWidths = new int[] { 300, 300, 300, 30, 100,
				50, 100, 100 };
		gblPanelSouthCenter.rowHeights = new int[] { 0, 25, 25 };
		gblPanelSouthCenter.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0 };
		gblPanelSouthCenter.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelSouthCenter.setLayout(gblPanelSouthCenter);

		// Stop Button
		btnReturn = new JButton("Return");
		GridBagConstraints gbcBtnReturn = new GridBagConstraints();
		gbcBtnReturn.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnReturn.insets = new Insets(0, 0, 0, 5);
		gbcBtnReturn.gridx = 4;
		gbcBtnReturn.gridy = 0;
		panelSouthCenter.add(btnReturn, gbcBtnReturn);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imu.deleteData();
				panel.removeAll();
				cl.show(content,listContent[0]);
			}
		});
		// Quit Button
		btnQuit = new JButton("Quitter");
		GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnQuitter.gridx = 6;
		gbcBtnQuitter.gridy = 0;
		panelSouthCenter.add(btnQuit, gbcBtnQuitter);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panelHome.removeAll();
				panelHome.repaint();
				cl.show(content, listContent[0]);
				initialise();
			}
		});
		// Stop Button
		btnStop = new JButton("STOP");
		GridBagConstraints gbcBtnStop = new GridBagConstraints();
		gbcBtnStop.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnStop.insets = new Insets(0, 0, 0, 5);
		gbcBtnStop.gridx = 2;
		gbcBtnStop.gridy = 0;
		panelSouthCenter.add(btnStop, gbcBtnStop);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				imu.stopListenImu(imu.getCalibration());
				
			}
		});
	}

	/**
	 * Classe qui implément ActionListener pour appliquer un mode suivant
	 * l'action réalisé sur un bouton
	 * 
	 * @author gui
	 * 
	 */
	class Action implements ActionListener {
		private String mod;

		public Action(String mod) {
			super();
			this.mod = mod;
		}

		public void actionPerformed(ActionEvent e) {
			if (mod.equals("Accl")) {
				modAccelero();
				Thread model = new Thread() {
					public void run() {
						StartUp start = new StartUp(
								TypeCalibration.ACCELEROMETER, panelDessin, id,
								panelInst, panelBar,imu);

					}
				};
				model.start();

			} else if (mod.equals("Mag")) {
				modMagneto();
				Thread model = new Thread() {
					public void run() {
						StartUp start = new StartUp(
								TypeCalibration.MAGNETOMETER, panelDessin, id,imu);

					}
				};
				model.start();
			} else if (mod.equals("Gyro")) {
				modGyro();
			}
		}
	}
	
	public void backHome(){
		imu.deleteData();
		TypeCalibration t = imu.getCalibration();
		if (t==TypeCalibration.ACCELEROMETER){
			panelAccl.removeAll();
		}
		else if (t==TypeCalibration.MAGNETOMETER){
			panelMag.removeAll();
		}
		else panelGyro.removeAll();
		cl.show(content,listContent[0]);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329728363330435663L;

}
