package ihm;

import fr.dgac.ivy.IvyException;
import iddrone.IvyIdListener;

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
import javax.swing.border.Border;

import rawmode.ExtractRawData;
import rawmode.IncorrectXmlException;
import rawmode.IvyRawListener;


public class Shell extends JFrame {

	private JButton btn_accelero, btn_magneto, btn_gyro;
	private JLabel titre;
	private JButton btnQuitter, btnStop;
	private int id;
	private JPanel panel_home, panel_accl, panel_gyro, panel_mag, content;
	private String[] listContent = { "HOME", "ACCL", "MAG", "GYRO" };
	private CardLayout cl;

	public Shell() {
		super();
		// Shell
		setTitle("Autofocus"); // On donne un titre à l'application
		setSize(1600, 800); // On donne une taille à notre fenêtre
		setLocationRelativeTo(null); // On centre la fenêtre sur l'écran
		setResizable(true); // On autorise le redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à
														// l'application de se
														// fermer lors du clic
														// sur la croix
		getContentPane().setLayout(new BorderLayout());
		cl = new CardLayout();
		content = new JPanel();
		content.setLayout(cl);
		panel_home = new JPanel();
		panel_accl = new JPanel();
		panel_mag = new JPanel();
		panel_gyro = new JPanel();
		panel_home.setLayout(new BorderLayout());
		panel_accl.setLayout(new BorderLayout());
		panel_mag.setLayout(new BorderLayout());
		panel_gyro.setLayout(new BorderLayout());
		content.add(panel_home, listContent[0]);
		content.add(panel_accl, listContent[1]);
		content.add(panel_mag, listContent[2]);
		content.add(panel_gyro, listContent[3]);
		getContentPane().add(content, BorderLayout.CENTER);
		addOptions();
		initialise();// On initialise notre fenêtre

	}

	private void initialise() {
		// Pour test
		btn_accelero.setEnabled(true);
		btn_magneto.setEnabled(true);

		// Panel titre

		JPanel panel_titre = new JPanel();
		panel_titre.setBackground(Color.blue);
		panel_titre.setPreferredSize(new Dimension(1600, 100));
		getContentPane().add(panel_titre, "North");
		titre = new JLabel("<html><br>Veuillez remplir les champs</html>");
		titre.setFont(new Font("Calibri", Font.BOLD, 28));
		panel_titre.add(titre);

		JPanel panel_north_center = new JPanel();
		panel_home.add(panel_north_center, BorderLayout.NORTH);
		panel_north_center.setPreferredSize(new Dimension(1600, 100));
		GridBagLayout gbl_panel_north_center = new GridBagLayout();
		gbl_panel_north_center.columnWidths = new int[] { 300, 300, 30 };
		gbl_panel_north_center.rowHeights = new int[] { 40, 40, 40 };
		gbl_panel_north_center.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		gbl_panel_north_center.rowWeights = new double[] { 0.0, 0.0 };
		panel_north_center.setLayout(gbl_panel_north_center);

		JPanel panel_center = new JPanel();
		panel_home.add(panel_center, BorderLayout.CENTER);
		panel_center.setLayout(null);

		// Panel_name with a textfield and a label
		JPanel panel_name = new JPanel();
		Border border_name = BorderFactory.createRaisedBevelBorder();
		panel_name.setBorder(border_name);
		panel_name.setBackground(Color.MAGENTA);
		panel_name.setBounds(275, 38, 508, 99);
		panel_center.add(panel_name);
		panel_name.setLayout(null);
		panel_name.setVisible(false);

		JLabel label_name = new JLabel();
		label_name.setBounds(106, 12, 138, 60);
		panel_name.add(label_name);

		JLabel dronesName = new JLabel();
		dronesName.setBounds(273, 35, 114, 29);
		panel_name.add(dronesName);
		dronesName.setBackground(Color.WHITE);
		dronesName.setText("blender");
		// Panel_mod
		Border border_mod = BorderFactory.createRaisedBevelBorder();
		JPanel panel_mod = new JPanel();
		panel_mod.setBackground(Color.ORANGE);
		panel_mod.setForeground(Color.BLACK);
		panel_mod.setBounds(275, 213, 508, 177);
		panel_mod.setBorder(border_mod);
		panel_center.add(panel_mod);
		panel_mod.setVisible(false);
		panel_mod.setLayout(null);

		/*
		 * textField.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e){ name=textField.getText();
		 * addcombo_mod(panel_mod); } });
		 */

		addcombo_id(panel_north_center, panel_name, label_name, panel_mod);

	}

	/**
	 * Function to add a Panel in order to choose the type of the calibration
	 */
	private void addOptions() {
		// Panel options
		JPanel panel_options = new JPanel();
		panel_options.setBackground(Color.red);
		getContentPane().add(panel_options, "West");
		GridLayout gd_options = new GridLayout(5, 1);
		gd_options.setVgap(30);
		panel_options.setLayout(gd_options);

		// Button
		btn_accelero = new JButton("Accelerometers");
		btn_accelero.setEnabled(false);
		btn_accelero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modAccelero();
			}
		});
		btn_magneto = new JButton("Magnetometers");
		btn_magneto.setEnabled(false);
		btn_magneto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modMagneto();
			}
		});
		btn_gyro = new JButton("Gyrometers");
		btn_gyro.setEnabled(false);
		btn_gyro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				modGyro();
			}
		});

		// Button insertion
		Border border_type = BorderFactory
				.createTitledBorder("Type de calibration");
		panel_options.setBorder(border_type);
		// JLabel titre_options= new JLabel("Type decalibration");
		// panel_options.add(titre_options);
		// panel_options.add(new JLabel());
		panel_options.add(btn_accelero);
		// panel_options.add(new JLabel());
		panel_options.add(btn_magneto);
		// panel_options.add(new JLabel());
		panel_options.add(btn_gyro);
	}

	/**
	 * Function to add some elements in order to obtain id+name of the drone
	 * 
	 * @param panel_north_center
	 * @param panel
	 * @param label
	 */
	private void addcombo_id(JPanel panel_north_center, final JPanel panel,
			final JLabel label, final JPanel panel_mod) {
		JLabel label_id = new JLabel("Choissisez l'id de votre drone");
		GridBagConstraints gbc_label_id = new GridBagConstraints();
		gbc_label_id.insets = new Insets(0, 0, 5, 5);
		gbc_label_id.anchor = GridBagConstraints.EAST;
		gbc_label_id.gridx = 1;
		gbc_label_id.gridy = 1;
		panel_north_center.add(label_id, gbc_label_id);
		final JComboBox combo = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		panel_north_center.add(combo, gbc_comboBox);

		// Add drone id detected
		combo.addItem(" ");
		combo.addItem("1");
		try {
			IvyIdListener ivyid = new IvyIdListener();
			ArrayList<Integer> l = (ArrayList<Integer>) ivyid.getList();
			if (!l.isEmpty()) {
				for (Integer i : l) {
					combo.addItem(i.toString());
				}
			}
			ivyid.stop();
		} catch (IvyException eivy) {
			eivy.printStackTrace();
		}
		
		// combo.addItem("1");
		// combo.addItem("2");
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (combo.getSelectedItem().toString() == " ") {
					panel.setVisible(false);
					panel_mod.setVisible(false);
				} else {
					id = Integer.parseInt(combo.getSelectedItem().toString());
					label.setText("<html>Le nom de votre drone d'id "
							+ Integer.toString(id) + " est :</html>");
					panel.setVisible(true);
					addcombo_mod(panel_mod, "blender");
				}
			}
		});
	}

	/**
	 * Function to add some elements in order to change drone mod
	 * 
	 * @param panel
	 */
	private void addcombo_mod(JPanel panel_mod, String name) {

		final JLabel label_mod = new JLabel(
				"Veuillez choisir le mode de votre drone " + name + " :");
		label_mod.setBounds(80, 45, 360, 42);
		panel_mod.add(label_mod);
		final JComboBox combo_mod = new JComboBox();
		combo_mod.setBounds(105, 124, 284, 24);
		panel_mod.add(combo_mod);
		try {
			ExtractRawData d = new ExtractRawData(System.getenv("HOME")
					+ "/paparazzi/var/" + name + "/settings.xml");
			List<String> list_mod = d.extract();
			panel_mod.setVisible(true);
			if (!list_mod.isEmpty()) {
				for (String i : list_mod) {
					combo_mod.addItem(i.toString());
				}
			}
			final IvyRawListener ivyraw = new IvyRawListener(id, d.getIndex());
			int mode_actuel = ivyraw.getTelemetryMode();
			combo_mod.setSelectedIndex(mode_actuel);
			combo_mod.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (combo_mod.getSelectedItem().toString() != " ") {
						// ("C:\\Users\\Alino�\\Desktop\\settings_booz2.xml");
						try {
							Integer mod = new Integer(combo_mod
									.getSelectedIndex());
							ivyraw.sendMode(id, mod.doubleValue());
						} catch (IvyException e1) {
							e1.printStackTrace();
						}
						if (ivyraw.isRawOnBus()) {
							btn_accelero.setEnabled(true);
							btn_magneto.setEnabled(true);
						}

					}
				}
			});

		} catch (IOException e) {
			panel_mod.setVisible(false);
			label_mod.setText("");
			JOptionPane.showMessageDialog(null, "Drone's name unknown : "
					+ name, "Error name", JOptionPane.ERROR_MESSAGE);

		} catch (IncorrectXmlException e) {
			panel_mod.setVisible(false);
			label_mod.setText("");
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
		// btn_accelero.removeActionListener();
		cl.show(content, listContent[1]);
		btn_magneto.setEnabled(false);
		btn_gyro.setEnabled(false);
		titre.setText("<html><br>Calibration des Accéléromètre</html>");
		addButton(panel_accl);
		JPanel panel_north = new JPanel();
		panel_north.setPreferredSize(new Dimension(1600, 75));
		panel_north.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(375, 0, 500, 75);
		inst.setText("<html>Suivez les instructions écrites à l'écran</html>");
		panel_north.add(inst);
		panel_accl.add(panel_north, BorderLayout.NORTH);
		JPanel panel_center = new JPanel();
		panel_center.setLayout(null);
		panel_accl.add(panel_center, BorderLayout.CENTER);
		JPanel panel_dessin = new JPanel();
		Border border_mod = BorderFactory.createRaisedBevelBorder();
		panel_dessin.setBackground(Color.WHITE);
		panel_dessin.setBounds(50, 0, 625, 425);
		panel_dessin.setBorder(border_mod);
		panel_center.add(panel_dessin);
		JPanel panel_inst = new JPanel();
		panel_inst.setBounds(825, 0, 300, 425);
		panel_inst.setBorder(border_mod);
		panel_inst.setBackground(Color.WHITE);
		panel_center.add(panel_inst);
	}

	/**
	 * Function to add some elements in order to make magnetometers calibration
	 */
	private void modMagneto() {
		cl.show(content, listContent[2]);
		btn_accelero.setEnabled(false);
		btn_gyro.setEnabled(false);
		titre.setText("<html><br>Calibration des Magnétomètres</html>");
		addButton(panel_mag);
		JPanel panel_north = new JPanel();
		panel_north.setPreferredSize(new Dimension(1600, 100));
		panel_north.setLayout(null);
		JLabel inst = new JLabel();
		inst.setBounds(275, 15, 500, 75);
		inst.setText("<html><br><br>Tournez le drone dans tous les sens afin de remplir les zones rouges</html>");
		panel_north.add(inst);
		panel_mag.add(panel_north, BorderLayout.NORTH);
		JPanel panel_center = new JPanel();
		panel_center.setLayout(null);
		panel_mag.add(panel_center, BorderLayout.CENTER);
		JPanel panel_dessin = new JPanel();
		Border border_mod = BorderFactory.createRaisedBevelBorder();
		panel_dessin.setBackground(Color.WHITE);
		panel_dessin.setBounds(125, 0, 775, 425);
		panel_dessin.setBorder(border_mod);
		panel_center.add(panel_dessin);

	}

	/**
	 * Function to add some elements in order to make gyrometers calibration
	 */
	private void modGyro() {
		cl.show(content, listContent[3]);
		btn_accelero.setEnabled(false);
		btn_magneto.setEnabled(false);
		titre.setText("<html><br>Calibration des Gyromètres</html>");
	}

	/**
	 * Funtion to add Quit and Stop button on the panel_center
	 */
	private void addButton(final JPanel panel) {
		final JPanel panel_south_center = new JPanel();
		panel.add(panel_south_center, BorderLayout.SOUTH);
		panel_south_center.setPreferredSize(new Dimension(1600, 50));
		GridBagLayout gbl_panel_south_center = new GridBagLayout();
		gbl_panel_south_center.columnWidths = new int[] { 300, 300, 300, 30,
				100, 50, 100, 100 };
		gbl_panel_south_center.rowHeights = new int[] { 0, 25, 25 };
		gbl_panel_south_center.columnWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0 };
		gbl_panel_south_center.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		panel_south_center.setLayout(gbl_panel_south_center);

		// Stop Button
		btnStop = new JButton("STOP");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStop.insets = new Insets(0, 0, 0, 5);
		gbc_btnStop.gridx = 4;
		gbc_btnStop.gridy = 0;
		panel_south_center.add(btnStop, gbc_btnStop);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		// Quit Button
		btnQuitter = new JButton("Quitter");
		GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
		gbc_btnQuitter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnQuitter.gridx = 6;
		gbc_btnQuitter.gridy = 0;
		panel_south_center.add(btnQuitter, gbc_btnQuitter);
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel_home.removeAll();
				panel_home.repaint();
				cl.show(content, listContent[0]);
				initialise();

			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 497574999009605779L;

}
