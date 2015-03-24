package awesome.seng371.part2;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import awesome.seng371.part2.DailyCheck;

public class myGUI {
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JLabel lblNewLabel_1;
	private static JLabel lblAddTheScore;
	private static JLabel lblAddYourReddit;
	private static JLabel lblAddYourGmail;
	
	public static void main(String[] args){
		JFrame myGUI = new JFrame();
		myGUI.setTitle("Reddit Parser");
		myGUI.setSize(620, 313);
		myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGUI.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		Panel panel = new Panel();
		myGUI.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setBounds(374, 39, 220, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(374, 8, 220, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(374, 132, 220, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		Button button = new Button("Monitor the subreddit");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DailyCheck geeg = new DailyCheck();
				String[] words = {textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText()};  
				geeg.main(words);
			}
		});
		button.setBounds(204, 223, 131, 22);
		panel.add(button);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(374, 70, 220, 20);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(374, 101, 220, 20);
		panel.add(textField_4);
		
		JLabel lblNewLabel = new JLabel("Add Keywords to track here(e.g. bug||issue)");
		lblNewLabel.setBounds(10, 11, 325, 14);
		panel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Add the subreddit you want to track");
		lblNewLabel_1.setBounds(10, 42, 325, 14);
		panel.add(lblNewLabel_1);
		
		lblAddTheScore = new JLabel("Add the upvote threshhold ");
		lblAddTheScore.setBounds(10, 73, 325, 14);
		panel.add(lblAddTheScore);
		
		lblAddYourReddit = new JLabel("Add your reddit username");
		lblAddYourReddit.setBounds(10, 104, 325, 14);
		panel.add(lblAddYourReddit);
		
		lblAddYourGmail = new JLabel("Add the address you want the email sent to");
		lblAddYourGmail.setBounds(10, 135, 325, 14);
		panel.add(lblAddYourGmail);
		myGUI.setVisible(true);
	}
}
