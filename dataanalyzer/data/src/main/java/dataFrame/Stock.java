package dataFrame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author zhjin
 *
 */
public class Stock {
	
	public String name;
	public String ticker;
	public ArrayList<String> dateList;
	public HashMap<String, DataPoint> dataMap;
	
	/**
	 * 
	 * @param name
	 * @param ticker
	 */
	public Stock(String name, String ticker) {
		this.name = name;
		this.ticker = ticker;
		this.dateList = new ArrayList<String>();
		this.dataMap = new HashMap<String, DataPoint>();
	}
	
	/**
	 * 
	 * @param entry
	 * read the stock information from the csv file from Yahoo! Finance
	 */
	public void addStockEntry (String entry) {
		DataPoint data = new DataPoint(this.name, this.ticker);
		String[] strings = entry.split(",");
		
		String date = strings[0];
		data.date = date;
		data.open = Double.parseDouble(strings[1]);
		data.high = Double.parseDouble(strings[2]);
		data.low = Double.parseDouble(strings[3]);
		data.close = Double.parseDouble(strings[4]);
		data.volume = Long.parseLong(strings[5]);
		data.label = Integer.parseInt(strings[7]);
		

		dateList.add(date);
		dataMap.put(date, data);
		
		
	}
	
	
	/**
	 * calculate average, ratio, etc associated with each dataPoint
	 */
	public void setParameters() {
		int prev = 0;
		int curr = 1;
		
		//first need to define some base cases for the first data point
		//for our running average
		String firstDate = dateList.get(0); 
		DataPoint firstData = dataMap.get(firstDate);
		firstData.setAll();
		dataMap.put(firstDate, firstData);
		
		while (curr < dateList.size()) {
			String currDate = dateList.get(curr);
			String prevDate = dateList.get(prev);
			DataPoint currData = dataMap.get(currDate);
			DataPoint prevData = dataMap.get(prevDate);
			
			currData.addFiveDayList(prevData.fiveDayList, prevData);
			currData.addTenDayList(prevData.tenDayList, prevData);
			currData.setAll();
			
			dataMap.put(currDate, currData);
			
			prev += 1;
			curr += 1;
		}
	}
	

	
	
	/**
	 * overwrite toString method
	 */
	public String toString() {
		String returnString = "";
		returnString += "Stock name: " + name + "\n";
		returnString += "Ticker: " + ticker + "\n";
		
		for (String date : dateList) {
			DataPoint data = dataMap.get(date);
			returnString += "date: " + data.date + " open: " + data.open + " high: " + data.high 
					+ " low: " + data.low + " close: " + data.close + " volume: " + data.volume 
					+ " spyClose: " + data.spyClose + " djiClose: " + data.djiClose + " ixicClose: " 
					+ data.ixicClose + " tnxClose: " + data.tnxClose + " vixClose: " + data.vixClose 
					+ " movingAverageFiveDay: " + data.movingAverageFiveDay + " label: " + data.label + "\n";
		}
		
		return returnString;
	}
	

}
