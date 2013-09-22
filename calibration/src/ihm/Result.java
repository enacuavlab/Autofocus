/**
 * Package IHM contents the interface of our application
 */
package ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import common.TypeCalibration;

import calibrate.PrintLog;
import calibrate.CalibrateSystem;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;;
/**
 * Show the result of the calibration in a JDialog
 * 
 * @author Guillaume
 * 
 */
public class Result extends JDialog {
	/**
	 * For copy and paste
	 */
	private TextTransfer transfer = new TextTransfer();

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
	public Result(String title, boolean modal, PrintLog log) {
		super();
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
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 5, 0);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 2;
		getContentPane().add(textPane, gbc_textPane);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		GridBagConstraints gbc_textPane_1 = new GridBagConstraints();
		gbc_textPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_textPane_1.fill = GridBagConstraints.BOTH;
		gbc_textPane_1.gridx = 0;
		gbc_textPane_1.gridy = 4;
		getContentPane().add(textPane_1, gbc_textPane_1);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 5;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
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
	 * Show the result
	 */
	public void showResult() {
		this.setVisible(true);
	}

	/**
	 * Update the result
	 */
	public void majResult() {
		new CalibrateSystem(
				TypeCalibration.MAGNETOMETER, "/home/alinoe/paparazzi",
				"/home/alinoe/paparazzi" + "/var/logs/calibration.data",
				this.textResultCopy, this.textResult).start();
		
		/*new CalibrateSystem(
		TypeCalibration.MAGNETOMETER, System.getenv("PAPARAZZI_HOME"),
		System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data",
		this.textResultCopy, this.textResult).start(); // use paparazzi
		// home normally*/
	}

	/**
	 * To have the result
	 * 
	 * @return
	 */
	public String getResult() {
		return textResult.getText();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008983430604669181L;
}
