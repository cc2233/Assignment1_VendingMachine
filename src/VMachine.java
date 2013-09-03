import java.util.*;
import java.io.*;
import java.math.*;


public class VMachine {
	private String m_machineName; //giving each machine a name
	Dispenser[] m_arrayOfDispensers;
	private BigDecimal m_cashBalance; //to show cash balance of a VMachine
	
	public VMachine() //default constructor to set the length of array to zero 
	{
		m_arrayOfDispensers = new Dispenser[0];
	}
	
	public void Load(Scanner inputStream) throws Exception {
		m_machineName = inputStream.nextLine().trim();
		if(m_machineName.length()<1)
			throw new Exception("VMachine name too short");
		
		m_cashBalance = new BigDecimal(inputStream.nextLine().trim());
	
		int numDispensers = Integer.parseInt(inputStream.nextLine().trim()); 
		
		if (numDispensers>=10 && numDispensers<500) //minimum of 10 Dispensers and a reasonable maximum
				m_arrayOfDispensers = new Dispenser[numDispensers];
		else 
			throw new Exception("Invalid numDispensers");
		
		for(int i=0;i<m_arrayOfDispensers.length;i++)
		{
			m_arrayOfDispensers[i] = new Dispenser();
			m_arrayOfDispensers[i].Load(inputStream);
		}	
	}
	
	public void Save(PrintWriter outputStream) throws Exception {
		outputStream.println(m_machineName);
		outputStream.println(m_cashBalance);
		outputStream.println(m_arrayOfDispensers.length);
		for(int i=0;i<m_arrayOfDispensers.length;i++)
		{
			m_arrayOfDispensers[i].Save(outputStream);
		}	
	}
	
	public int getNumberOfDispensers() {
		return m_arrayOfDispensers.length;
	}
	
	public Dispenser getDispenser(int i) {
		return m_arrayOfDispensers[i];
	}
	

	public BigDecimal getCashBalance() {
		return m_cashBalance;
	}
	
	public void addToCashBalance(BigDecimal price) {
		m_cashBalance = m_cashBalance.add(price);
	}
	
	public String getMachineName() {
		return m_machineName;
	}
	
}
