package uk.co.o2.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.o2.exception.CustomerNotFoundException;
import uk.co.o2.vo.Customer;

public class CustomerDAO {
	
	/**
	 * Private constructor to avoid instance creation
	 */
	private CustomerDAO()
	{
		
	}
	
	private static String firstName[]={"Chad ","Johnathan","Early","Will","Chris","Fernando","Ndamukong","Colt","Arrelious","Roman","Levi"};
	private static String lastName[]={"Ochocinco ","Joseph","Doucet","Smith","Cooley","Velasco","Suh","McCoy","Benn","Harper","Brown"};
	private static HashMap<String, Customer> customersMap = new HashMap<String, Customer>();
	
	private static int customerID = 0;
	
	private static String EMPTY_STRING = "";
	
	static {
		for (; customerID < 10; customerID ++)
			customersMap.put(customerID + EMPTY_STRING,
					getCustomerVO());
	}
	
	/**
	 * Constructs customer vo
	 * @return CustomerVO
	 */
	private static Customer getCustomerVO() {
		Customer customer = new Customer();
		customer.setCustomerId(customerID+EMPTY_STRING);
		customer.setCustomerType("PREPAY");
		customer.setSimNo("44882291314"+customerID);
		customer.setFirstName(firstName[customerID]);
		customer.setLastName(lastName[customerID]);
		customer.setTitle("Mr");

		return customer;
	}
	
	/**
	 * Returns customer details for the given customer ID
	 * @param customerID : The customerID
	 * @return
	 */
	public static Customer getCustomerDetail(String customerID) throws CustomerNotFoundException
	{
		Customer custvo =customersMap.get(customerID); 
		if(null == custvo)
		{
			throw new CustomerNotFoundException("Customer Not found");
		}
		return custvo; 
	}
	
	public static List<Customer> getCustomerList()
	{
		ArrayList<Customer> customersList = new ArrayList<Customer>();
		customersList.addAll(customersMap.values());
		
		return customersList;
	}
	
	public static void addCustomer(Customer customer)
	{
		customer.setCustomerId(customerID+EMPTY_STRING);
		customersMap.put(customerID + EMPTY_STRING, customer);
		customerID ++;
	}
	
	public static void updateCustomer(Customer customer) {
		customersMap.put(customer.getCustomerId(), customer);
	}
	
	public static void deleteCustomer(String customerID)throws CustomerNotFoundException
	{
		if(null == customersMap.remove(customerID))
		{
			throw new CustomerNotFoundException("Customer details not found");
		}
	}

}
