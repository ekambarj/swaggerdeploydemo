package uk.co.o2.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	@Produces({MediaType.APPLICATION_JSON})
	/**
	 * Returns list of customer details.
	 * @return List<Customer> : List of customer details
	 */
	public List<Customer> customer() {
		List<Customer> custList = getCustomerDetails();
		if(null == custList || custList.size() <=0)
		{
			Response.status(Response.Status.NO_CONTENT).build();	
		}
		Response.status(Response.Status.OK).build();
		 
		 return custList;
	}
	
	@POST
	@Path("/")
	@ApiOperation(value = "Creates a new customer detail", notes = "Creates a new customer detail", responseClass = "java.lang.String")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	/**
	 * Creates new customer details.
	 * @return Customer : The newly created customer details
	 */
public String customer(@ApiParam(value = "Customer details", required = true) Customer cust) {
		addCustomer("PREPAY",cust.getFirstName(),cust.getLastName(),cust.getTitle());
		
		Response.status(Response.Status.OK).build();	
		
		return "Customer details created successfully";
		
	}
	
	@PUT
	@Path("/{customerID}")
	@ApiOperation(value = "Updates customer detail", notes = "Updates customer detail", responseClass = "java.lang.String")
	@ApiErrors(value = { @ApiError(code = 404, reason = "Customer details not found") })
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	/**
	 * Updates customer details.
	 * @return String : The status of the operation
	 */
	public String customer(@PathParam("customerID") String customerID,@ApiParam(value = "Customer details", required = true) Customer cust) {
		
		if(null == customersMap.get(customerID))
		{
			Response.status(Response.Status.NOT_FOUND).build();
			return "Customer details not found";
		}
		
		updateCustomer(cust.getCustomerType(),cust.getFirstName(),cust.getLastName(),customerID);
		
		Response.status(Status.OK);
		 return "Customer details updated successfully";
	}

	@GET
	@Path("/{customerID}")
	@ApiOperation(value = "Get customer details by customer id", 
	notes = "Returns customer details for the given customer id", responseClass = "uk.co.o2.vo.Customer")
    @ApiErrors(value = { @ApiError(code = 404, reason = "Customer details not found"),
    @ApiError(code = 404, reason = "Customer details not found") })
	@Produces({MediaType.APPLICATION_JSON})
	public Customer customer(@PathParam("customerID") int customerID) throws Exception{
		
		if(null == customersMap.get(customerID+""))
		{
			Response.status(Response.Status.NOT_FOUND).build();
			throw new Exception(" 404 Customer details not found");
		}
		 return getCustomerDetail(customerID+"");
	}
	
	@DELETE
	@Path("/{customerID}")
	@ApiOperation(value = "Delete customer details", 
	notes = "Deletes customer details for the given customer id", responseClass = "java.lang.String")
    @ApiErrors(value = { @ApiError(code = 404, reason = "Customer Details not found"),
    @ApiError(code = 404, reason = "Customer Details not found") })
	@Produces({MediaType.APPLICATION_JSON})
	
	public Response customer(@ApiParam(value = "CustomerID", required = true) @PathParam("customerID") String customerID) {
		if(null == customersMap.remove(customerID))
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).build();
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
	private Customer getCustomerDetail(String custId)  
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
	private static Customer addCustomer(String customerType,String firstName,String lastName,String title)
	{
		
		Customer customer = new Customer();
		customer.setCustomerId(customerID+"");
		customer.setCustomerType("PREPAY");
		customer.setSimNo("44882291311"+customerID);
		customer.setFirstName(firstName +customerID);
		customer.setLastName(lastName +customerID);
		
		customer.setTitle(title);

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
		customer.setSimNo("44882291311"+id);
		customer.setFirstName("Customer  "+id);
		customer.setLastName(" Michael "+id);
		
		customer.setTitle("Mr");
		
		return customer;
		
	}

}
