import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries; 
import org.jfree.data.time.TimeSeriesCollection; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import org.joda.time.DateTime;

public class ViewHistory extends JFrame {
	
	static int tokenCounter = 0;
	
	public ViewHistory( String title, Child child, Mode mode, DateTime start, int intervals) {
	      super(title);
	      final XYDataset dataset = createDataset(child, mode, start, intervals);         
	      final JFreeChart chart = createChart(dataset, child, mode);         
	      final ChartPanel chartPanel = new ChartPanel( chart ); 
	         
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
	      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	   private XYDataset createDataset(Child child, Mode mode, DateTime start, int intervals) 
	   {
		      final TimeSeries series = new TimeSeries( "Random Data" );
		      		      	
		      	int day = start.getDayOfMonth();
		      	int minute = start.getMinuteOfHour();
		      	int hour = start.getHourOfDay();
		      	int month = start.getMonthOfYear();
		      	int year = start.getYear();
		      	
		      	Minute current = new Minute (minute, hour, day, month, year);
		      	
		      	int counter = 0;
		      	
				try 
				{ 
					for (int i = 0; i < intervals; i++) {
				      	
				      	// generate ALL in one run 
				      	for (Token t: child.getTokens()) {
				      		DateTime time = t.getTimestamp();
				      		if(time.getMinuteOfHour() == current.getMinute() &&
				      			time.getHourOfDay() == current.getHourValue() &&
				      			time.getDayOfMonth() == current.getDay().getDayOfMonth() &&
				      			time.getMonthOfYear() == current.getDay().getMonth() &&
				      			time.getYear() == current.getDay().getYear() &&
				      			t.getType().getClass().equals(mode.getClass())) {
				      			counter++;
				      		}
				      	}
						
						series.add(current, counter);
						current = (Minute) current.next();
					}
				}
				catch ( SeriesException e ) 
				{
				    System.err.println("Error adding to series");
				}


		      return new TimeSeriesCollection(series);
		      
	   }     

	   private JFreeChart createChart( final XYDataset dataset, Child child, Mode mode) 
	   {
	      return ChartFactory.createTimeSeriesChart(             
	      child.getName() + "'s " + mode.toString() + " Tokens", 
	      "Date",              
	      "Number of Tokens",              
	      dataset,             
	      false,              
	      false,              
	      false);
	   }

}
