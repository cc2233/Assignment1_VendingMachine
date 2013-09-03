import java.io.*;
import java.util.*;


public class Dispenser {

	private String m_foodInfoName; //name of food item
	private int m_qty; //quantity
	
	public void Load(Scanner inputStream) throws Exception {
		
		String line = inputStream.nextLine().trim();
		m_foodInfoName = line;
		
		if(m_foodInfoName.length()<1) //Exception for invalid food names
			throw new Exception("Food name too short");
		
		m_qty = Integer.parseInt(inputStream.nextLine().trim());
		if(m_qty<0)
			throw new Exception("Invalid quantity"); //Exception for invalid food quantities
	}
	
	public void Save(PrintWriter outputStream) throws Exception {
		
		outputStream.println(m_foodInfoName);
		outputStream.println(m_qty);	
	}

	public String getFoodName() {
		return m_foodInfoName;
	}
	
	public int getQty() {
		return m_qty;
	}
	
	public int changeQty() throws Exception {
		
		if(m_qty>=1)
			m_qty -= 1;
		else
			throw new Exception("Sold out");
		
		return m_qty;
	}
	
}
