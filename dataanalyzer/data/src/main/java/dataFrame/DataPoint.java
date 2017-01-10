package dataFrame;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * 
 * @author Zhaohong Jin
 * The Data Source is Yahoo! Finance
 *
 */
public class DataPoint implements Comparable<DataPoint> {
	
	//features
	public String stockName;
	public String ticker;
	public String date; 
	public double open; //opening price
	public double high; //highest price of the day
	public double low; //lowest price of the day
	public double close; //closing price
	public long volume; //total volume in dollars
	
	public LinkedList<DataPoint> fiveDayList;
	public LinkedList<DataPoint> tenDayList;
	public double movingAverageFiveDay; //moving average in 5 days
	public double movingAverageTenDay; //moving average in 10 days
	public double exponentialMovingAverage; // EMA (t) = EMA (t-1) + alpha * (Price (t) - EMA (t-1)) Where, alpha = 2/ (N+1), Thus, for N=9, alpha = 0.20
	public double rateOfChangeFiveDay; // The ratio of the current price to the price 5 quotes earlier
	public double rateOfChangeTenDay; // The ratio of the current price to the price 10 quotes earlier
	
	
	public final double alpha = 0.20; //hyperparameter
	
	public int label; // 1 if tomorrow's closing price is higher, 0 otherwise
	
	
	
	/**
	 * 
	 * @param stockName
	 * @param ticker
	 */
	public DataPoint(String stockName, String ticker) {
		this.stockName = stockName;
		this.ticker = ticker;
	}
	
	
	/**
	 * update the current five day and ten day moving average up to today
	 * if the list contains less than five data, we still calculate the average 
	 */
	public void setFiveDayMovingAverage() {
		if (fiveDayList != null) {
			int size = fiveDayList.size();
			
			int runningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					runningSum += data.close;
				}
				movingAverageFiveDay = (double) runningSum / (double) size;		
			}		
			
		} else {
			movingAverageFiveDay = close;

		}
	}
	
	/**
	 * update the current five day and ten day moving average up to today
	 * if the list contains less than five data, we still calculate the average 
	 */
	public void setTenYearMovingAverage() {
		if (tenDayList != null) {
			int size = tenDayList.size();
			
			int runningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					runningSum += data.close;
				}
				movingAverageTenDay = (double) runningSum / (double) size;	
				
			}
			
		} else {
			movingAverageTenDay = close;
		}
	}
	
	
	
	/**
	 * 
	 * EMA (t) = EMA (t-1) + alpha * (Price (t) - EMA (t-1))
	 */
	public void setExponentialMovingAverage() {
		if (fiveDayList == null) {
			this.exponentialMovingAverage = 1;
		} else {
			DataPoint prev = fiveDayList.peekLast();
			this.exponentialMovingAverage = prev.exponentialMovingAverage + alpha * (this.close - prev.exponentialMovingAverage);	
		}
	}
	
	
	/**
	 * update the ratio of the current price to the price 5 or 10 quotes earlier
	 * if the five day or ten day data is not available (less than 5 or 10), use the oldest available data
	 */
	public void setRateOfChange() {
		if (fiveDayList != null) {
			DataPoint last = fiveDayList.peekFirst();
			if (last.close != 0) {
				rateOfChangeFiveDay = (close - last.close) / last.close;
			}
		} else {
			rateOfChangeFiveDay = 0;
		}
		
		if (tenDayList != null) {
			DataPoint last = tenDayList.peekFirst();
			if (last.close != 0) {
				rateOfChangeTenDay = (close - last.close) / last.close;
			}
		} else {
		
			rateOfChangeTenDay = 0;
		}
		
	}
	
	/**
	 * 
	 * @param data
	 * insert the data into the five day or ten day list
	 * if the five day or ten day list is full, drop the oldest data
	 */
	public void addFiveDayList(LinkedList<DataPoint> prevList, DataPoint prevData) {
		fiveDayList = prevList;
		if (fiveDayList == null) {
			fiveDayList = new LinkedList<DataPoint>();
		} else {
			if (fiveDayList.size() == 5) {
				fiveDayList.remove(); //remove from the head
			}
		}
		fiveDayList.add(prevData); //insert into the tail
	}
	
	/**
	 * 
	 * @param data
	 * insert the data into the five day or ten day list
	 * if the five day or ten day list is full, drop the oldest data
	 */
	public void addTenDayList(LinkedList<DataPoint> prevList, DataPoint prevData) {
		tenDayList = prevList;
		if (tenDayList == null) {
			tenDayList = new LinkedList<DataPoint>();
		} else {
			if (tenDayList.size() == 10) {
				tenDayList.remove(); //remove from the head
			} 
		}
		tenDayList.add(prevData); //insert into the tail
	}
	
	
	public void setAll() {
		setFiveDayMovingAverage();
		setTenYearMovingAverage();
		setExponentialMovingAverage();
		setRateOfChange();
	}


	
	public int compareTo(DataPoint other) {
		// TODO Auto-generated method stub
		return this.date.compareTo(other.date);
	}
	
	
	
	
	
}
