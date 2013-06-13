/**
 * Package IHM contents the interface of our application
 */
package ihm;

import imu.IMU;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import calibrate.CalibrateSystem;

/**
 * Show the result of the calibration in a JDialog
 * 
 * @author Guillaume
 * 
 */
public class Result extends JDialog {
	/**
	 * Where there is the result
	 */
	private JTextArea textResultCopy, textResult;
	/**
	 * Button for different actions : Return home, Copy the result, Continue the
	 * calibration
	 */
	private JButton btnReturn, btnCopy, btnContinue;
	/**
	 * For copy and paste
	 */
	private TextTransfer transfer = new TextTransfer();
	/**
	 * IMU
	 */
	private IMU imu;
	/**
	 * To know where the JDialog must be shown
	 */
	private Shell parent;

	
	
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
	public Result(Shell parent, String title, boolean modal, IMU imu) {
		super(parent, title, modal);
		this.parent = parent;
		this.imu = imu;
		// The size of the JDialog
		this.setSize(550, 420);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		initialise();
	}

	/**
	 * Initialise the JDialog
	 */
	public void initialise() {
		JPanel panel = new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		// The log file
		System.out.println(System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data");
		imu.getLog().print(System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data");
		// Text where the result appears
		textResultCopy = new JTextArea();
		textResultCopy.setEditable(false);
		textResultCopy.setLineWrap(true);
		textResultCopy.setBackground(Color.WHITE);
		textResultCopy.setBounds(10, 120, 530, 210);

		panel.add(textResultCopy);
		
		textResult = new JTextArea();
		textResult.setEditable(false);
		textResult.setBackground(Color.WHITE);
		textResult.setBounds(10, 30, 530, 50);
		panel.add(textResult);
		
		JLabel labelPrec = new JLabel();
		JLabel labelInst = new JLabel();
		labelPrec.setText("Accuracy");
		labelInst.setText("Copy in the Airframe of your drone");
		labelPrec.setBounds(10, 10, 100, 20);
		labelInst.setBounds(10,90,300,20);
		panel.add(labelInst);
		panel.add(labelPrec);
		
		
		btnReturn = new JButton("Return Home");
		btnCopy = new JButton("Copy");
		btnContinue = new JButton("Continue");

		btnCopy.setBounds(20, 350, 140, 30);
		btnReturn.setBounds(390, 350, 140, 30);
		btnContinue.setBounds(205, 350, 140, 30);

		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transfer.setClipboardContents(textResultCopy.getText());
			}
		});

		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				parent.backHome();
			}
		});
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				imu.ListenIMU(imu.getData(), imu.getCalibration(), imu.getLog());
			}
		});

		panel.add(btnCopy);
		panel.add(btnReturn);
		panel.add(btnContinue);

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
		new CalibrateSystem(imu.getCalibration(),
				System.getenv("PAPARAZZI_HOME"),
				System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data", 
				this.textResultCopy,
				this.textResult).start(); //use paparazzi home normally
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
