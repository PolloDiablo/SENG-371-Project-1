package awesome.seng371.part2;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Compares the patch note activity for multiple keywords.
 */
public class GraphCreator_MultiKeyword{

	// graph parameters
	private static final String CHART_X_AXIS_LABEL = "Keyword";
	private static String CHART_Y_AXIS_LABEL; // Different for PatchNote vs. RedditPost graphs
	private static final boolean CHART_SHOW_LEGEND = false;
	private static final boolean CHART_SHOW_TOOLTIPS = false;
	private static final boolean CHART_GENERATE_URLS = false;
	private static final PlotOrientation CHART_ORIENTATION = PlotOrientation.HORIZONTAL;
	private static final Color CHART_PATCH_SERIES_COLOUR = new Color(0,123,76);
	private static final int CHART_WIDTH = 900;
	//Chart height is calculated auto magically
	
	// If this is true, all bars in the bar chart are sorted by value. Otherwise they will appear in the same order given.
	private static final boolean sortKeywordsByValue = false;
		
	/**
	 * This function analyzes the database on either RedditPosts or PatchNotes and creates a graph:<br>
	 * - "analyticsNEW/<i>databaseTableName</i>-multikeyword_barchart.png"<br>
	 * 
	 * Precondition: the <i>databaseTableName</i> table exists in the database
	 * 
	 * @param keywords The comma-separated list of words.
	 * @param databaseURL Connection URL for the JDBC driver
	 * @param databaseTableName Name of the database table ('PatchNotes' or 'RedditPosts') ALWAYS RedditPosts for our GUI
	 * @param gameName Name of the game in the database table (gameName column)
	 * @param queryStartDate Date for the start of the graph (seconds since epoch)
	 * @param queryEndDate Date for the start of the graph (seconds since epoch)
	 */
	public static void createCharts(String keywords, String databaseURL, String databaseTableName, 
			String gameName, long queryStartDate , long queryEndDate ){
	
		//-----------------------------------------------------------------
		// Initialize the chart
		System.out.println("Creating the chart...");

		// Get the list of keywords into a usable form: (splits by comma and removes leading/trailing whitespace)
		List<String> keywordArray = Arrays.asList(keywords.split("\\s*,\\s*"));
		List<Map.Entry<String,Double>> keywordValues= new java.util.ArrayList<>();
		
		// Create the dataset
		DefaultCategoryDataset dataset =  new DefaultCategoryDataset( );  

	    String chartTitle = databaseTableName+ " - Keyword Comparison";

		if(databaseTableName == "PatchNotes"){
			CHART_Y_AXIS_LABEL = "Number of Patch Notes which contain the Keyword";
		}else if(databaseTableName == "RedditPosts"){
			CHART_Y_AXIS_LABEL = "Log of Reddit Popularity of the Keyword";
		}else{
			System.err.println("ERROR: invalid databaseTableName, please use 'PatchNotes' or 'RedditPosts'.");
			return;
		}
	    
	    JFreeChart barChart = ChartFactory.createBarChart(
			chartTitle,
			CHART_X_AXIS_LABEL,
			CHART_Y_AXIS_LABEL,
			dataset,
			CHART_ORIENTATION,
			CHART_SHOW_LEGEND,
			CHART_SHOW_TOOLTIPS, 
			CHART_GENERATE_URLS);
		
	    // Change some display settings of the chart
        CategoryPlot plot = barChart.getCategoryPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, CHART_PATCH_SERIES_COLOUR);
	    
		//-----------------------------------------------------------------
		// Populate the data series for the chart

		// Initialize database connection
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			
			// Iterate through each week from start date to end date
			Date sqlDateLowerBound = new Date(queryStartDate*1000);
			Date sqlDateUpperBound = new Date(queryEndDate*1000);
			
			System.out.println("Performing database queries...");
			for(String keyword : keywordArray){
				
				Double count = getCount(conn, stmt, rs, keyword, databaseTableName, gameName, sqlDateLowerBound, sqlDateUpperBound);
				count =  Math.max(0.0, Math.log10(count));

				// Store data
				keywordValues.add(new AbstractMap.SimpleEntry<>(keyword,count));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (conn != null) conn.close(); } catch (Exception e) {};
		}

		// Sort (or don't) the dataset
		if(sortKeywordsByValue){
			keywordValues.sort(new ValueComparator<String,Double>()); 
		}
		
		// Add the keywords and values to the chart's dataset
		for(Map.Entry<String,Double> entry : keywordValues){
			dataset.addValue( entry.getValue() , "Reddit Activity" , entry.getKey() );
		}

	
		//-----------------------------------------------------------------
		// Output the chart to a PNG file
		System.out.println("Outputting the Chart...");
	    File outputFile = new File("analyticsNEW/" + databaseTableName+"-multikeyword_barchart.png" );                        
	    try {
	    	int chartHeight = 100 + 12*keywordArray.size();
	    	ChartUtilities.saveChartAsPNG( outputFile, barChart, CHART_WIDTH, chartHeight);
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 

		System.out.println("Complete.");
	}
	
	
	
	/**
	 * This function analyzes the database on both RedditPosts AND PatchNotes and creates a graph:<br>
	 * - "analyticsNEW/multikeyword_normalized_difference.png"<br>
	 * 
	 * Precondition: RedditPosts AND PatchNotes tables exist in the database
	 * 
	 * @param keywords The comma-separated list of words.
	 * @param databaseURL Connection URL for the JDBC driver
	 * @param gameName Name of the game in the database table (gameName column)
	 * @param queryStartDate Date for the start of the graph (seconds since epoch)
	 * @param queryEndDate Date for the start of the graph (seconds since epoch)
	 */
	public static void createNormalizedCharts(String keywords, String databaseURL, 
			String gameName, long queryStartDate , long queryEndDate ){
	
		//-----------------------------------------------------------------
		// Initialize the chart
		System.out.println("Creating the chart...");

		// Get the list of keywords into a usable form: (splits by comma and removes leading/trailing whitespace)
		List<String> keywordArray = Arrays.asList(keywords.split("\\s*,\\s*"));
		List<Map.Entry<String,Double>> redditPostKeywordValues= new java.util.ArrayList<>();
		List<Map.Entry<String,Double>> patchNoteKeywordValues= new java.util.ArrayList<>();
		
		// Create the dataset
		DefaultCategoryDataset dataset2 =  new DefaultCategoryDataset( );

	    String chart2Title = "Keyword Comparison: Difference";
	    
	    String chart2YAxisLabel = "Patch Note Occurences (Normalized) - Reddit Popularity (Normalized)";
	    
	    JFreeChart chart2 = ChartFactory.createBarChart(
			chart2Title,
			CHART_X_AXIS_LABEL,
			chart2YAxisLabel,
			dataset2,
			CHART_ORIENTATION,
			CHART_SHOW_LEGEND,
			CHART_SHOW_TOOLTIPS, 
			CHART_GENERATE_URLS);
		
	    // Change some display settings of the charts
        CategoryPlot plot2 = chart2.getCategoryPlot();
        plot2.setRenderer(new DifferenceBarRenderer());
        CategoryItemRenderer renderer2 = plot2.getRenderer();
        renderer2.setSeriesPaint(0, CHART_PATCH_SERIES_COLOUR);
	    
		//-----------------------------------------------------------------
		// Populate the data series for the chart

		// Initialize database connection
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			
			// Iterate through each week from start date to end date
			Date sqlDateLowerBound = new Date(queryStartDate*1000);
			Date sqlDateUpperBound = new Date(queryEndDate*1000);
			
			System.out.println("Performing database queries...");
			for(String keyword : keywordArray){
				
				// Count occurrences
				Double countRedditPosts = getCount(conn, stmt, rs, keyword, "RedditPosts", gameName, sqlDateLowerBound, sqlDateUpperBound);
				Double countPatchNotes = getCount(conn, stmt, rs, keyword, "PatchNotes", gameName, sqlDateLowerBound, sqlDateUpperBound);

				// Store data
				redditPostKeywordValues.add(new AbstractMap.SimpleEntry<>(keyword,countRedditPosts));
				patchNoteKeywordValues.add(new AbstractMap.SimpleEntry<>(keyword,countPatchNotes));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		    try { if (conn != null) conn.close(); } catch (Exception e) {};
		}

		//-----------------------------------------------------------------
		// Setup and output the second chart to a PNG file
		System.out.println("Outputting the second Chart...");
		// Add the keywords and values to the chart's dataset
		
		// First sum total occurences
		double sumRedditPosts = 0;
		double sumPatchNotes = 0;
		for(Map.Entry<String,Double> entry : redditPostKeywordValues){
			sumRedditPosts += entry.getValue();
		}
		for(Map.Entry<String,Double> entry : patchNoteKeywordValues){
			sumPatchNotes += entry.getValue();
		}
		
		// Now add the weighted values to the chart
		for(int i = 0 ; i < redditPostKeywordValues.size() ; ++i){
			double val = patchNoteKeywordValues.get(i).getValue()/sumPatchNotes - redditPostKeywordValues.get(i).getValue()/sumRedditPosts;
			dataset2.addValue(  val, "Reddit Activity" , redditPostKeywordValues.get(i).getKey() );
		}

	    File outputFile2 = new File("analyticsNEW/multikeyword_normalized_difference.png" );                        
	    try {
	    	int chartHeight = 100 + 12*keywordArray.size();
	    	ChartUtilities.saveChartAsPNG( outputFile2, chart2, CHART_WIDTH, chartHeight);
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 
		System.out.println("Complete.");
	}
	
	
	/**Code re-use, yay*/
	private static double getCount(Connection conn, PreparedStatement stmt, ResultSet rs, String keyword, String databaseTableName, 
			String gameName, Date sqlDateLowerBound , Date sqlDateUpperBound) throws SQLException{
		Double count = 0.0;
		// Get outputPatchCount (the number of patches released in this time span)	
		String keywordLIKE = "%[^A-Za-z]"+keyword+"[^A-Za-z]%"; 
		String sql = "SELECT * FROM "+databaseTableName+" WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, gameName);
		stmt.setDate(2, sqlDateLowerBound);
		stmt.setDate(3, sqlDateUpperBound);
		stmt.setString(4, keywordLIKE);
		stmt.setString(5, keywordLIKE);
		rs = stmt.executeQuery();
		while(rs.next()){
			if(databaseTableName == "PatchNotes"){
				++count;
			}else if(databaseTableName == "RedditPosts"){
				if(rs.getInt("numberOfComments")>0){
					count += rs.getInt("numberOfComments");
				}
				if(rs.getInt("popularity")>0){
					count += rs.getInt("popularity");
				}
			}	
		}
		return(count);
	}

	
	
}

// Declare K and V as generic type parameters to ValueComparator
class ValueComparator<K, V extends Comparable<V>> 
// Let your class implement Comparator<T>, binding Map.Entry<K, V> to T
implements Comparator<Map.Entry<K, V>> {
    public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {

        // Compare value 2 to value 1 (REVERSE sort)
        return o2.getValue().compareTo(o1.getValue());
    }   
}

/**
 * Lets us colour the difference graph green and red
 * http://www.jfree.org/phpBB2/viewtopic.php?t=19726
 */
@SuppressWarnings("serial")
class DifferenceBarRenderer extends BarRenderer {
	  public DifferenceBarRenderer() {
	    super();
	  }
	  public Paint getItemPaint(int x_row, int x_col) {
	    CategoryDataset l_jfcDataset = getPlot().getDataset();
	    String l_rowKey = (String)l_jfcDataset.getRowKey(x_row);
	    String l_colKey = (String)l_jfcDataset.getColumnKey(x_col);
	    double l_value  = l_jfcDataset.getValue(l_rowKey, l_colKey).doubleValue();
	    if (l_value < 0.0) { return Color.red; }
	    else { return Color.green; }
	  }
}
