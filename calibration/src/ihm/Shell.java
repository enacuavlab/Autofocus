/**
 * Package IHM contents the interface of our application
 */
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
 * @author Guillaume
 * @version 2.0
 */
public class Shell extends JFrame {
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
	private JButton btnQuit, btnStop, btnReturn;
	/**
	 * Drone's id
	 */
	private int id;
	/**
	 * Drone's name and url of the drone's settings.xml
	 */
	private String name, url;
	/**
	 * Different panels of our Application : panelHome , panelGyro, panelMag ->
	 * background panels. content contents all the background panels.
	 * panelDessinMag to design calibration
	 */
	private JPanel panelHome, panelAccl, panelGyro, panelMag, content,
			panelDessinMag;
	/**
	 * listContent to get an access to the background panels
	 */
	private String[] listContent = { "HOME", "ACCL", "MAG", "GYRO" };
	/**
	 * CardLayout
	 */
	private CardLayout cl;
	/**
	 * To know the mode for action
	 */
	private String type;
	/**
	 * Buttons actions
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
	 * 
	 */
	private Shell me = null;
	/**
	 * Model for the comboBox
	 */
	private DefaultComboBoxModel comboModelId , comboModelMod ;
	/**
	 * Combo to choose the mod of telemetry
	 */
	private JComboBox comboMod;
	/**
	 * Size of the window
	 */
	private final int widthWindow = 1600, heightWindow = 800;

	/**
	 * Constructor which initialises the window with options Button and the
	 * cardlayout used.
	 */
	public Shell() {
		super();
		// Shell
		me = this;
		setTitle("Autofocus");
		setSize(widthWindow, heightWindow);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		// CardLayout with his panels
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

		// Option's panel and buttons
		addOptions();
		// Mod of our application
		type = "Home";

		initialise();

	}

	/**
	 * Fonction pour initialiser et réinitialiser l'accueil
	 */
	private void initialise() {

		// For test
		// btnAccelero.setEnabled(true);
		// btnMagneto.setEnabled(true);

		// Activate option buttons
		activateButton(type);

		//id 
		id = -1;
		
		//comboMod
		comboMod = new JComboBox();
		comboModelMod = new DefaultComboBoxModel();
		comboMod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()== ItemEvent.SELECTED){
					try {
						Integer mod = new Integer(comboMod
								.getSelectedIndex());
						imu.sendMode(id, mod.doubleValue());
					} catch (IvyException e1) {
						e1.printStackTrace();
					}
					// Test if their is Raw Data send by the IMU

					/*if (imu.isRawOnBus()) {
						btnAccelero.setEnabled(true);
						btnMagneto.setEnabled(true);
					}
					imu.stopIvyRawListener();*/
				}
			}
		});
		// Panel title
		JPanel panelTitle = new JPanel();
		panelTitle.setLayout((null));
		Border borderTitle = BorderFactory.createBevelBorder(
				BevelBorder.LOWERED, Color.black, Color.white);
		panelTitle.setBackground(Color.LIGHT_GRAY);
		panelTitle.setBorder(borderTitle);
		panelTitle.setPreferredSize(new Dimension(widthWindow, 100));
		getContentPane().add(panelTitle, "North");
		title = new JLabel("<html>Home</html>");
		title.setBounds(600, 35, 400, 40);
		title.setFont(new Font("Calibri", Font.BOLD, 28));
		JLabel labelIndImu = new JLabel();
		labelIndImu.setBounds(0, 30, 150, 20);
		labelIndImu.setText("Uav's presence : ");
		JLabel labelImu = new JLabel();
		labelImu.setBounds(120, 30, 20, 20);
		labelImu.setOpaque(true);
		panelTitle.add(labelImu);
		panelTitle.add(labelIndImu);
		panelTitle.add(title);
		// IMU
		try {
			imu = new IMU(labelImu);
		} catch (IvyException e) {
			JOptionPane.showMessageDialog(null, "Ivy Bus problem" + name,
					"Error Bus ", JOptionPane.ERROR_MESSAGE);
		}
		// Panel which contains comboBox for the id choice
		JPanel panelNorth = new JPanel();
		panelHome.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setPreferredSize(new Dimension(widthWindow, 100));
		GridBagLayout gblPanelNorth = new GridBagLayout();
		gblPanelNorth.columnWidths = new int[] { 300, 300, 10, 10, 10 };
		gblPanelNorth.rowHeights = new int[] { 40, 40, 40 };
		gblPanelNorth.columnWeights = new double[] { 0.0, 0.0, 1.0, 5.0, 20.0 };
		gblPanelNorth.rowWeights = new double[] { 0.0, 0.0 };
		panelNorth.setLayout(gblPanelNorth);

		// Panel Center which contains panel mod and name
		JPanel panelCenter = new JPanel();
		panelHome.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(null);

		// Panel_name with 2 labels
		JPanel panelName = new JPanel();
		Border borderName = BorderFactory.createRaisedBevelBorder();
		panelName.setBorder(borderName);
		panelName.setBackground(new Color(202, 205, 236));
		panelName.setBounds(275, 38, 508, 99);
		panelCenter.add(panelName);
		panelName.setLayout(null);
		panelName.setVisible(false);

		// Contains the name of the drone
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
				.createTitledBorder("Type of calibration");
		panelOptions.setBorder(borderType);
		panelOptions.add(btnAccelero);
		panelOptions.add(btnMagneto);
		panelOptions.add(btnGyro);
	}

	/**
	 * Activate the option buttons
	 * 
	 * @param mod
	 */
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
		JLabel labelId = new JLabel("Choose id of your drone");
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

		JButton reload = new JButton("Reload");
		GridBagConstraints gbcReload = new GridBagConstraints();
		gbcReload.anchor = GridBagConstraints.WEST;
		gbcReload.insets = new Insets(0, 0, 5, 5);
		gbcReload.gridx = 3;
		gbcReload.gridy = 1;
		panelNorth.add(reload, gbcReload);
		reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id != -1){
					imu.stopIvyRawListener();
					imu.setId(-1);
				}
				
				if (panel.isVisible()) {
					try {
						imu.IvyIdListener();
						ArrayList<Integer> l = (ArrayList<Integer>) imu
								.getList();
						l.add(0, -1);
						comboModelId.removeAllElements();
						
						if (!l.isEmpty()) {
							for (Integer i : l) {
								comboModelId.addElement((Integer) i);
							}
						}
						combo.setModel(comboModelId);
						imu.stopIdListener();
					} catch (IvyException eivy) {
						eivy.printStackTrace();
					}
					addcomboMod(panelMod, panel);
				} else {

					try {

						imu.IvyIdListener();
						ArrayList<Integer> l = (ArrayList<Integer>) imu
								.getList();
						l.add(0, -1);
						comboModelId.removeAllElements();
						if (!l.isEmpty()) {
							for (Integer i : l) {
								comboModelId.addElement((Integer) i);
							}
						}
						imu.stopIdListener();
					} catch (IvyException eivy) {
						eivy.printStackTrace();
					}

				}
			}
		});

		// Label with the drone's name
		final JLabel dronesName = new JLabel();
		dronesName.setBounds(273, 35, 114, 29);
		dronesName.setBackground(Color.WHITE);
		panel.add(dronesName);

		// Add drone id detected
		comboModelId = new DefaultComboBoxModel();
		try {
			imu.IvyIdListener();
			ArrayList<Integer> l = (ArrayList<Integer>) imu.getList();
			//l.add(0, -1);
			if (!l.isEmpty()) {
				for (Integer i : l) {
					comboModelId.addElement((Integer) i);
				}
			}
			combo.setModel(comboModelId);
			imu.stopIdListener();
		} catch (IvyException eivy) {
			eivy.printStackTrace();
		}
		// Users choose their id drone
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {

					if ((Integer) combo.getSelectedItem() == -1) {
						panel.setVisible(false);
						panelMod.setVisible(false);
					} else {
						id = Integer.parseInt(combo.getSelectedItem()
								.toString());
						try {
							// Get name and url of the drone
							imu.IvyConfigListener(id);
							name = imu.getAcName();
							url = imu.getSettingsURL();
							label.setText("<html>The selected id drone name ( id : "
									+ Integer.toString(id) + " ) is : </html>");
							dronesName.setText(name);
							panel.setVisible(true);
							panelMod.setVisible(true);
							// To change drone mod
							addcomboMod(panelMod, panel);
						} catch (GetConfigException eConf) {
							// Check lack of communication
							combo.setSelectedItem(" ");
							JOptionPane
									.showMessageDialog(
											null,
											"Bus problem, check if ground station is launched ",
											"Bus error",
											JOptionPane.ERROR_MESSAGE);
						} catch (IvyException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

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
				"Please choose the mod of your drone called " + name + " :");
		labelMod.setBounds(60, 45, 385, 42);
		panelMod.add(labelMod);
		
		
		comboMod.setBounds(105, 124, 284, 24);
		panelMod.add(comboMod);
		comboModelMod.removeAllElements();
		try {
			// Detect all the available mods of the drone
			ExtractRawData d = new ExtractRawData(url);
			// Detect the current mod
			
			List<String> listMod = d.extract();
			if (!listMod.isEmpty()) {
				for (String i : listMod) {
					comboModelMod.addElement(i.toString());
				}
			}
			comboMod.setModel(comboModelMod);
			imu.IvyRawListener(d.getIndex(),btnMagneto,btnAccelero,id,comboMod);
			
			
			comboMod.setSelectedIndex(imu.getTelemetryMode());
			// Users can change the current mod
			
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
		// Remove actionListener to prevent a new action during the calibration
		btnAccelero.removeActionListener(ac1);
		// Accelerometers Calibration
		type = "Accl";
		// Show panelAccl
		cl.show(content, listContent[1]);
		// Disable the other buttons
		btnMagneto.setEnabled(false);
		btnGyro.setEnabled(false);

		title.setText("<html><br>Accelerometers Calibration</html>");
		// Add Quit Stop and Return Button
		addButton(panelAccl);

		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(1600, 75));
		panelNorth.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(375, 0, 500, 75);
		inst.setText("<html>Follow the directions on the screen</html>");
		panelNorth.add(inst);
		panelAccl.add(panelNorth, BorderLayout.NORTH, 0);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelAccl.add(panelCenter, BorderLayout.CENTER, 1);
	}

	/**
	 * Function to add some elements in order to make magnetometers calibration
	 */
	private void modMagneto() {
		// Remove actionListener to prevent a new action during the calibration
		btnMagneto.removeActionListener(ac2);
		// Magnetometers calibration
		type = "Mag";
		// Show panelMag
		cl.show(content, listContent[2]);
		// Disable the other buttons
		btnAccelero.setEnabled(false);
		btnGyro.setEnabled(false);
		title.setText("<html><br>Magnetometers Calibration</html>");
		// Add quit , return and stop buttons
		addButton(panelMag);
		// Directions
		JPanel panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(widthWindow, 100));
		panelNorth.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(315, 15, 500, 75);
		inst.setText("<html><br><br>Rotate the drone in all directions in order to fill red areas</html>");
		panelNorth.add(inst);
		panelMag.add(panelNorth, BorderLayout.NORTH, 0);

		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(null);
		panelMag.add(panelCenter, BorderLayout.CENTER, 1);
		// Panel which contains the sphere
		panelDessinMag = new JPanel();
		panelDessinMag.setBounds(125, 0, 775, 425);
		panelCenter.add(panelDessinMag);
	}

	/**
	 * Function to add some elements in order to make gyrometers calibration
	 */
	private void modGyro() {
		// Remove actionListener to prevent a new action during the calibration
		btnGyro.removeActionListener(ac3);
		// Gyrometers calibration
		type = "Gyro";
		// Show panelGyro
		cl.show(content, listContent[3]);
		btnAccelero.setEnabled(false);
		btnMagneto.setEnabled(false);
		title.setText("<html><br>Gyrometers Calibration</html>");
	}

	/**
	 * Funtion to add Quit and Stop and Return button on the panel_center
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
				// Stop listen imu
				imu.stopListenImu(imu.getCalibration());
				// Return to the Home
				backHome();

				addButton(panel);
				// Show panelHome
				cl.show(content, listContent[0]);
			}
		});
		// Quit Button
		btnQuit = new JButton("Quit");
		GridBagConstraints gbcBtnQuitter = new GridBagConstraints();
		gbcBtnQuitter.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnQuitter.gridx = 6;
		gbcBtnQuitter.gridy = 0;
		panelSouthCenter.add(btnQuit, gbcBtnQuitter);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Stop listen IMU
				imu.stopListenImu(imu.getCalibration());
				// Remove some components of the panel
				panel.remove(0);
				panel.remove(1);
				// Remove elements of the panelHome to have an empty panelHome
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
			public void actionPerformed(ActionEvent e) {
				// Stop listen IMU
				imu.stopListenImu(imu.getCalibration());
				// Show the result of the calibration
				result = new Result(me, "Result", true, imu);
				new Thread() {
					public void run() {
						result.majResult();
					}
				}.start();
				result.showResult();
			}
		});
	}

	/**
	 * Allow to return to the home panel
	 */
	public void backHome(){
		imu.deleteDataLog();
		TypeCalibration t = imu.getCalibration();
		if (t.equals(TypeCalibration.ACCELEROMETER)) {
			panelAccl.remove(0);
			panelAccl.remove(1);
			btnAccelero.addActionListener(ac1);
			/*btnMagneto.setEnabled(true);
			btnAccelero.setEnabled(true);*/
			ExtractRawData d;
			try {
				d = new ExtractRawData(url);
				System.out.println(url);
				imu.IvyRawListener(d.getIndex(),btnMagneto,btnAccelero,id,comboMod);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IvyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncorrectXmlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (t.equals(TypeCalibration.MAGNETOMETER)) {
			panelMag.remove(0);
			panelMag.remove(1);
			btnMagneto.addActionListener(ac2);
			ExtractRawData d;
			try {
				d = new ExtractRawData(url);
				imu.IvyRawListener(d.getIndex(),btnMagneto,btnAccelero,id,comboMod);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IvyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IncorrectXmlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*btnAccelero.setEnabled(true);
			btnMagneto.setEnabled(true);*/
		} else {
			panelGyro.removeAll();
			btnGyro.addActionListener(ac3);
		}
		title.setText("Home");
		cl.show(content, listContent[0]);
	}

	/**
	 * Make an actionListener according to the type of the calibration
	 * 
	 * @author Guillaume
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
				imu.stopIvyRawListener();
				Thread model = new Thread() {
					public void run() {
						@SuppressWarnings("unused")
						StartUp start = new StartUp(
								TypeCalibration.ACCELEROMETER, panelAccl, id,
								imu, 1);

					}
				};
				model.start();

			} else if (mod.equals("Mag")) {
				modMagneto();
				imu.stopIvyRawListener();
				Thread model = new Thread() {
					public void run() {
						@SuppressWarnings("unused")
						StartUp start = new StartUp(
								TypeCalibration.MAGNETOMETER, panelDessinMag,
								id, imu);

					}
				};
				model.start();
			} else if (mod.equals("Gyro")) {
				modGyro();
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329728363330435663L;

}
