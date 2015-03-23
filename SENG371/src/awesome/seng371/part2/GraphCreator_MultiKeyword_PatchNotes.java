package awesome.seng371.part2;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Compares the patch note activity for multiple keywords.
 * Generates: piechart and barchart
 * 
 * 
TODO
(make these the same file, just with an option of which table to look in)
- PatchNotes
-- wordcloud, piechart
- RedditPost
-- wordcloud, piechart

(separate file)
- Both
-- stacked column chart
-- normalized difference: column chart
 * 
 */
public class GraphCreator_MultiKeyword_PatchNotes {

	
	//-----------------------------------------------------------------
	//TODO VARIABLE SETTINGS HERE
	
	// The term to look for in the Patch Notes
	
	//All the league champs as of March 2015
	/*private static final String keywords = "Aatrox,Ahri,Akali,Alistar,Amumu,Anivia,Annie,Ashe,Azir,Bard,Blitzcrank," +
			"Brand,Braum,Caitlyn,Cassiopeia,Cho'Gath,Corki,Darius,Diana,Draven,Elise,Evelynn,Ezreal,Fiddlesticks,Fiora," +
			"Fizz,Galio,Gangplank,Garen,Gnar,Gragas,Graves,Hecarim,Heimerdinger,Irelia,Janna,Jarvan,Jax,Jayce,Jinx,Kalista," +
			"Karma,Karthus,Kassadin,Katarina,Kayle,Kennen,Kha'Zix,Kog'Maw,LeBlanc,Lee,Leona,Lissandra,Lucian,Lulu,Lux,Malphite,Malzahar," +
			"Maokai,Miss Fortune,Mordekaiser,Morgana,Mundo,Nami,Nasus,Nautilus,Nidalee,Nocturne,Nunu,Olaf,Orianna,Pantheon,Poppy,Quinn," +
			"Rammus,Rek'Sai,Renekton,Rengar,Riven,Rumble,Ryze,Sejuani,Shaco,Shen,Shyvana,Singed,Sion,Sivir,Skarner,Sona,Soraka,Swain," +
			"Syndra,Talon,Taric,Teemo,Thresh,Tristana,Trundle,Tryndamere,Twisted Fate,Twitch,Udyr,Urgot,Varus,Vayne,Veigar,Vel'Koz,Vi,Viktor," +
			"Vladimir,Volibear,Warwick,Wukong,Xerath,Xin Zhao,Yasuo,Yi,Yorick,Zac,Zed,Ziggs,Zilean,Zyra";*/
	
	// Champs released before 2014
	private static final String keywords = "Aatrox,Ahri,Akali,Alistar,Amumu,Anivia,Annie,Ashe,Blitzcrank,Brand,Caitlyn," +
			"Cassiopeia,Cho'Gath,Corki,Darius,Diana,Draven,Elise,Evelynn,Ezreal,Fiddlesticks,Fiora,Fizz,Galio,Gangplank," +
			"Garen,Gragas,Graves,Hecarim,Heimerdinger,Irelia,Janna,Jarvan,Jax,Jayce,Jinx,Karma,Karthus,Kassadin,Katarina,Kayle," +
			"Kennen,Kha'Zix,Kog'Maw,LeBlanc,Lee,Leona,Lissandra,Lucian,Lulu,Lux,Malphite,Malzahar,Maokai,Miss Fortune,Mordekaiser," +
			"Morgana,Mundo,Nami,Nasus,Nautilus,Nidalee,Nocturne,Nunu,Olaf,Orianna,Pantheon,Poppy,Quinn,Rammus,Renekton,Rengar,Riven," +
			"Rumble,Ryze,Sejuani,Shaco,Shen,Shyvana,Singed,Sion,Sivir,Skarner,Sona,Soraka,Swain,Syndra,Talon,Taric,Teemo,Thresh,Tristana," +
			"Trundle,Tryndamere,Twisted Fate,Twitch,Udyr,Urgot,Varus,Vayne,Veigar,Vi,Viktor,Vladimir,Volibear,Warwick,Wukong,Xerath," +
			"Xin Zhao,Yasuo,Yi,Yorick,Zac,Zed,Ziggs,Zilean,Zyra";
	
	private static final String gameName = "League of Legends";	// Used in the database search
	private static final String gameNameShort = "LOL";			// Reflected in the file name
	
	
	/* Start of 2013 = 1357027200
	 * Start of 2014 = 1388563200
	 * Start of 2015 = 1420099200
	 */
	private static final long startDate = 1388563200;
	private static final long endDate = 1420099200;
	
	private static final String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=db371project;user=sa;password=sosecure";
	
	// If this is true, all bars in the bar chart are sorted by value. Otherwise they will appear in the same order given.
	private static final boolean sortKeywordsByValue = true;
	
	
	//-----------------------------------------------------------------
	
	// Constant graph parameters
	private static final String CHART_X_AXIS_LABEL = "Keyword";
	private static final String CHART_Y_AXIS_LABEL = "Number of Patch Notes which contain Keyword";
	private static final boolean CHART_SHOW_LEGEND = false;
	private static final boolean CHART_SHOW_TOOLTIPS = false;
	private static final boolean CHART_GENERATE_URLS = false;
	private static final PlotOrientation CHART_ORIENTATION = PlotOrientation.HORIZONTAL;
	private static final Color CHART_PATCH_SERIES_COLOUR = new Color(0,123,76);
	private static final int CHART_WIDTH = 900;
	//Chart height is calculated auto magically
	
	public static void main( String[] args ){
	
		//-----------------------------------------------------------------
		// Initialize the chart
		System.out.println("Creating the chart...");

		// Get the list of keywords into a usable form:
		List<String> keywordArray = Arrays.asList(keywords.split("\\s*,\\s*"));
		List<Map.Entry<String,Integer>> keywordValues= new java.util.ArrayList<>();
		
		// Create the dataset
		DefaultCategoryDataset dataset =  new DefaultCategoryDataset( );  

	    String chartTitle = gameName+ " - Keyword Comparison";
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
		String sql;
		
		try{
			System.out.println("Connecting to database...");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(databaseURL);
			
			// Iterate through each week from start date to end date
			Date sqlDateLowerBound = new Date(startDate*1000);
			Date sqlDateUpperBound = new Date(endDate*1000);
			
			System.out.println("Performing database queries...");
			for(String keyword : keywordArray){

				Integer count = 0;
				
				// Get outputPatchCount (the number of patches released in this time span)	
		        String keywordLIKE = "%[^A-Za-z]"+keyword+"[^A-Za-z]%"; 
				sql = "SELECT COUNT(*) AS total FROM PatchNotes WHERE gameName = ? AND dateTime >= ? and dateTime < ? AND (body LIKE ? OR title LIKE ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, gameName);
				stmt.setDate(2, sqlDateLowerBound);
				stmt.setDate(3, sqlDateUpperBound);
				stmt.setString(4, keywordLIKE);
				stmt.setString(5, keywordLIKE);
				rs = stmt.executeQuery();
				if(rs.next()){
					count = rs.getInt("total");
				}

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
			keywordValues.sort(new ValueComparator<String,Integer>()); 
		}
		
		// Add the keywords and values to the chart's dataset
		for(Map.Entry<String,Integer> entry : keywordValues){
			dataset.addValue( entry.getValue() , "Reddit Activity" , entry.getKey() );
		}

	
		//-----------------------------------------------------------------
		// Output the chart to a PNG file
		System.out.println("Outputting the Chart...");
	    File outputFile = new File("analyticsNEW/" + gameNameShort +"-MULTI.png" );                        
	    try {
	    	int chartHeight = 50 + 15*keywordArray.size();
	    	ChartUtilities.saveChartAsPNG( outputFile, barChart, CHART_WIDTH, chartHeight);
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 

		System.out.println("Complete.");
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
