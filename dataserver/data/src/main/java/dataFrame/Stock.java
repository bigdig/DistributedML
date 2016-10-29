package dataFrame;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * 
	 * @param entry
	 * add the associated index on a particular day of the stock
	 */
	public void addIndexEntry(String entry, String indexName) {
		String[] strings = entry.split(",");
		String date = strings[0];
		if (dataMap.containsKey(date)) {
			DataPoint data = dataMap.get(date);
			if (indexName.equals("spy")) {
				data.spyClose = Double.parseDouble(strings[4]);
			}
			else if (indexName.equals("dji")) {
				data.djiClose = Double.parseDouble(strings[4]);
			}
			else if (indexName.equals("ixic")) {
				data.ixicClose = Double.parseDouble(strings[4]);
			}
			else if (indexName.equals("tnx")) {
				data.tnxClose = Double.parseDouble(strings[4]);
			}
			else if (indexName.equals("vix")) {
				data.vixClose = Double.parseDouble(strings[4]);	
			}
			dataMap.put(date, data);
		}
	}
	
	public void debug() {
		for (String date : dateList) {
			DataPoint data = dataMap.get(date);
			System.out.println(data.tnxClose);
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
					+ " label: " + data.label + "\n";
		}
		
		return returnString;
	}
	

}
