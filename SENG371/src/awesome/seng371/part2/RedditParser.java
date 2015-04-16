package awesome.seng371.part2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Adapted from the code in part 1
 * 
 * @author Jeremy
 *
 */
public class RedditParser {

	// I increment the date by week, so each week I only get 100 results
	private static final int secondsInWeek = 60*60*24*7; //604800
	
	private static final int minimumScoreThreshold = 1;
	
	// TODO these keywords could be adjusted
	private static final String keywords = "bug||issue||crash";
	
	// Setting a unique HTTP User Agent will make Reddit servers happy. Pay no attention to this :P
	private static final String userAgent = "Java - University project for Software Evolution (by /u/devil_chicken)";
	
	/**
	 * This function will find the bug/issue/crash related posts in the subreddit for the past 2 years.
	 * It will pull data from these posts and store them in the database under the given game name.
	 * 
	 * Note: 	It will not return any posts with a score less than minimumScoreThreshold
	 * 			It will also not get every post if there was >100 for a given week
	 * 
	 * @param parser
	 */
	
	
	/**
	 * This function analyzes the database on either RedditPosts or PatchNotes and creates a graph:<br>
	 * - "analyticsNEW/<i>databaseTableName</i>-multikeyword_barchart.png"<br>
	 * 
	 * Precondition: the <i>databaseTableName</i> table exists in the database
	 * 
	 * @param subredditName Name of the subreddit to scrape.
	 * @param databaseURL Connection URL for the JDBC driver
	 * @param gameName Name of the game in the database table (gameName column)
	 * @param queryStartDate Date for the start of the graph (seconds since epoch)
	 * @param queryEndDate Date for the start of the graph (seconds since epoch)
	 */
	public static void getRedditPostData(String subredditName, String databaseURL, String gameName, long queryStartDate, long queryEndDate){
		System.out.println("Reddit post parsing starting for " + gameName);
		
		// Initialize HTTP client
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		
		// Initialize database connection
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
				
		try {		
			// Setup database connectionS

			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			
			System.out.println("Parsing forum post metadata...");

	
			// Getting top 100 for given time span
			final String formattedKeywords = URLEncoder.encode(keywords,"UTF-8");
			
			String queryStart = "http://www.reddit.com/r/"+subredditName+"/search.json?q=(and+timestamp:";
			String queryEnd = "+title:'"+formattedKeywords+"')&sort=top&restrict_sr=on&syntax=cloudsearch&limit=100";
			
			

			
			// Iterate through each week from start date to end date
			long dateLowerBound = queryStartDate;
			long dateUpperBound = queryStartDate + secondsInWeek;
			
			
			while(dateUpperBound <= queryEndDate){
				// For logging
				Date thisWeek = new Date(dateLowerBound*1000);
				System.out.println("Querying this week: " + (java.sql.Date)thisWeek);
				
				// Construct the URL
				String queryURL = queryStart + dateLowerBound + ".." + dateUpperBound + queryEnd;	
				
				// Get the HTML of the page
				HttpGet httpGet = new HttpGet(queryURL);
				httpGet.setHeader("User-Agent", userAgent);
				response = httpclient.execute(httpGet);
				HttpEntity entity1 = response.getEntity();
				String pageText = EntityUtils.toString(entity1);
				
				try{
					// Get the JSON data
					JSONObject json = new JSONObject(pageText);
			        JSONObject data = json.getJSONObject("data");
			        JSONArray resultList = data.getJSONArray("children");
	
			        //For logging
			        if(resultList.length() >= 100){
				        System.out.println("  This query maxed out... it had " + resultList.length() + " results");
			        }
			        
			        // Iterate through each post for this week
			        for(int i=0; i<resultList.length(); i++) {
			            JSONObject topic = resultList.getJSONObject(i).getJSONObject("data");
	
			            // Get the relevant fields out of the post
			            String permalink = topic.getString("permalink"); 
			            String url = "http://www.reddit.com" + permalink;		            
			            Double createdTime = topic.getDouble("created"); // In seconds
			            Date dateTime = new Date((long)(createdTime*1000)); // Must be in milliseconds		            
			            String title = topic.getString("title");  
			            int numberOfComments = topic.getInt("num_comments");		            
			            int popularity = topic.getInt("score"); 
			            String body = topic.getString("selftext");
	
			            // Don't bother storing data on posts that were downvoted
			            if(popularity < minimumScoreThreshold){
			            	continue;
			            }
			            
						// Check if the row already exists (url is key)
						sql = "SELECT * FROM RedditPosts WHERE url = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, url);
						rs = stmt.executeQuery();
						boolean empty = !rs.next();
						if (empty){					
							// ResultSet is empty => We need to create a new entry for this data
							sql = "INSERT INTO RedditPosts VALUES (?,?,?,?,?,?,?)";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, gameName);
							stmt.setString(2, url);
							stmt.setDate(3, (java.sql.Date)(dateTime));
							stmt.setString(4, title);
							stmt.setInt(5, numberOfComments);
							stmt.setInt(6, popularity);
							stmt.setString(7, body);
							stmt.executeUpdate();
	
						}
						//TODO Super weird lol, if I put this in an else statement, I get a compiler warning
						if (!empty){
							// ResultSet is not empty => We need to update the entry for this data
							// 	... the numberOfComments and popularity may have changed
							// 	Everything else should be the same
							sql = "UPDATE RedditPosts SET numberOfComments = ?, popularity = ? WHERE url = ?";
							stmt = conn.prepareStatement(sql);
							stmt.setInt(1,numberOfComments);
							stmt.setInt(2,popularity);
							stmt.setString(3,url);
							stmt.executeUpdate();
						}
			            
			        }
				}catch(Exception e){
					System.out.println("ERROR: JSON parsing and/or SQL writing failed for the following query string:");
					System.out.println(queryURL);
					System.out.println("You may encounter this error if your searches are too recent (and return empty)...");//TODO
					e.printStackTrace();
				}
				
				// Clean-up the HTTP stuff
				EntityUtils.consume(entity1);
				response.close();	
		        
				// Go to the next week
				dateLowerBound += secondsInWeek;
				dateUpperBound += secondsInWeek;

				// Pause briefly before the next page query, or Reddit gets angry (max 30 requests/minute)
	            Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// The underlying HTTP connection is still held by the response object
			// to allow the response content to be streamed directly from the network socket.
			// In order to ensure correct deallocation of system resources
			// the user MUST call CloseableHttpResponse#close() from a finally clause.
			// Please note that if response content is not fully consumed the underlying
			// connection cannot be safely re-used and will be shut down and discarded
			// by the connection manager. 
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	
		System.out.println("Parsing complete for " + gameName);
	}
	
	
}