/**
 * Package IHM contents the interface of our application
 */
package ihm;

import imu.IMU;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import calibrate.CalibrateSystem;
import calibrate.PrintLog;

import common.TypeCalibration;
/**
 * Show the result of the calibration in a JDialog
 * 
 * @author Guillaume
 * 
 */
public class Result extends JDialog {

	/**Used to command for the listeners
	 * 
	 */
	final private IMU imu;
	
	/**
	 * 
	 * @param parent
	 *            the shell
	 * @param title
	 *            title of the JDialog
	 * @param modal
	 * @param imu
	 *            the imu
	 */
	public Result(String title, boolean modal, PrintLog log, IMU imu) {
		super();
		this.imu = imu;
		this.setTitle("Results");
		// The size of the JDialog
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		initialise(log);
		this.setPreferredSize(getMaximumSize());
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {30, 40, 120, 40, 120, 40, 0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Calibration results");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JTextArea textPaneResults = new JTextArea();
		textPaneResults.setEditable(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 2;
		getContentPane().add(textPaneResults, gbc_textPane);
		
		JLabel lblNewLabel_1 = new JLabel("Calibration results accuracy");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JTextArea textPaneAccuracy = new JTextArea();
		textPaneAccuracy.setEditable(false);
		GridBagConstraints gbc_textPane_1 = new GridBagConstraints();
		gbc_textPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_textPane_1.fill = GridBagConstraints.BOTH;
		gbc_textPane_1.gridx = 0;
		gbc_textPane_1.gridy = 4;
		getContentPane().add(textPaneAccuracy, gbc_textPane_1);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 5;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JButton btnNewButton_1 = new JButton("Continue");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Home");
		panel.add(btnNewButton);
		
		//Update the results displayed
		new CalibrateSystem(
				TypeCalibration.MAGNETOMETER, "/home/alinoe/paparazzi",
				"/home/alinoe/paparazzi" + "/var/logs/calibration.data",
				textPaneResults, textPaneAccuracy).start();
		
		/*new CalibrateSystem(
		TypeCalibration.MAGNETOMETER, System.getenv("PAPARAZZI_HOME"),
		System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data",
		this.textResultCopy, this.textResult).start(); // use paparazzi
		// home normally*/
		
		this.setVisible(true);
	}

	/**
	 * Initialise the JDialog
	 */
	public void initialise(PrintLog log) {
		// The log file
			log.print("/home/alinoe/paparazzi" + "/var/logs/calibration.data");

	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 6008983430604669181L;
}
