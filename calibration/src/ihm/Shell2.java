package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.CardLayout;

public class Shell2 {

	private JFrame frmCalibrate;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalibrate = new JFrame();
		frmCalibrate.setTitle("Calibrate");
		frmCalibrate.setBounds(100, 100, 675, 550);
		frmCalibrate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalibrate.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel menuSide = new JPanel();
		frmCalibrate.getContentPane().add(menuSide, BorderLayout.WEST);
		menuSide.setBorder(new LineBorder(Color.GRAY));
		menuSide.setLayout(new MigLayout("", "[183px,grow 230]", "[41px][46px][46px][][][][][]"));
		menuSide.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtpnChooseAMode, btnNewButton, btnNewButton_1}));
		
		JTextPane txtpnChooseAMode = new JTextPane();
		txtpnChooseAMode.setAlignmentY(Component.TOP_ALIGNMENT);
		txtpnChooseAMode.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtpnChooseAMode.setEditable(false);
		txtpnChooseAMode.setBackground(UIManager.getColor("Button.background"));
		txtpnChooseAMode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnChooseAMode.setText("Choose a mode of calibration");
		menuSide.add(txtpnChooseAMode, "cell 0 0,grow");
		
		JButton btnNewButton_1 = new JButton("Accelerometers");
		menuSide.add(btnNewButton_1, "cell 0 1,grow");
		
		JButton btnNewButton = new JButton("Magnetometers");
		menuSide.add(btnNewButton, "cell 0 2,grow");
		
		JPanel presentIcon = new JPanel();
		frmCalibrate.getContentPane().add(presentIcon, BorderLayout.NORTH);
		presentIcon.setBorder(new LineBorder(Color.GRAY));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 255, 0));
		
		JTextPane txtpnUavsPresent = new JTextPane();
		txtpnUavsPresent.setEditable(false);
		txtpnUavsPresent.setBackground(UIManager.getColor("Button.background"));
		txtpnUavsPresent.setText("UAV's presence");
		GroupLayout gl_presentIcon = new GroupLayout(presentIcon);
		gl_presentIcon.setHorizontalGroup(
			gl_presentIcon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_presentIcon.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtpnUavsPresent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(541, Short.MAX_VALUE))
		);
		gl_presentIcon.setVerticalGroup(
			gl_presentIcon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_presentIcon.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_presentIcon.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtpnUavsPresent, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14))
		);
		presentIcon.setLayout(gl_presentIcon);
		
		JPanel panel = new JPanel();
		frmCalibrate.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setLayout(new CardLayout(0, 0));
		
		JPanel welcome = new JPanel();
		panel.add(welcome, "name_281529050503524");
		welcome.setLayout(new MigLayout("", "[100px,grow 200][276px,grow 400][100px,grow 200]", "[46px][][41px][46px][][41px][46px]"));
		
		JSeparator separator = new JSeparator();
		welcome.add(separator, "flowx,cell 0 0,alignx left,aligny top");
		
		JTextPane txtpnFillTheField = new JTextPane();
		welcome.add(txtpnFillTheField, "cell 1 0");
		txtpnFillTheField.setEditable(false);
		txtpnFillTheField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnFillTheField.setBackground(UIManager.getColor("Button.background"));
		txtpnFillTheField.setText("Fill the fileds according to the UAV you want to calibrate properties");
		
		JLabel lblNewLabel = new JLabel("Name of the UAV");
		welcome.add(lblNewLabel, "cell 1 2,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox comboBox = new JComboBox();
		welcome.add(comboBox, "cell 1 3,grow");
		
		JLabel lblChooseModeSending = new JLabel("Choose mode sending RAW data");
		lblChooseModeSending.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.add(lblChooseModeSending, "cell 1 5,grow");
		
		JComboBox comboBox_1 = new JComboBox();
		welcome.add(comboBox_1, "cell 1 6,grow");
		
		JPanel magneto = new JPanel();
		panel.add(magneto, "name_282348376234515");
		frmCalibrate.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{welcome}));
	}
}
