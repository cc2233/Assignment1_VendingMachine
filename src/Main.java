import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	
	VMachine m_machinesArray[]; //array of type VMachine
	FoodInfo m_foodInfoArray[]; //array of type FoodInfo

	public static void main(String[] args) {
		
		Main A = new Main(); //creating an instance of Main
		String fileName = "Dispenser.txt";
		try {
			A.Load(fileName);
			A.SimulateCustomers();
			A.Save(fileName);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage() + e.toString());
			System.exit(0);
		}
	}
	public void Load(String fileName) throws Exception {
		
		Scanner inputStream = new Scanner(new File(fileName));
		
		int numFoodInfo = Integer.parseInt(inputStream.nextLine().trim());
		if(numFoodInfo>0)
			m_foodInfoArray = new FoodInfo[numFoodInfo];
		else
			throw new Exception("Invalid numFoodInfo");
		
		System.out.println("Load: " + numFoodInfo + " Food Infos\n");
		
		for(int i=0;i<m_foodInfoArray.length;i++) {
			m_foodInfoArray[i] = new FoodInfo();
			m_foodInfoArray[i].Load(inputStream);
		}
		
		int numMachines = Integer.parseInt(inputStream.nextLine()); //convert line from String to int
		System.out.println("Load: " + numMachines + " machines\n ");
		
		if (numMachines>=2 && numMachines<500) //checking to see if numMachines is at least 2 and a reasonable number
				m_machinesArray = new VMachine[numMachines];
		else 
			throw new Exception("Invalid numMachines");
		
		for(int i=0;i<m_machinesArray.length;i++) {
			m_machinesArray[i] = new VMachine();
			m_machinesArray[i].Load(inputStream);
		}
		
		inputStream.close();
		return;
	}
	
public void Save(String fileName) throws Exception {
		
		PrintWriter outputStream = new PrintWriter(fileName);
		outputStream.println(m_foodInfoArray.length);
		for(int i=0;i<m_foodInfoArray.length;i++){
			m_foodInfoArray[i].Save(outputStream);
		}
		outputStream.println(m_machinesArray.length);

		for(int i=0;i<m_machinesArray.length;i++)
		{
			m_machinesArray[i].Save(outputStream);
		}
		
		outputStream.close();
		return;
	}
	
public FoodInfo FindFoodInfo(String foodName) throws Exception {
	
	for(int i=0;i<m_foodInfoArray.length;i++) {
		if(m_foodInfoArray[i].getFoodName().equals(foodName))
			return m_foodInfoArray[i];
				
	}
	throw new Exception("Food item \"" + foodName + "\" not found"); //if food item:foodName is not found
}

public BigDecimal purchase(BigDecimal money, int dispenserNum, int machineNum) throws Exception {
	
	BigDecimal change = new BigDecimal("0"); //change
	
	VMachine Machine1 = m_machinesArray[machineNum]; //checking for Machine validity
	Dispenser Dispenser1 = Machine1.getDispenser(dispenserNum); //checking for Dispenser validity
	
	if(Dispenser1.getQty()<1) 
		throw new Exception("Cannot purchase from Dispenser: " + dispenserNum);
		
	FoodInfo Food1 = this.FindFoodInfo(Dispenser1.getFoodName());
	String foodName = Dispenser1.getFoodName();
	BigDecimal foodPrice = Food1.getPrice();

	//check if enough money 
	if(foodPrice.compareTo(money)>0)
		throw new Exception("Not enough money!" + "\nThe price of " + foodName  + " is $" + foodPrice + " but you only inserted $" + money + "\n Please take your money.");
	
	change = money.subtract(foodPrice);	
	
	Dispenser1.changeQty();
	Machine1.addToCashBalance(foodPrice);
	
	return change;
}

public void SimulateCustomers() throws Exception {
	
	System.out.println("** START ** Simulating a series of customers ^.^\n");
	

	
	//displaying menu
	for(int i=0;i<m_machinesArray.length;i++) {
		m_machinesArray[i].printMenu();
		System.out.println("\n");
	}
	
	
	
	int numCustomers = (int)(Math.random()*10.0+11); //generating a random number of customers between 11 and 21
	int numCustRejected = 0;
	int decideMachine;
	int decideDispenser;
	BigDecimal subtotal = new BigDecimal("0");
	BigDecimal changetotal = new BigDecimal("0");
	System.out.println("We have " + numCustomers + " customers.\n");
	

	//For each customer, 
	for(int i=0;i<numCustomers;i++) 
	{ 
		System.out.println("=============================================================================================================================\nNow serving Customer #" + (i+1));
		//randomly decide which VMachine
		decideMachine = (int)(Math.random()*m_machinesArray.length);
		VMachine Machine1 = m_machinesArray[decideMachine];
		//and Dispenser to use
		decideDispenser = (int)(Math.random()*Machine1.getNumberOfDispensers());
		Dispenser Dispenser1 = Machine1.getDispenser(decideDispenser);
		
		
		//look up food info
		FoodInfo Food1 = this.FindFoodInfo(Dispenser1.getFoodName());
		//Food1.print();
		System.out.println("Customer #" + (i+1) + " chose " + Machine1.getMachineName() + ", " + "Dispenser: " + Food1.getFoodName());
		
		//If chosen Dispenser is not empty, perform transaction (If it's empty, print a receipt and move on to the next customer)
		if(Dispenser1.getQty()==0)
		{
			System.out.println("Dispenser is empty. Customer leaves without a purchase.");
			numCustRejected += 1;
			continue; //moving on to the next customer
		}
		//get the price
		BigDecimal itemPrice = (Food1.getPrice());
		//customers randomly insert money, ranging between 0 and 5) since all items price<=$5
		BigDecimal randomMoney = BigDecimal.valueOf(((int)(Math.random()*10)+10)*0.25); 
		BigDecimal change = new BigDecimal("0");
		try {
			
			System.out.println("Customer inserts $" + randomMoney);
			
			change = this.purchase(randomMoney, decideDispenser, decideMachine);
			changetotal = changetotal.add(change);
			subtotal  = subtotal.add(itemPrice);
			
			System.out.println("Customer purchased from Dispenser#" + decideDispenser + ".\n RECEIPT: ");
			Food1.print(); //printing food info
			System.out.println("Customer received $" + change + " in change.");
		}
		catch(Exception e) {
			System.out.println("Problem " + e.getMessage()); //printing error message
		}	
	}
		System.out.println("\n** END ** Summary");
		System.out.println("\n  Total of " + numCustomers + " customers visited."); //print total number of customers
		System.out.println("  " + numCustRejected + " of those customer(s) left without making a purchase."); 
		System.out.println("  " + (numCustomers-numCustRejected) + " customer(s) bought something.");
		System.out.println("  " + "A total of $" + subtotal + " were received by the machines.");
		System.out.println("  " + "A total of $" + changetotal + " were given in change.");
	
		for(int i=0;i<m_machinesArray.length;i++)
		{
			String machineName = m_machinesArray[i].getMachineName();
			BigDecimal machineCashBalance = m_machinesArray[i].getCashBalance();
			System.out.println("  Cash balance of " + machineName+ " is $" + machineCashBalance);	
		}
}
}
