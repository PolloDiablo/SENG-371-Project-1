package awesome.seng371.part2;

import javax.swing.*;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import awesome.seng371.part2.DailyCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;


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
	private static JPanel panel_3;
	private static JLabel lblNewLabel_2;
	private static JLabel lblNewLabel_11;
	private static JLabel lblNewLabel_3;
	private static JLabel lblNewLabel_4;
	private static JCheckBox chckbxYes_1;
	private static JTextField textField_5;
	private static JTextField textField_6;
	private static JTextField textField_7;
	private static JTextField textField_8;
	private static JTextField textField_9;
	private static JTextField textField_10;
	private static JTextField textField_11;
	private static JTextField textField_12;
	private static JTextField textField_13;
	private static JTextField textField_14;
	private static JTextField textField_15;
	private static JTextField textField_16;
	//private static JCheckBox chckbxYes;
	
	/**If true, use the single-keyword search, else use the multi-keyword search. */
	private static boolean singleKeyword = true;
	private static JButton btnTakeMeTo;
	
	public static void main(String[] args){
		final JFrame myGUI = new JFrame();
		myGUI.setTitle("Reddit Parser");
		myGUI.setSize(720, 420);
		myGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myGUI.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		myGUI.getContentPane().add(tabbedPane);
		
		// -----------------------------------------------------
		// PANEL 0
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
				String[] words = {textField.getText(),textField_1.getText(),textField_2.getText(),textField_3.getText(),textField_4.getText()};  
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
		
		// -----------------------------------------------------
		// PANEL 1	
		panel_1 = new JPanel();
		tabbedPane.addTab("Graph Data", null, panel_1, null);
		panel_1.setLayout(null);
		
		// Radio buttons allow user to choose between single and multi keywords
		lblNewLabel_2 = new JLabel("<html><center>If you haven't yet set up your database, please read the database setup file at ~\\SENG-371-Project-1\\docs\\database-setup.txt</html>");
		lblNewLabel_2.setBounds(10, 11, 579, 34);
		panel_1.add(lblNewLabel_2);
		final JRadioButton radioSingleKeyword = new JRadioButton("Single keyword", true);
		radioSingleKeyword.setBounds(10, 56, 274, 14);
		radioSingleKeyword.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		textField_6.setEditable(true);
	    		textField_7.setEditable(false);
	    		singleKeyword = true;
	    		chckbxYes_1.setEnabled(true);
	    		textField_11.setEnabled(true);
	    	}           
	    });
		panel_1.add(radioSingleKeyword);
		JRadioButton radioMultiKeyword = new JRadioButton("Multiple keywords (comma separated)");
		radioMultiKeyword.setBounds(10, 81, 274, 14);
		radioMultiKeyword.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		textField_6.setEditable(false);
	    		textField_7.setEditable(true);
	    		singleKeyword = false;
	    		chckbxYes_1.setEnabled(false);
	    		textField_11.setEnabled(false);
	    	}           
	    });
		panel_1.add(radioMultiKeyword);
	    ButtonGroup group = new ButtonGroup();
	    group.add(radioSingleKeyword);
	    group.add(radioMultiKeyword);
	    
		lblNewLabel_3 = new JLabel("Loading...");
		//lblNewLabel_3.setBounds(259, 305, 127, 23);
		lblNewLabel_3.setBounds(410, 305, 160, 23);
		panel_1.add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);
		
		JLabel lblNewLabel_5 = new JLabel("Database url");
		lblNewLabel_5.setBounds(10, 131/*106*/, 343, 14);
		panel_1.add(lblNewLabel_5);
		
		//JLabel lblNewLabel_6 = new JLabel("Do you want to include data from the patch notes table?");
		//lblNewLabel_6.setBounds(10, 131, 343, 14);
		//panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Name of game in database table");
		lblNewLabel_7.setBounds(10, 156, 274, 14);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Start date (epoch time)");
		lblNewLabel_8.setBounds(10, 181, 274, 14);
		panel_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("End date (epoch time)");
		lblNewLabel_9.setBounds(10, 206, 280, 14);
		panel_1.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Time increment between points (seconds)");
		lblNewLabel_10.setBounds(10, 231, 274, 14);
		panel_1.add(lblNewLabel_10);
		
		lblNewLabel_11 = new JLabel("Connect the points on your graph?");
		lblNewLabel_11.setBounds(10, 256, 274, 14);
		panel_1.add(lblNewLabel_11);

		//chckbxYes = new JCheckBox("Yes");
		//chckbxYes.setBounds(369, 127, 97, 23);
		//panel_1.add(chckbxYes);
		
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
		textField_7.setEditable(false); // Initially disabled (see radio buttons)
		
		textField_8 = new JTextField();
		textField_8.setBounds(369, 128, 230, 20);
		panel_1.add(textField_8);
		textField_8.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setBounds(369, 178, 230, 20);
		panel_1.add(textField_9);
		textField_9.setColumns(10);
		//textField_9.setText("1388563200");//Default value
		
		textField_10 = new JTextField();
		textField_10.setBounds(369, 203, 230, 20);
		panel_1.add(textField_10);
		textField_10.setColumns(10);
		//textField_10.setText("1420099200");//Default value
		
		textField_11 = new JTextField();
		textField_11.setBounds(369, 228, 230, 20);
		panel_1.add(textField_11);
		textField_11.setColumns(10);
		//textField_11.setText("604800");//Default value
		
		JButton btnCreateGraphs = new JButton("Create Graphs");
		btnCreateGraphs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final String keyword = textField_6.getText();
				final String keywords = textField_7.getText();
				final String databaseURL = textField_8.getText();
				//if(chckbxYes.isSelected()){
				//	includePatchNoteData = true;
				//}
				final String gameName = textField_5.getText();
				final long queryStartDate = Long.parseLong(textField_9.getText());
				final long queryEndDate = Long.parseLong(textField_10.getText());
				final int granularity = Integer.parseInt(textField_11.getText());
				final boolean connectPoints = chckbxYes_1.isSelected();
				
				// Before we try creating graphs, test the database URL
				Connection conn = null;
				try{
					conn = DriverManager.getConnection(databaseURL);
				} catch (Exception e1) {
					lblNewLabel_3.setText("ERROR: Invalid Database url");
					lblNewLabel_3.setVisible(true);
					return;
				} finally {
					try { if (conn != null) conn.close(); } catch (Exception e2) {};
				}
				
				// Also, ensure that the user didn't leave the keyword/keywords field blank
				if( (singleKeyword && keyword.equals(""))|| (!singleKeyword && keywords.equals(""))){
					lblNewLabel_3.setText("ERROR: keyword field blank");
					lblNewLabel_3.setVisible(true);
					return;
				}
				
				// Avoid a filename length error
				if( singleKeyword && keyword.length()>200){
					lblNewLabel_3.setText("ERROR: keyword too long");
					lblNewLabel_3.setVisible(true);
					return;
				}			
				
				// Show a loading message
				lblNewLabel_3.setText("Loading...");
				lblNewLabel_3.setVisible(true);

				// Worker allows us to create the graphs asynchronously
			    SwingWorker<String, Object> worker = new SwingWorker<String, Object>() {
			        @Override
			        protected String doInBackground() throws Exception {
						if(singleKeyword){
							boolean includePatchNoteData = true;
							GraphCreator_SingleKeyword.createCharts(keyword,databaseURL,includePatchNoteData,
									gameName,queryStartDate,queryEndDate,granularity,connectPoints);
						}else{
							String databaseTableName = "RedditPosts";
							GraphCreator_MultiKeyword.createCharts(keywords, databaseURL, 
									databaseTableName, gameName, queryStartDate , queryEndDate);
						}
						return "I don't know what this does? lol";                
			        }
			        @Override
			        protected void done() {
			            try {
			            	// Hide the loading message and automagically switch to the "Display Graphs" tab
			            	lblNewLabel_3.setVisible(false);
			            	// Switch to the new tab automagically
			            	tabbedPane.setSelectedIndex(2);
			            	
			            } catch (Exception e) {
			                //ignore
			            }
			        }
			    };      
			    worker.execute();
			    
			    // Save the parameters from the last successful run
			    // These will autofill into the fields on the next launch
			    try {
			    	// create a new file with an ObjectOutputStream
			    	FileOutputStream out = new FileOutputStream("userdata.tmp");
			    	ObjectOutputStream oout = new ObjectOutputStream(out);
		          
			    	// write something in the file
			    	oout.writeObject(databaseURL);
			    	oout.writeObject(gameName);
			    	oout.writeObject(queryStartDate);
			    	oout.writeObject(queryEndDate);
			    	oout.writeObject(granularity);
			    	oout.writeObject(connectPoints);

			    	// close the stream
			    	oout.close();

			    } catch (Exception ex) {ex.printStackTrace();}
			    
			    
			}
		});
		btnCreateGraphs.setBounds(259, 305, 127, 23);
		panel_1.add(btnCreateGraphs);
		
		// -----------------------------------------------------
		// PANEL 2
		panel_2 = new JPanel();
		tabbedPane.addTab("Display Graphs", null, panel_2, null);
		panel_2.setLayout(null);
		
		btnTakeMeTo = new JButton("Take me to my graphs");
		btnTakeMeTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Desktop desktop = Desktop.getDesktop();
				File directory1 = new File("").getAbsoluteFile();
				File directory2 = new File(directory1,"analyticsNEW");
				try{
					desktop.open(directory2);
				} catch (IllegalArgumentException | IOException iae) {
					System.out.println("File Not Found");
				} 
			}
		});
		btnTakeMeTo.setBounds(186, 68, 347, 169);
		panel_2.add(btnTakeMeTo);
		
		// -----------------------------------------------------
		// PANEL 3
		panel_3 = new JPanel();
		tabbedPane.addTab("Data Scraper", null, panel_3, null);
		panel_3.setLayout(null);
		
		lblNewLabel_4 = new JLabel("<html><center>Use this scraper to populate your database, " +
				"please read the database setup file at ~\\SENG-371-Project-1\\docs\\database-setup.txt</html>");
		lblNewLabel_4.setBounds(10, 11, 579, 34);
		panel_3.add(lblNewLabel_4);
		
		JLabel lblNewLabel_16 = new JLabel("Subreddit");
		lblNewLabel_16.setBounds(10, 103, 343, 14);
		panel_3.add(lblNewLabel_16);
		JLabel lblNewLabel_12 = new JLabel("Database url");
		lblNewLabel_12.setBounds(10, 153/*106*/, 343, 14);
		panel_3.add(lblNewLabel_12);	
		JLabel lblNewLabel_13 = new JLabel("Name of game in database table");
		lblNewLabel_13.setBounds(10, 178, 274, 14);
		panel_3.add(lblNewLabel_13);	
		JLabel lblNewLabel_14 = new JLabel("Start date (epoch time)");
		lblNewLabel_14.setBounds(10, 203, 274, 14);
		panel_3.add(lblNewLabel_14);	
		JLabel lblNewLabel_15 = new JLabel("End date (epoch time)");
		lblNewLabel_15.setBounds(10, 228, 280, 14);
		panel_3.add(lblNewLabel_15);
		
		
		textField_16 = new JTextField();
		textField_16.setBounds(369, 103, 230, 20);
		panel_3.add(textField_16);
		textField_16.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setBounds(369, 153, 230, 20);
		panel_3.add(textField_12);
		textField_12.setColumns(10);
		
		textField_13 = new JTextField();
		textField_13.setBounds(369, 178, 230, 20);
		panel_3.add(textField_13);
		textField_13.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setBounds(369, 203, 230, 20);
		panel_3.add(textField_14);
		textField_14.setColumns(10);
		
		textField_15 = new JTextField();
		textField_15.setBounds(369, 228, 230, 20);
		panel_3.add(textField_15);
		textField_15.setColumns(10);

		JButton btnParseReddit = new JButton("Scrape Subreddit");
		btnParseReddit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
	
				final String subredditName = textField_15.getText();
				final String databaseURL = textField_12.getText();
				final String gameName = textField_13.getText();
				final long queryStartDate = Long.parseLong(textField_14.getText());
				final long queryEndDate = Long.parseLong(textField_15.getText());
				
				// Before we try creating graphs, test the database URL
				Connection conn = null;
				try{
					conn = DriverManager.getConnection(databaseURL);
				} catch (Exception e1) {
					lblNewLabel_3.setText("ERROR: Invalid Database url");
					lblNewLabel_3.setVisible(true);
					return;
				} finally {
					try { if (conn != null) conn.close(); } catch (Exception e2) {};
				}
				RedditParser.getRedditPostData(subredditName, databaseURL, gameName, queryStartDate, queryEndDate);
			}
		});
		btnParseReddit.setBounds(259, 305, 150, 23);
		panel_3.add(btnParseReddit);
		
		// -----------------------------------------------------
		// Now that we have populated the panels, attempt to update fields with stored user values
		try{
	    	// create an ObjectInputStream for the file we created before
	    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userdata.tmp"));
	
	    	// read and print what we wrote before
	    	//panel 1
			textField_8.setText((String) ois.readObject()); //databaseURL
			textField_5.setText((String) ois.readObject()); //gameName
			textField_9.setText(""+(long)ois.readObject());	//queryStartDate
			textField_10.setText(""+(long)ois.readObject());//queryEndDate
			textField_11.setText(""+(Integer)ois.readObject());//granularity
			chckbxYes_1.setSelected((boolean) ois.readObject());//connectPoints
			//panel 3
			textField_12.setText(textField_8.getText()); //databaseURL
			textField_13.setText(textField_5.getText()); //gameName
			textField_14.setText(textField_9.getText());	//queryStartDate
			textField_15.setText(textField_10.getText());//queryEndDate
	
	    	ois.close();
		} catch (Exception ex) {
			// If this failed, do nothing (it probably failed because the userdata.tmp file doesn't exist)
			System.out.println(ex.getMessage());
		}
		
		
		myGUI.setVisible(true);
	}
}
