package stockdatacollector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dataFrame.Stock;

public class Main {
	
	public static Stock stock;
	
	public static String root = "/Users/zhjin/Desktop/projects/DistributedML/data";
	public static String stockName = "Microsoft";
	public static String ticker = "MSFT";
	public static String[] indexList = {"spy", "dji", "ixic", "tnx", "vix"};

	
	public static void main(String[] args) {
		loadStockData();
		loadIndexData();
		System.out.println(stock);
	}
	
	
	
	public static void loadStockData() {
		stock = new Stock(stockName, ticker);
		
		File file = new File(root + "/msft.csv");
		Scanner c;
		try {
			c = new Scanner(file);
			// skip the title line and the first line
			c.nextLine();
			c.nextLine();
			while (c.hasNextLine()) {
				String line = c.nextLine();
				stock.addStockEntry(line);
				
			}
			c.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void loadIndexData() {
		for (String index : indexList) {
			File file = new File(root + "/" + index + ".csv");
			Scanner c;
			try {
				c = new Scanner(file);
				// skip the title line and the first line
				while (c.hasNextLine()) {
					String line = c.nextLine();
					stock.addIndexEntry(line, index);
				}
				//stock.debug();
				c.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
