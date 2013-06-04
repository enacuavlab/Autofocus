package ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Result extends JDialog {
	private JTextArea textResult;
	private JButton btnEnd, btnCopy;
	TextTransfer transfer= new TextTransfer();
	
	public Result(JFrame parent, String title, boolean modal){
	    super(parent, title, modal);
	    this.setSize(550, 270);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    initialise();
	}
	
	public void initialise(){
		JPanel panel = new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);
		textResult = new JTextArea();
		textResult.setEditable(false);
		textResult.setLineWrap(true);
		textResult.setBackground(Color.WHITE);
		textResult.setBounds(10,10,530,180);
		
		panel.add(textResult);
		
		
		btnEnd = new JButton("End");
		btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				transfer.setClipboardContents(textResult.getText());
			}
		});
		btnCopy.setBounds(340,200,80,30);
		btnEnd.setBounds(450,200,80,30);
		btnEnd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
		panel.add(btnCopy);
		panel.add(btnEnd);
		
	}
	
	public void showResult(){
		this.setVisible(true);
	}
	
	
	public void setResult(String text){
		textResult.setText(text);
	}
	
	public String getResult(){
		return textResult.getText();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6008983430604669181L;
	
}

	