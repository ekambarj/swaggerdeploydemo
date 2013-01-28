package uk.co.o2.customer;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.co.o2.vo.Customer;



@Path("/customer/")
public class CustomerResource {

	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> customer() {
		
		 return getCustomerDetails();
	}

	@GET
	@Path("/{customerID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer customer(@PathParam("customerID") int customerID) {
		
		 return getCustomerDetail(customerID);
	}

	
	/**
	 * Returns customer VO
	 * @param custId - The customer id for which customer details is required
	 * @return Customer - The customer VO
	 */
	private Customer getCustomerDetail(int custId)
	{
		Customer customer = new Customer();
		customer.setCustomerId(custId);
		customer.setCustomerType("PREPAY");
		customer.setFirstName("John");
		customer.setLastName("Peter");
		customer.setMSISDN(448822903848l);
		customer.setTitle("Mr");
		
		return customer;
		
	}
	
	/**
	 * Returns lsit of customer VO
	 * @return List<Customer> - The list of customer VO
	 */
	private List<Customer> getCustomerDetails()
	{
		ArrayList<Customer> lst = new ArrayList<Customer>();
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerType("PREPAY");
		customer.setFirstName("John");
		customer.setLastName("Peter");
		customer.setMSISDN(448822903848l);
		customer.setTitle("Mr");
		
		Customer customer1 = new Customer();
		customer1.setCustomerId(2);
		customer1.setCustomerType("PREPAY");
		customer1.setFirstName("Daniel");
		customer1.setLastName("Peter");
		customer1.setMSISDN(448822913887l);
		customer1.setTitle("Mr");
		
		Customer customer2 = new Customer();
		customer2.setCustomerId(3);
		customer2.setCustomerType("PREPAY");
		customer2.setFirstName("Mark");
		customer2.setLastName("Pattinson");
		customer2.setMSISDN(4488229131123l);
		customer2.setTitle("Mr");
		
		lst.add(customer);
		lst.add(customer1);
		lst.add(customer2);
		
		
		return lst;
		
	}

}
