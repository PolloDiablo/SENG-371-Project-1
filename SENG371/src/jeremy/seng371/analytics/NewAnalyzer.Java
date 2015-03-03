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
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Main {

	static final String CHART_X_AXIS_LABEL = "Time (weeks)";
	static final String CHART_Y_AXIS_LABEL = "Log10 of Reddit Activity";
	static final String CHART_REDDIT_SERIES_LABEL = "Reddit Activity";
	static final String CHART_PATCH_SERIES_LABEL = "Patch Releases";
	static final boolean CHART_SHOW_LEGEND = true;
	static final boolean CHART_SHOW_TOOLTIPS = true;
	static final boolean CHART_GENERATE_URLS = false;
	static final int CHART_WIDTH = 800;
	static final int CHART_HEIGHT = 400;
	
	private static final long startDate = 1388563200; 	//Start of 2014
	private static final long endDate = 1420099200;		//Start of 2015
	private static final int secondsInWeek = 60*60*24*7; //604800
	
	public static void main( String[ ] args ){
	
		// Create the data series
		TimeSeries redditActivitySeries = new TimeSeries( CHART_REDDIT_SERIES_LABEL );
		TimeSeries patchNoteSeries = new TimeSeries( CHART_PATCH_SERIES_LABEL );
		
		// Populate the data series
		long dateLowerBound = startDate;
		long dateUpperBound = startDate + secondsInWeek;
		double fakeData1 = 7;
			
		while(dateUpperBound <= endDate){
			Date sqlDateLowerBound = new Date(dateLowerBound*1000);
		
			
			/* Need to convert the date format...
			 * 1. sql Date
			 * 2. util GregorianCalendar
			 * 3. jfree Second
			 */
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(sqlDateLowerBound);
			
			Second currentWeek = new Second(cal.get(Calendar.SECOND),cal.get(Calendar.MINUTE), 
				cal.get(Calendar.HOUR),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR));
			
			// Just put some fake data on to the graph
			// TODO actually do some SQL here
			redditActivitySeries.add(currentWeek , fakeData1*=0.90 );
			patchNoteSeries.add(currentWeek , new Random().nextInt(3) );	
	
			dateLowerBound += secondsInWeek;
			dateUpperBound += secondsInWeek;
		}


		
		// Create the chart's dataset
		TimeSeriesCollection dataset = new TimeSeriesCollection( );    
	    dataset.addSeries( redditActivitySeries );          
	    dataset.addSeries( patchNoteSeries );  
	    
	    // Create the chart
	    String gameName = "League of Legends";
	    String gameNameShort = "LOL";
	    String searchTerm = "ashe";
	    String chartTitle = gameName+ " - "+ searchTerm;
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
            
            // Remove the lines connecting the Patch Note series
            renderer.setSeriesLinesVisible(1, false);
            
            // TODO Can we make the Patch Note series a vertical line?
        }
	    
	    // Output the chart to a PNG file
	    File outputFile = new File(gameNameShort +"-"+searchTerm + ".png" );                        
	    try {
	    	ChartUtilities.saveChartAsPNG( outputFile, timeSeriesChart, CHART_WIDTH, CHART_HEIGHT);
	    } catch (IOException e) {
	    	e.printStackTrace();
		} 
	    

	}
	
}
