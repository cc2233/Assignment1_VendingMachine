import java.util.*;
import java.io.*;
import java.math.*;


public class FoodInfo {
	
	private String m_name;
	private BigDecimal m_price;
	private String m_nutrition;

	public void Load(Scanner inputStream) throws Exception {
		
		String line = inputStream.nextLine();
		m_name = line.trim();
		
		if(m_name.length()<1) //Exception for invalid food names
			throw new Exception("Food name too short");
		
		System.out.println("Loading food information: " + m_name);
		
		m_price = new BigDecimal(inputStream.nextLine().trim());
		BigDecimal zero = new BigDecimal(0);
		if(m_price.compareTo(zero) < 0)
			throw new Exception("Invalid price"); //Exception for invalid food price
		
		System.out.println(" My price is: " + m_price);
		
		m_nutrition = inputStream.nextLine().trim();
		if(m_nutrition.length()<1) //Exception for invalid nutrition info
			throw new Exception("Invalid nutrition information");
		
		System.out.println(" My nutrition info: " + m_nutrition + "\n");
	}
	
	public void Save(PrintWriter outputStream) throws Exception {
		outputStream.println(m_name);
		outputStream.println(m_price);
		outputStream.println(m_nutrition);
	}
	
	public String getFoodName() {
		return m_name;
	}
	
	public BigDecimal getPrice() {
		return m_price;
	}
	
	public void print() 
	{
		System.out.println("Food name is " + m_name);
		System.out.println("Price is $" + m_price);
		System.out.println("Nutrition information: " + m_nutrition);
	}

}
