package ihm;

import imu.IMU;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import calibrate.CalibrateSystem;

public class Result extends JDialog {
	private JTextArea textResult;
	private JButton btnReturn, btnCopy, btnContinue;
	TextTransfer transfer = new TextTransfer();
	private IMU imu;
	private Shell parent;

	public Result(Shell parent, String title, boolean modal, IMU imu) {
		super(parent, title, modal);
		this.parent = parent;
		this.imu = imu;
		this.setSize(550, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		initialise();
	}

	public void initialise() {
		JPanel panel = new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		imu.getLog().print("/home/gui/test.data");
		textResult = new JTextArea();
		textResult.setEditable(false);
		textResult.setLineWrap(true);
		textResult.setBackground(Color.WHITE);
		textResult.setBounds(10, 10, 530, 180);

		panel.add(textResult);

		btnReturn = new JButton("Return Home");
		btnCopy = new JButton("Copy");
		btnContinue = new JButton("Continue");

		btnCopy.setBounds(20, 200, 140, 30);
		btnReturn.setBounds(390, 200, 140, 30);
		btnContinue.setBounds(205, 200, 140, 30);

		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transfer.setClipboardContents(textResult.getText());
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

	public void showResult() {
		this.setVisible(true);
	}

	public void majResult() {
		System.out.println("Debut Maj result");
		textResult.setText("ok");
		new CalibrateSystem(imu.getCalibration(), "/home/gui/paparazzi",
				"/home/gui/test.data", this.textResult).start();
	}

	public String getResult() {
		return textResult.getText();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008983430604669181L;

}
