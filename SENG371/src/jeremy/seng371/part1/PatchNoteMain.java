/*
Code examples:
	HTTP: https://hc.apache.org/httpcomponents-client-4.3.x/quickstart.html
	JDBC: http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm

Info page for TCP/IP connection to local database:
	http://stackoverflow.com/questions/18981279/the-tcp-ip-connection-to-the-host-localhost-port-1433-has-failed
*/

package jeremy.seng371.part1;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class PatchNoteMain {

	public static class PatchNoteMetaData{
		public final String url;
		public final Date dateTime;
		public final String title;
		
		public PatchNoteMetaData(String url, Date dateTime,String title) {
			this.url = url;
			this.dateTime = dateTime;
			this.title = title;
		}
	}
	
	private static final String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=db371project;user=sa;password=sosecure";
	
	public static void main(String[] args) {	
		// Initialize the generic parsers
		IPatchNoteParser parser1 = (IPatchNoteParser) new LOLPatchNoteParser();
		IPatchNoteParser parser2 = (IPatchNoteParser) new TF2PatchNoteParser();
		IPatchNoteParser parser3 = (IPatchNoteParser) new WOWPatchNoteParser();
		
		getPatchNoteData(parser1);
		getPatchNoteData(parser2);
		getPatchNoteData(parser3);
	}
	
	/** The parser will specify the game name, and where a list of patch notes is located (urls).
	 * 	The parser has a function to extract all metadata from a list of patch notes.
	 * 	The parser has a function to extract the body text from a singular patch note page.
	 * 
	 * 	This function utilizes the above functions and stores the resulting data in a database.
	 * 
	 * Note: this runs in two parts, first the metadata for all patch notes is collected
	 * 			then in part #2, the body of each patch note is collected
	 * 
	 * @param parser
	 */
	public static void getPatchNoteData(IPatchNoteParser parser){
		System.out.println("Patch note parsing starting for " + parser.getGameName());
		
		// Initialize HTTP client
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		
		// Initialize database connection
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
		
		try {		
			// Setup database connection

			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			
			// ALGORITHM PART 1 (populate table of patches):
			System.out.println("Parsing patch note metadata...");
			List<String> rootURLs = parser.getRootPageURLs();			
			for (String rootURL : rootURLs){
				
				// Get the HTML of the root page
				HttpGet httpGet = new HttpGet(rootURL);
				response = httpclient.execute(httpGet); //TODO fix the cookie warning
				HttpEntity entity1 = response.getEntity();
				String htmlPageText = EntityUtils.toString(entity1);
				
				
				// Get the patch metadata from the page
				List<PatchNoteMetaData> patchNoteData = parser.getPatchDetailsFromRoot(htmlPageText);
				
				// Clean-up the HTTP stuff
				EntityUtils.consume(entity1);
				response.close();
				
				// Ensure that each patch note is added to the database
				for (PatchNoteMetaData p : patchNoteData){	
					// Check if the row already exists (url is key)
					sql = "SELECT * FROM PatchNotes WHERE url = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, p.url);
					rs = stmt.executeQuery();
					if (!rs.next()){
						
						// TODO test validity of the url for each patch note before inserting
						
						// ResultSet is empty => We need to create a new entry for this data
						sql = "INSERT INTO PatchNotes VALUES (?,?,?,?,?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, parser.getGameName());
						stmt.setString(2, p.url);
						stmt.setDate(3, (java.sql.Date)(p.dateTime));
						stmt.setString(4, p.title);
						stmt.setString(5, "");
						stmt.executeUpdate();

					}else{
						// Row already exists
						//TODO If new data is different, then update the row
					}	
								
				}
			}
			
			// ALGORITHM PART 2 (get patch bodies):
			System.out.println("Getting patch bodies...");
			sql = "SELECT * FROM PatchNotes WHERE body=? AND gameName = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "");
			stmt.setString(2, parser.getGameName());
			rs = stmt.executeQuery();
			// Iterate through each patch note that does not yet have an associated body
			while(rs.next()){
				// Get the HTML of the page
				String patchNoteURL = rs.getString("url");
				HttpGet httpGet = new HttpGet(patchNoteURL);
				response = httpclient.execute(httpGet);  //TODO fix the cookie warning
				HttpEntity entity1 = response.getEntity();
				String htmlPageText = EntityUtils.toString(entity1);
				
				// Get the body text
				String body = parser.getBodyTextFromPatchURL(htmlPageText);
				
				// Update the database with the body text
				sql = "UPDATE PatchNotes SET body = ? WHERE url = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, body);
				stmt.setString(2, patchNoteURL);
				stmt.executeUpdate();
				
				// Clean-up the HTTP stuff
				EntityUtils.consume(entity1);
				response.close();	
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
	
		System.out.println("Parsing complete for " + parser.getGameName());
	}
	
	
}