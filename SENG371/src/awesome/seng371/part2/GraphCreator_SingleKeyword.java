package awesome.seng371.part2;

/*
 * JFreeChart
 * http://www.jfree.org/jfreechart/download.html
 * - jcommon
 * - jfreechart
 * 
 * Examples:
 * http://www.tutorialspoint.com/jfreechart/jfreechart_xy_chart.htm
 * http://www.tutorialspoint.com/jfreechart/jfreechart_file_interface.htm
 * 
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * Generates a .png XY plot of Reddit activity and patch note mentions vs. time for a given keyword.
 */
public class GraphCreator_SingleKeyword {
	
	// Constant graph parameters
	private static final String CHART_X_AXIS_LABEL = "Time (weeks)";
	private static final String CHART_Y_AXIS_LABEL = "Reddit Activity (Log10)";
	private static final String CHART_REDDIT_SERIES_LABEL = "Reddit Activity";
	private static final Color CHART_REDDIT_SERIES_COLOUR = Color.BLUE;
	private static final String CHART_PATCH_SERIES_LABEL = "Patch Releases";
	private static final Color CHART_PATCH_SERIES_COLOUR = new Color(0,123,76);
	private static final boolean CHART_SHOW_LEGEND = true;
	private static final boolean CHART_SHOW_TOOLTIPS = false;
	private static final boolean CHART_GENERATE_URLS = false;
	private static final int CHART_WIDTH = 900;
	private static final int CHART_HEIGHT = 400;
	
	// If this is true, any time when the Reddit Activity for a week is zero, it will not show on the graph.
	// And lines are drawn on the graph to connect values
	
	/**
	 * This function analyzes the database on either RedditPosts or PatchNotes and creates some graphs:<br>
	 * - "analyticsNEW/<i>keyword</i>-singlekeyword_XYplot.png"<br>
	 * - TODO ? more graphs
	 * 
	 * Precondition: the 'RedditPosts' table exists in the database
	 * 
	 * @param keyword The keyword that will be queried
	 * @param databaseURL Connection URL for the JDBC driver
	 * @param includePatchNoteData Whether or not to use data from the 'PatchNotes' table. ALWAYS false for our GUI
	 * @param gameName Name of the game in the database table (gameName column)
	 * @param queryStartDate Date for the start of the graph (seconds since epoch)
	 * @param queryEndDate Date for the start of the graph (seconds since epoch)
	 * @param granularity Size of time increment between points on the graph
	 * @param connectPoints If true, lines are drawn to connect data points on the graph

	 */
	public static void createCharts(String keyword, String databaseURL, boolean includePatchNoteData, 
			String gameName, long queryStartDate , long queryEndDate, int granularity, boolean connectPoints ){
	
		System.out.println("DEBUG");
		System.out.println("keyword = "+ keyword);
		System.out.println("databaseURL = "+ databaseURL);
		System.out.println("includePatchNoteData = "+ includePatchNoteData);
		System.out.println("gameName = "+ gameName);
		System.out.println("queryStartDate = "+ queryStartDate);
		System.out.println("queryEndDate = "+ queryEndDate);
		System.out.println("granularity = "+ granularity);
		System.out.println("connectPoints = "+ connectPoints);
		
		//-----------------------------------------------------------------
		// Initialize the chart
		System.out.println("Creating the chart...");

		// Create the dataset and data series
		TimeSeriesCollection dataset = new TimeSeriesCollection( );
		TimeSeries redditActivitySeries = new TimeSeries( CHART_REDDIT_SERIES_LABEL );
	    dataset.addSeries( redditActivitySeries );
	    // Note, the patch note series is really a formality (to get something to appear in the legend)
	    // I just draw vertical lines on the graph to represent patch notes :P
		TimeSeries patchNoteSeries = new TimeSeries( CHART_PATCH_SERIES_LABEL );
	    dataset.addSeries( patchNoteSeries ); 
	    
	    String chartTitle = gameName+ " - "+ keyword;
	    JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(
			chartTitle,
			CHART_X_AXIS_LABEL,
			CHART_Y_AXIS_LABEL,
			dataset,
			CHART_SHOW_LEGEND,
			CHART_SHOW_TOOLTIPS, 
			CHART_GENERATE_URLS);
	    
	    // Change some display settings of the chart
        XYPlot plot = (XYPlot) timeSeriesChart.getPlot();
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;   
            // Display the basic data-point shapes on the chart
            renderer.setBaseShapesVisible(true);
            
            // Remove the lines connecting the series
            if(!connectPoints){
            	renderer.setSeriesLinesVisible(0, false);
            }
            
            // Make the first series (Reddit) appear blue :P
            renderer.setSeriesPaint(0, CHART_REDDIT_SERIES_COLOUR);
            renderer.setSeriesShape(0, new Ellipse2D.Double(-2,-2,4,4));
            renderer.setSeriesPaint(1, CHART_PATCH_SERIES_COLOUR);
            renderer.setSeriesShape(1, new Rectangle2D.Double(-6,-1,12,2));
        }
		
		
		//-----------------------------------------------------------------
		// Populate the data series for the chart

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
			long dateLowerBound = queryStartDate;
			long dateUpperBound = queryStartDate + granularity;
			
			System.out.println("Performing database queries...");
			while(dateUpperBound <= queryEndDate){
				Date sqlDateLowerBound = new Date(dateLowerBound*1000);
				Date sqlDateUpperBound = new Date(dateUpperBound*1000);
				
				/* READ ME!
				 * Each iteration of the loop will generate one point in the data series
				 * 		X: the date in jfree second format
				 * 		Y: the data/value
				 */
				Second outputCurrentWeek;
				double outputRedditScore = 0;
		
				/* Get outputCurrentWeek
				 * Need to convert the date format...
				 * 1. sql Date
				 * 2. util GregorianCalendar
				 * 3. jfree Second
				 */
		        Calendar cal = new GregorianCalendar();
		        cal.setTime(sqlDateLowerBound);
		        outputCurrentWeek = new Second(cal.get(Calendar.SECOND),cal.get(Calendar.MINUTE), 
					cal.get(Calendar.HOUR),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
				

				// Get outputRedditScore = log(numberOfComments) + log(popularity)
		        // Match non-alphabet letters on either side, to ensure we don't get false matches (e.g. "ashe" matches "flashes")
		        String keywordLIKE = "%[^A-Za-z]"+keyword+"[^A-Za-z]%"; 
				sql = "SELECT numberOfComments, popularity FROM RedditPosts WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, gameName);
				stmt.setDate(2, sqlDateLowerBound);
				stmt.setDate(3, sqlDateUpperBound);
				stmt.setString(4, keywordLIKE);
				stmt.setString(5, keywordLIKE);
				rs = stmt.executeQuery();	
				while(rs.next()){
					if(rs.getInt("numberOfComments")>0){
						outputRedditScore += rs.getInt("numberOfComments");
					}
					if(rs.getInt("popularity")>0){
						outputRedditScore += rs.getInt("popularity");
					}
				}
				outputRedditScore =  Math.max(0, Math.log10(outputRedditScore));
				
				if(includePatchNoteData){
					// Get outputPatchCount (the number of patches released in this time span)		
					sql = "SELECT COUNT(*) AS total FROM PatchNotes WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, gameName);
					stmt.setDate(2, sqlDateLowerBound);
					stmt.setDate(3, sqlDateUpperBound);
					stmt.setString(4, keywordLIKE);
					stmt.setString(5, keywordLIKE);
					rs = stmt.executeQuery();
					if(rs.next() && rs.getInt("total") > 0){
						// Draw a vertical line if there is a patch
						Marker newMarker = new ValueMarker(cal.getTimeInMillis(),CHART_PATCH_SERIES_COLOUR, new BasicStroke(2)); 
						plot.addDomainMarker(newMarker);
					}
				}

				// Store series data
	           // if(!connectPoints){
	            	redditActivitySeries.add(outputCurrentWeek , outputRedditScore);
	            //}else if( outputRedditScore>=1){
				//	redditActivitySeries.add(outputCurrentWeek , outputRedditScore);
				//}
				
				// Go to the next time period
				dateLowerBound += granularity;
				dateUpperBound += granularity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (conn != null) conn.close(); } catch (Exception e) {};
		}

	
		//-----------------------------------------------------------------
		// Output the chart to a PNG file
		System.out.println("Outputting the Chart...");      
	    File outputFile = new File("analyticsNEW/" +keyword+"-singlekeyword_XYplot.png" );   
	    try {
	    	ChartUtilities.saveChartAsPNG( outputFile, timeSeriesChart, CHART_WIDTH, CHART_HEIGHT);
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 

		System.out.println("Complete.");
	}
	
}
