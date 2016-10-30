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
	
	public double spyClose; //closing price of S&P500 that day
	public double spyMovingAverageFiveDay;
	public double spyMovingAverageTenDay;
	public double spyRateOfChangeFiveDay;
	public double spyRateOfChangeTenDay;
	
	public double djiClose; //closing price of Dow Jones that day
	public double djiMovingAverageFiveDay;
	public double djiMovingAverageTenDay;
	public double djiRateOfChangeFiveDay;
	public double djiRateOfChangeTenDay;
	
	public double ixicClose; //closing price of Nasdaq that day
	public double ixicMovingAverageFiveDay;
	public double ixicMovingAverageTenDay;
	public double ixicRateOfChangeFiveDay;
	public double ixicRateOfChangeTenDay;
	
	public double tnxClose; //closing price of 10-yr bond that day
	public double tnxMovingAverageFiveDay;
	public double tnxMovingAverageTenDay;
	public double tnxRateOfChangeFiveDay;
	public double tnxRateOfChangeTenDay;
	
	public double vixClose; //closing volatility of S&P500 that day
	public double vixMovingAverageFiveDay;
	public double vixMovingAverageTenDay;
	public double vixRateOfChangeFiveDay;
	public double vixRateOfChangeTenDay;
	
	
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
			
			int spyRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					spyRunningSum += data.spyClose;
				}
				spyMovingAverageFiveDay = (double) spyRunningSum / (double) size;		
			}
			
			
			int djiRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					djiRunningSum += data.djiClose;
				}
				djiMovingAverageFiveDay = (double) djiRunningSum / (double) size;		
			}
			
			int ixicRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					ixicRunningSum += data.ixicClose;
				}
				ixicMovingAverageFiveDay = (double) ixicRunningSum / (double) size;		
			}
			
			
			int tnxRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					tnxRunningSum += data.tnxClose;
				}
				tnxMovingAverageFiveDay = (double) tnxRunningSum / (double) size;		
			}
			
			int vixRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : fiveDayList) {
					vixRunningSum += data.vixClose;
				}
				vixMovingAverageFiveDay = (double) vixRunningSum / (double) size;		
			}
			
			
		} else {
			movingAverageFiveDay = close;
			spyMovingAverageFiveDay = spyClose;
			djiMovingAverageFiveDay = djiClose;
			ixicMovingAverageFiveDay = ixicClose;
			tnxMovingAverageFiveDay = tnxClose;
			vixMovingAverageFiveDay = vixClose;
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
			
			int spyRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					spyRunningSum += data.spyClose;
				}
				spyMovingAverageTenDay = (double) spyRunningSum / (double) size;		
			}
			
			
			int djiRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					djiRunningSum += data.djiClose;
				}
				djiMovingAverageTenDay = (double) djiRunningSum / (double) size;		
			}
			
			int ixicRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					ixicRunningSum += data.ixicClose;
				}
				ixicMovingAverageTenDay = (double) ixicRunningSum / (double) size;		
			}
			
			
			int tnxRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					tnxRunningSum += data.tnxClose;
				}
				tnxMovingAverageTenDay = (double) tnxRunningSum / (double) size;		
			}
			
			int vixRunningSum = 0;
			if (size != 0) {
				for (DataPoint data : tenDayList) {
					vixRunningSum += data.vixClose;
				}
				vixMovingAverageTenDay = (double) vixRunningSum / (double) size;		
			}
		} else {
			movingAverageTenDay = close;
			spyMovingAverageTenDay = spyClose;
			djiMovingAverageTenDay = djiClose;
			ixicMovingAverageTenDay = ixicClose;
			tnxMovingAverageTenDay = tnxClose;
			vixMovingAverageTenDay = vixClose;
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
			rateOfChangeFiveDay = (close - last.close) / last.close;
			spyRateOfChangeFiveDay = (spyClose - last.spyClose) / last.spyClose;
			djiRateOfChangeFiveDay = (djiClose - last.djiClose) / last.djiClose;
			ixicRateOfChangeFiveDay = (ixicClose - last.ixicClose) / last.ixicClose;
			tnxRateOfChangeFiveDay = (tnxClose - last.tnxClose) / last.tnxClose;
			vixRateOfChangeFiveDay = (vixClose - last.vixClose) / last.vixClose;
		} else {
			rateOfChangeFiveDay = 1;
			spyRateOfChangeFiveDay = 1;
			djiRateOfChangeFiveDay = 1;
			ixicRateOfChangeFiveDay = 1;
			tnxRateOfChangeFiveDay = 1;
			vixRateOfChangeFiveDay = 1;
			
		}
		
		if (tenDayList != null) {
			DataPoint last = tenDayList.peekFirst();
			rateOfChangeTenDay = (close - last.close) / last.close;
			spyRateOfChangeTenDay = (spyClose - last.spyClose) / last.spyClose;
			djiRateOfChangeTenDay = (djiClose - last.djiClose) / last.djiClose;
			ixicRateOfChangeTenDay = (ixicClose - last.ixicClose) / last.ixicClose;
			tnxRateOfChangeTenDay = (tnxClose - last.tnxClose) / last.tnxClose;
			vixRateOfChangeTenDay = (vixClose - last.vixClose) / last.vixClose;
		} else {
		
			rateOfChangeTenDay = 1;
			spyRateOfChangeTenDay = 1;
			djiRateOfChangeTenDay = 1;
			ixicRateOfChangeTenDay = 1;
			tnxRateOfChangeTenDay = 1;
			vixRateOfChangeTenDay = 1;
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
