package uk.co.o2.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import uk.co.o2.vo.Customer;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiError;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


@Path("/customers")
@Api(value = "/customers", description = "Manage customer details")
@Produces({MediaType.APPLICATION_JSON})
public class CustomerResource {
	
	private static HashMap<String,Customer> customersMap = new HashMap<String,Customer>();
	
	private static int customerID=1;
	static{
		for(;customerID<=10;customerID++)
		customersMap.put(customerID+"",getCustomerVO(customerID));
	}

	@GET
	@Path("/")
	@ApiOperation(value = "Returns list of all available customer details", notes = "Returns all the customer details", responseClass = "uk.co.o2.vo.Customer" , multiValueResponse = true)
	@ApiErrors(value = { @ApiError(code = 404, reason = "Customers not found") })
	@Produces({MediaType.APPLICATION_JSON})
	public List<Customer> customer() {
		 return getCustomerDetails();
	}
	
	@POST
	@Path("/")
	@ApiOperation(value = "Creates a new customer detail", notes = "Creates a new customer detail", responseClass = "java.lang.String")
	@ApiErrors(value = { @ApiError(code = 404, reason = "Customers not found") })
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.TEXT_PLAIN)
public Response customer(@ApiParam(value = "Customer value that needs to be added", required = true)Customer cust) {
		addCustomer(cust.getCustomerType(),cust.getFirstName(),cust.getLastName());
		return Response.status(Response.Status.OK).build();
	}
	
	@PUT
	@Path("/")
	@ApiOperation(value = "Updates customer detail", notes = "Updates customer detail", responseClass = "java.lang.String")
	@ApiErrors(value = { @ApiError(code = 404, reason = "Customer not found") })
	@Produces({MediaType.APPLICATION_JSON})
	public String customer(
			@FormParam("customerID") String customerID,
			@FormParam("lastName") String lastName,
			@FormParam("customerType") String customerType,
			@FormParam("firstName") String firstName) {
		
		updateCustomer(customerType,firstName,lastName,customerID);
		
		Response.status(Status.ACCEPTED);
		 return "Customer Added successfully";
	}

	@GET
	@Path("/{customerID}")
	@ApiOperation(value = "Get customer details by customer id", 
	notes = "Returns customer details for the given customer id", responseClass = "uk.co.o2.vo.Customer")
    @ApiErrors(value = { @ApiError(code = 400, reason = "Customer ID invalid"),
    @ApiError(code = 404, reason = "Customer not found") })
	@Produces({MediaType.APPLICATION_JSON})
	public Customer customer(@PathParam("customerID") int customerID) {
		
		 return getCustomerDetail(customerID);
	}

	/**
	 * Returns list of customer details
	 * @return List of customer VO
	 */
	private List<Customer>getCustomerDetails()
	{
		Collection<Customer> custColection = customersMap.values();
		
		if(null == custColection || custColection.size() ==0)
		{
			Response.status(Status.NO_CONTENT);
		}
		
		ArrayList<Customer> arrlst = new ArrayList<Customer>();
		arrlst.addAll(customersMap.values());
		
		return arrlst;
	}
	
	/**
	 * Returns customer VO
	 * @param custId - The customer id for which customer details is required
	 * @return Customer - The customer VO
	 */
	private Customer getCustomerDetail(int custId)  
	{	
		Customer customerVO =customersMap.get(custId); 
		if(null == customerVO)
		{
			Response.status(Status.NOT_FOUND);
		}
		
		return customerVO;
		
	}

	/**
	 * Returns customer value object
	 * @param id : The unique customer id
	 * @return : Customer VO
	 */
	private static Customer addCustomer(String customerType,String firstName,String lastName)
	{
		
		Customer customer = new Customer();
		customer.setCustomerId(customerID+"");
		customer.setCustomerType("PREPAY");
		customer.setSimNo("44882291311l"+customerID);
		customer.setFirstName(firstName +customerID);
		customer.setLastName(lastName +customerID);
		
		customer.setTitle("Mr");

		customersMap.put(customerID+"", customer);
		customerID++;
		
		return customer;
		
	}
	
	/**
	 * Updates customer details
	 * @param customerType : The customer type
	 * @param firstName : The customer first name
	 * @param lastName : The customer last name
	 * @param customerID : The customer id
	 * @return String : Status message
	 */
	private static String updateCustomer(String customerType,String firstName,String lastName,String customerID)
	{
		Customer customer =customersMap.get(customerID);
		if(null == customer)
		{
			Response.status(Status.NOT_FOUND);
			return "Customer details not found";
		}
		customer.setCustomerType("PREPAY");
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customersMap.put(customerID+"", customer);
		return "Customer details updated successfully";
	}
	
	/**
	 * Returns customer value object
	 * @param id : The unique customer id
	 * @return : Customer VO
	 */
	private static Customer getCustomerVO(int id)
	{
		
		Customer customer = new Customer();
		customer.setCustomerId(id+"");
		customer.setCustomerType("PREPAY");
		customer.setSimNo("44882291311l"+id);
		customer.setFirstName("Customer  "+id);
		customer.setLastName(" Michael "+id);
		
		customer.setTitle("Mr");
		
		return customer;
		
	}

}
