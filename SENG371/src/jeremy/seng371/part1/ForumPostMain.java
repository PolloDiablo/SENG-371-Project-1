package jeremy.seng371.part1;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import jeremy.custom.parsers.LOLPatchNoteParser;

public class ForumPostMain {

  private static final String pageURL = "http://www.google.ca/";

	public static void main(String[] args) {
		// Code examples:
		// From: http://hc.apache.org/httpclient-3.x/tutorial.html
		//https://hc.apache.org/httpcomponents-client-4.3.x/quickstart.html
	  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(pageURL);
		CloseableHttpResponse response1 = null;
		try {
			// Get the Html text 
			response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			// do something useful with the response body
			String htmlPageText = EntityUtils.toString(entity1);
			

			System.out.println(htmlPageText);
			//TODO
			Document doc = Jsoup.parse(htmlPageText);
			Elements links = doc.select("a");
			Element head = doc.select("#head").first();
			
			System.out.println(htmlPageText);

			// Ensure entity is fully consumed (proper resource management thing to do)
			EntityUtils.consume(entity1);
			// Close the response
			response1.close();	
			
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
		}
	
	}
}