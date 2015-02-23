package jeremy.seng371.analytics;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Analyzer {

	private static final long startDate = 1356854400; 	// Dec. 30 2012, i.e. ~start of 1st week of 2013
	//private static final long endDate = 1424678400; 	// Fen, 23 2015
	
	//private static final long startDate = 1388563200; 	//Start of 2014
	private static final long endDate = 1420099200;		//Start of 2015
	
	private static final int secondsInWeek = 60*60*24*7; //604800
	private static final String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=db371project;user=sa;password=sosecure";
	
	//===================================
	//Search settings
	private static final String gameName = "League of Legends";	// Used in the database search
	private static final String gameNameShort = "LOL";			// Reflected in the file name
	
	private static final String term = "shen";					// The term to look for in both the Reddit Posts and Patch Notes
	//===================================
	
	public static void main(String[] args) {	
		
		// Initialize database connection
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
		
		try{
			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			


			// Iterate through each week from start date to end date
			long dateLowerBound = startDate;
			long dateUpperBound = startDate + secondsInWeek;
			
			String output= "";
			
			System.out.println("Performing queries...");
			
			// Each time period corresponds to one row in the resulting data table
			while(dateUpperBound <= endDate){	
				Date sqlDateLowerBound = new Date(dateLowerBound*1000);
				Date sqlDateUpperBound = new Date(dateUpperBound*1000);
				
				// Write the date for this row
				output += sqlDateLowerBound+",";		
				
				//=========================================
				// Look at Reddit Posts
				
				/*
				// Analysis without weightings (just total number of posts per week that include the term)
				sql = "SELECT COUNT(*) AS total FROM RedditPosts WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, gameName);
				stmt.setDate(2, sqlDateLowerBound);
				stmt.setDate(3, sqlDateUpperBound);
				stmt.setString(4, "%"+term+"%");
				stmt.setString(5, "%"+term+"%");
				rs = stmt.executeQuery();
				
				while(rs.next()){
					output += rs.getInt("total") + ",";
				}*/
				
				// Analysis with weightings (takes into account numberOfComments and popularity of posts that include the term)
				sql = "SELECT numberOfComments, popularity FROM RedditPosts WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, gameName);
				stmt.setDate(2, sqlDateLowerBound);
				stmt.setDate(3, sqlDateUpperBound);
				stmt.setString(4, "%"+term+"%");
				stmt.setString(5, "%"+term+"%");
				rs = stmt.executeQuery();
				
				int score = 0; // Score is the "activity" of this topic for this time period
				while(rs.next()){
					if(rs.getInt("numberOfComments")>0){
						score += Math.log10(rs.getInt("numberOfComments"));
					}
					if(rs.getInt("popularity")>0){
						score += Math.log10(rs.getInt("popularity"));
					}
				}
				
				// Write the RedditPost score for this time period
				output += (score + ",");
				
				
				//=========================================
				// Look at Patch Notes
								
				sql = "SELECT COUNT(*) AS total FROM PatchNotes WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, gameName);
				stmt.setDate(2, sqlDateLowerBound);
				stmt.setDate(3, sqlDateUpperBound);
				stmt.setString(4, "%"+term+"%");
				stmt.setString(5, "%"+term+"%");
				rs = stmt.executeQuery();
				
				
				// Write how many (if any) patches were released during this time period
				while(rs.next()){
					if(rs.getInt("total")>0){
						output += rs.getInt("total");
					}
					output+="\n";	
				}
				//=========================================
							
				// Go to the next week
				dateLowerBound += secondsInWeek;
				dateUpperBound += secondsInWeek;
				
			}
			
			
			
			// Write output as csv
			System.out.println("Writing to csv...");
			PrintWriter writer = new PrintWriter("analytics/"+gameNameShort+"-"+term+".csv", "UTF-8");
			writer.write(output);
			writer.close();
			
			//TODO instead of writing to csv, I could probably write to xlsx, and maybe even pre-create the graphs?
			//TODO or can I generate graphs from Java here?
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		System.out.println("Complete.");
		
	}
	
}
