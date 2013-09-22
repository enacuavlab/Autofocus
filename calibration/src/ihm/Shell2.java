package ihm;

import imu.Aircraft;
import imu.IMU;
import imu.IMUtest;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import calibrate.PrintLog;

import common.TypeCalibration;

import data.Data;
import ellipsoide.AffichSphere;
import ellipsoide.Sphere;
import filtre.FilterSphere;

public class Shell2 {

	/**
	 * IMU object to handle the other part of the control
	 * 
	 */
	private IMU imu;

	private JFrame frmCalibrate;
	
	private TypeCalibration type;

	private PrintLog log = new PrintLog();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Shell2 window = new Shell2();
					window.frmCalibrate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Shell2() {
		startImu();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalibrate = new JFrame();
		frmCalibrate.setFont(new Font("Arial", Font.PLAIN, 12));
		frmCalibrate.setTitle("Calibrate");
		frmCalibrate.setBounds(100, 100, 675, 550);
		frmCalibrate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalibrate.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel menuSide = new JPanel();
		frmCalibrate.getContentPane().add(menuSide, BorderLayout.WEST);
		menuSide.setBorder(new LineBorder(Color.GRAY));
		menuSide.setLayout(new MigLayout("", "[183px,grow 230]", "[41px][46px][46px][][][][][][][][][]"));

		JTextPane txtpnChooseAMode = new JTextPane();
		txtpnChooseAMode.setAlignmentY(Component.TOP_ALIGNMENT);
		txtpnChooseAMode.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtpnChooseAMode.setEditable(false);
		txtpnChooseAMode.setBackground(UIManager.getColor("Button.light"));
		txtpnChooseAMode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnChooseAMode.setText("Choose a mode of calibration");
		menuSide.add(txtpnChooseAMode, "cell 0 0,grow");

		final JButton btnNewButton_1 = new JButton("Accelerometers");

		btnNewButton_1.setEnabled(false);
		menuSide.add(btnNewButton_1, "cell 0 1,grow");

		final JButton btnNewButton = new JButton("Magnetometers");
		menuSide.add(btnNewButton, "cell 0 2,grow");
		btnNewButton.setEnabled(false);
		
		final JButton btnResults = new JButton("Results");
		btnResults.setVisible(false);

		menuSide.add(btnResults, "cell 0 10 1 2,grow");

		JPanel presentIcon = new JPanel();
		frmCalibrate.getContentPane().add(presentIcon, BorderLayout.NORTH);
		presentIcon.setBorder(new LineBorder(Color.GRAY));

		final JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 0, 0));

		JTextPane txtpnUavsPresent = new JTextPane();
		txtpnUavsPresent.setEditable(false);
		txtpnUavsPresent.setBackground(UIManager.getColor("Button.background"));
		txtpnUavsPresent.setText("UAV's presence");

		final JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.RED);

		JTextPane txtpnCorrectTelemetryMode = new JTextPane();
		txtpnCorrectTelemetryMode.setText("Correct telemetry mode");
		GroupLayout gl_presentIcon = new GroupLayout(presentIcon);
		gl_presentIcon.setHorizontalGroup(gl_presentIcon.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_presentIcon
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 24,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtpnUavsPresent,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 25,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(txtpnCorrectTelemetryMode,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(470, Short.MAX_VALUE)));
		gl_presentIcon
				.setVerticalGroup(gl_presentIcon
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_presentIcon
										.createSequentialGroup()
										.addGap(14)
										.addGroup(
												gl_presentIcon
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_presentIcon
																		.createSequentialGroup()
																		.addComponent(
																				txtpnCorrectTelemetryMode,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap())
														.addGroup(
																gl_presentIcon
																		.createSequentialGroup()
																		.addGroup(
																				gl_presentIcon
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								panel_1,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								Alignment.LEADING,
																								gl_presentIcon
																										.createParallelGroup(
																												Alignment.TRAILING,
																												false)
																										.addComponent(
																												panel_2,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												txtpnUavsPresent,
																												Alignment.LEADING)))
																		.addGap(14)))));
		presentIcon.setLayout(gl_presentIcon);

		final JPanel panel = new JPanel();
		frmCalibrate.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setLayout(new CardLayout(0, 0));


		
		JPanel welcome = new JPanel();
		panel.add(welcome, "name_281529050503524");
		welcome.setLayout(new MigLayout("",
				"[100px,grow 200][276px,grow 400][100px,grow 200]",
				"[46px][][41px][46px][][41px][46px]"));

		JSeparator separator = new JSeparator();
		welcome.add(separator, "flowx,cell 0 0,alignx left,aligny top");

		JTextPane txtpnFillTheField = new JTextPane();
		welcome.add(txtpnFillTheField, "cell 1 0");
		txtpnFillTheField.setEditable(false);
		txtpnFillTheField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnFillTheField.setBackground(UIManager.getColor("Button.light"));
		txtpnFillTheField
				.setText("Fill the fileds according to the UAV you want to calibrate properties");

		JLabel lblNewLabel = new JLabel("Name of the UAV");
		welcome.add(lblNewLabel, "cell 1 2,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		final JComboBox<Aircraft> comboBox = new JComboBox<Aircraft>();
		welcome.add(comboBox, "cell 1 3,grow");

		JLabel lblChooseModeSending = new JLabel("Choose mode sending RAW data");
		lblChooseModeSending.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.add(lblChooseModeSending, "cell 1 5,grow");

		final JComboBox<String> comboBox_1 = new JComboBox<String>();
		welcome.add(comboBox_1, "cell 1 6,grow");

		// Listeners for all panels

		imu.addIMUListener(new IMUAdaptater() {
			public void aircraftExited(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						panel_2.setBackground(new Color(255, 0, 0));
						btnNewButton.setEnabled(false);
						btnNewButton_1.setEnabled(false);
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}

			public void aircraftConnected(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						panel_2.setBackground(new Color(0, 255, 0));
						if (ac.getIsRawData()) {
							panel_1.setBackground(Color.GREEN);
							btnNewButton.setEnabled(true);
							btnNewButton_1.setEnabled(true);
						}
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}

			public void aircraftRawOn(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						panel_1.setBackground(new Color(0, 255, 0));
						
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}
			
			public void aircraftRawOff(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						panel_1.setBackground(new Color(255, 0, 0));
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}
		});

		// Listeners for welcome

		imu.addIMUListener(new IMUAdaptater() {
			public void aircraftConnected(Aircraft ac) {
				comboBox.addItem(ac);
				if (ac.equals(comboBox.getSelectedItem())) {
					try {
						panel_2.setBackground(Color.GREEN);
						comboBox_1.setModel(new DefaultComboBoxModel<String>(
								(ac.getModes().toArray(
										new String[1]))));
					} catch (Exception e) {
						System.out.println("Failure in getting modes");
						e.printStackTrace();
					}
				}
			}
			
			public void aircraftRawOn(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						btnNewButton.setEnabled(true);
						btnNewButton_1.setEnabled(true);
						
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}
			
			public void aircraftRawOff(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						btnNewButton.setEnabled(false);
						btnNewButton_1.setEnabled(false);
						
					}
				} catch (Exception e) {
					System.out.println("comboBox AC vide");
				}
			}
			
			public void aircraftExited(Aircraft ac) {
				if (ac.equals(comboBox.getSelectedItem())) {
					comboBox_1.setModel(new DefaultComboBoxModel<String>());
				}
				comboBox.removeItem(ac);
			}	

			public void aircraftModChanged(Aircraft ac) {
				try {
					if (comboBox.getSelectedItem().equals(ac)) {
						comboBox_1.setSelectedIndex(ac.getMode());
						IMUtest.main(new String[1]);
					}
				} catch (Exception e) {
					System.out.println("comboBox mode vide");
				}
			}
		});

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("selected");
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					try {
						panel_2.setBackground(Color.GREEN);
						comboBox_1.setModel(new DefaultComboBoxModel<String>(
								((Aircraft) arg0.getItem()).getModes().toArray(
										new String[1])));
						if (((Aircraft) arg0.getItem()).getIsRawData()) {
							panel_1.setBackground(Color.GREEN);
							btnNewButton.setEnabled(true);
							btnNewButton_1.setEnabled(true);
						}  else {
							panel_1.setBackground(Color.RED);
							btnNewButton.setEnabled(false);
							btnNewButton_1.setEnabled(false);
						}
					} catch (Exception e) {
						System.out.println("Failure downcasting to aircraft");
						e.printStackTrace();
					}
				}
			}
		});
		
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("mode selected");
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					try {
						imu.changeAcMode(comboBox_1.getSelectedIndex(), (Aircraft) comboBox.getSelectedItem());
					} catch (Exception e) {
						System.out.println("Incorrect mode or mode comboBox empty");
					}
				}
			}
		});

		// start the discovering of all connected aircraft
		imu.listenAllAc();

		final Sphere s = new Sphere(10, 10, 800);
		final AffichSphere magneto = new AffichSphere(s);
		panel.add(magneto, "mag");
		System.out.println( "" + magneto.getWidth() + " " + magneto.getHeight());
		btnResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imu.stopListenRaw(type, log, ((Aircraft) comboBox.getSelectedItem()).getId());
				new Result("Results",true,log).majResult();
			}
		});

		JPanel accelero = new JPanel();
		panel.add(accelero, "name_5925458635449");
		frmCalibrate.setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { frmCalibrate.getContentPane(), menuSide,
						txtpnChooseAMode, btnNewButton_1, btnNewButton,
						presentIcon, panel_2, txtpnUavsPresent, panel, welcome,
						separator, txtpnFillTheField, lblNewLabel, comboBox,
						lblChooseModeSending, comboBox_1, magneto, accelero }));
		
		//Listeners on button to switch to calibration
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = TypeCalibration.MAGNETOMETER;
				FilterSphere filtre = new FilterSphere(s,40,type);
				Data data = new Data(filtre,type);
				imu.ListenRaw(data, type, log, ((Aircraft) comboBox.getSelectedItem()).getId());
				((CardLayout) panel.getLayout()).show(panel,"mag");
				btnResults.setVisible(true);
			}
		});
	}

	/**
	 * Start the IMU, physical control part (outside GUI)
	 */
	private void startImu() {
		imu = new IMU();
	}
}