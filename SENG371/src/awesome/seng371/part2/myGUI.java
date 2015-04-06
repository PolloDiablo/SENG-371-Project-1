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
	private static JTabbedPane tabbedPane;
	private static JPanel panel_1;
	private static JPanel panel_2;
	private static JLabel lblNewLabel_2;
	private static JLabel lblNewLabel_11;
	private static JCheckBox chckbxYes_1;
	private static JTextField textField_5;
	private static JTextField textField_6;
	private static JTextField textField_7;
	private static JTextField textField_8;
	private static JTextField textField_9;
	private static JTextField textField_10;
	private static JTextField textField_11;
	private static JCheckBox chckbxYes;
	
	public static void main(String[] args){
		JFrame myGUI = new JFrame();
		myGUI.setTitle("Reddit Parser");
		myGUI.setSize(720, 420);
		myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGUI.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		myGUI.getContentPane().add(tabbedPane);
		
		Panel panel = new Panel();
		tabbedPane.addTab("Monitor Subreddit", null, panel, null);
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
		button.setBounds(285, 204, 131, 22);
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
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Graph Data", null, panel_1, null);
		panel_1.setLayout(null);
		
		lblNewLabel_2 = new JLabel("<html><center>If you haven't yet set up your database, please read the database setup file at ~\\SENG-371-Project-1\\docs\\database-setup.txt</html>");
		lblNewLabel_2.setBounds(10, 11, 579, 34);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Keyword(s) to look for, seperate with spaces");
		lblNewLabel_3.setBounds(10, 56, 274, 14);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Graph name");
		lblNewLabel_4.setBounds(10, 81, 246, 14);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Database url");
		lblNewLabel_5.setBounds(10, 106, 343, 14);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Do you want to include data from the patch notes table?");
		lblNewLabel_6.setBounds(10, 131, 343, 14);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Name of game in database table");
		lblNewLabel_7.setBounds(10, 156, 274, 14);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Start date (epoch time)");
		lblNewLabel_8.setBounds(10, 181, 274, 14);
		panel_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("End date (epoch time)");
		lblNewLabel_9.setBounds(10, 206, 280, 14);
		panel_1.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Time increment between points (epoch time)");
		lblNewLabel_10.setBounds(10, 231, 274, 14);
		panel_1.add(lblNewLabel_10);
		
		lblNewLabel_11 = new JLabel("Connect the points on your graph?");
		lblNewLabel_11.setBounds(10, 256, 274, 14);
		panel_1.add(lblNewLabel_11);

		chckbxYes = new JCheckBox("Yes");
		chckbxYes.setBounds(369, 127, 97, 23);
		panel_1.add(chckbxYes);
		
		chckbxYes_1 = new JCheckBox("Yes");
		chckbxYes_1.setBounds(369, 252, 97, 23);
		panel_1.add(chckbxYes_1);
		
		textField_5 = new JTextField();
		textField_5.setBounds(369, 153, 230, 20);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(369, 53, 230, 20);
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(369, 78, 230, 20);
		panel_1.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(369, 103, 230, 20);
		panel_1.add(textField_8);
		textField_8.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setBounds(369, 178, 230, 20);
		panel_1.add(textField_9);
		textField_9.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setBounds(369, 203, 230, 20);
		panel_1.add(textField_10);
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setBounds(369, 228, 230, 20);
		panel_1.add(textField_11);
		textField_11.setColumns(10);
		
		JButton btnCreateGraphs = new JButton("Create Graphs");
		btnCreateGraphs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String test1 = textField_6.getText();
				String test2 = textField_7.getText();
				String test3 = textField_8.getText();
				boolean test4 = false;
				if(chckbxYes.isSelected()){
					test4 = true;
				}
				String test5 = textField_6.getText();
				long test6 = Long.parseLong(textField_9.getText());
				long test7 = Long.parseLong(textField_10.getText());
				int test8 = Integer.parseInt(textField_11.getText());
				boolean test9 = false;
				if(chckbxYes_1.isSelected()){
					test9 = true;
				}
				GraphCreator_SingleKeyword.createCharts(test1,test2,test3,test4,test5,test6,test7,test8,test9);
			}
		});
		btnCreateGraphs.setBounds(259, 305, 127, 23);
		panel_1.add(btnCreateGraphs);
		
		
		panel_2 = new JPanel();
		tabbedPane.addTab("Display Graphs", null, panel_2, null);
		myGUI.setVisible(true);
	}
}
