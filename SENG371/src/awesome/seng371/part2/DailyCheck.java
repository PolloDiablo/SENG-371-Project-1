package awesome.seng371.part2;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;





public class DailyCheck {
    //Uses current time and previous 24 hours for checking post history
    static java.util.Date date = new java.util.Date();
    public final static long endTime = date.getTime() / 1000;
    public static long startTime = endTime - 86400;
    
    //TODO Like in previous, keywords could be adjusted
    private static String keywords = "bug||issue||crash||Bugged";
    
    //TODO Change this value for the searched subreddit
    private static int minimumScoreThreshold = 300;

    // Setting a  unique HTTP User Agent will make Reddit servers happy
    //TODO set your username then add a semicolon :P
    private static String userAgent = "Java - University project for Software Evolution (by /u/<USERNAME_HERE>)";
    
    // This is the username and password of the e-mail the update will be sent to
    public static String username = "371redditparser@gmail.com";
    
    
    
    //method to send actual information out
    public static void sendEmail(String message){
    	
    	//Connect to the mail server 
    	String host = "smtp.gmail.com";
    	Properties properties = System.getProperties();
    	Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    	Session session = Session.getInstance(properties,
    	new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("371redditparser@gmail.com","thepasswordispassword");
            }
          });
    	
    	//set up email and send it
    	try{
    		MimeMessage defaultMessage = new MimeMessage(session);
    		defaultMessage.setFrom(new InternetAddress("371redditparser@gmail.com"));
    		defaultMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
    		defaultMessage.setSubject("Important bug post on reddit");
    		defaultMessage.setText(message);
    		Transport t = session.getTransport("smtps");
    	    try {
    	    	t.connect(host, "371redditparser@gmail.com", "thepasswordispassword");
    	    	t.sendMessage(defaultMessage, defaultMessage.getAllRecipients());
    	    	System.out.println("Message sent successfully..");
    	    } finally {
    	    	t.close();
    	    }
    	} catch(MessagingException mex){
    		mex.printStackTrace();
    	}
    }
    
    public static void getRedditPostData(String subreddit){
        System.out.println("Reddit post parsing starting for /r/" + subreddit);
        
        // Initialize HTTP client
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        
        try{
            final String formattedKeywords = URLEncoder.encode(keywords,"UTF-8");

            // Construct the URL
            String queryURL = "http://www.reddit.com/r/"+subreddit+"/search.json?q=(and+timestamp:"+startTime+".."+endTime+"+title:'"+formattedKeywords+"')&sort=top&restrict_sr=on&syntax=cloudsearch&limit=100";    
            
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

                for(int i=0; i<resultList.length(); i++) {
                    JSONObject topic = resultList.getJSONObject(i).getJSONObject("data");

                    // Get the relevant fields out of the post, excluding posted time since it's useless for this
                    String permalink = topic.getString("permalink"); 
                    String url = "http://www.reddit.com" + permalink;
                    String title = topic.getString("title");  
                    int numberOfComments = topic.getInt("num_comments");                    
                    int popularity = topic.getInt("score"); 
                    String body = topic.getString("selftext");

                    // Checks if post is noteworthy, otherwise disregards it
                    if(popularity > minimumScoreThreshold){
                        String importantPost = "There is an important post at " + url + " about " + title + " - " + body + " with " + numberOfComments + " coments.";
                    	sendEmail(importantPost);                        
                    }
                    else{
                        continue;
                    }
                    
                    
                }
            }
            catch(Exception e){
                System.out.println("JSON parsing failed for the following query string:");
                System.out.println(queryURL);
                System.out.println("Start: " +startTime+" End: " +endTime);
                e.printStackTrace();
            }
            // Clean-up the HTTP stuff
            EntityUtils.consume(entity1);
            response.close();    

            // only querying once
            // Pause briefly before the next page query, or Reddit gets angry (max 30 requests/minute)
            //Thread.sleep(2000);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager. 
            try {
                httpclient.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void main(String args[]){
        getRedditPostData(args[1]);
        keywords = args[0];
        minimumScoreThreshold = Integer.parseInt(args[2]);
        userAgent = args[3];
        username = args[4];
    }
}
