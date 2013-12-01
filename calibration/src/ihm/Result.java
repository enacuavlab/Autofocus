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
import data.Data;
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
	private Data data;
	private TypeCalibration type = TypeCalibration.MAGNETOMETER;
	private PrintLog log;
	private int idDrone;
	private JTextArea textPaneAccuracy;
	private JTextArea textPaneResults;
	
	/** Builder
	 * @param title
	 *            title of the JDialog
	 * @param modal
	 * @param imu
	 *            the imu
	 */
	public Result(String title, boolean modal, final PrintLog log, final IMU imu) {
		super();
		this.log = log;
		this.setTitle("Results");
		// The size of the JDialog
		this.setBounds(100, 100, 400, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
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
		
		textPaneResults = new JTextArea();
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
		
		textPaneAccuracy = new JTextArea();
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
		
		this.setVisible(false);
		
		btnNewButton_1.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					imu.ListenRaw(data, type, log, idDrone);
			}
		});

	}

	public void getCalib() {
		this.setVisible(true);
		//Update the results displayed
		log.print(System.getenv("PAPARAZZI_HOME") + "/var/logs/calibration.data");
		new CalibrateSystem(
				type, System.getenv("PAPARAZZI_HOME"),
				System.getenv("PAPARAZZI_HOME")+"/var/logs/calibration.data",
				textPaneResults, textPaneAccuracy);
	}

	/**set the data in order to be able to begin the raw collect again*/
	public void setData(Data d) {
		this.data = d;
	}
	
	/**set the type of the current calibration*/
	public void setType(TypeCalibration t) {
		this.type = t;
	}
	
	public void setId(int id) {
		this.idDrone = id;
	}
	
	public static void main(String[] args) {
		new Result("test", true, new PrintLog(), new IMU()).setVisible(true);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6008983430604669181L;
}
