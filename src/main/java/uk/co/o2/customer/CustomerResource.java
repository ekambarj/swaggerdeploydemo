package uk.co.o2.customer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import uk.co.o2.dao.CustomerDAO;
import uk.co.o2.exception.CustomerNotFoundException;
import uk.co.o2.util.CustomerConstants;
import uk.co.o2.vo.Customer;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiErrors;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/customers")
@Api(value = "/customers", description = "Manage customer details")
@Produces({ "application/json" })
public class CustomerResource {

	@GET
	@Path("/")
	@ApiOperation(value = "Returns list of all available customer details", notes = "Returns all the customer details", responseClass = "uk.co.o2.vo.Customer", multiValueResponse = true)
	@Produces({ "application/json" })
	public List<Customer> customer() {

		List custList = CustomerDAO.getCustomerList();

		if ((custList == null) || (custList.size() <= 0)) {
			Response.status(Response.Status.NO_CONTENT).entity(getMessage(204, "Customer details not available", CustomerConstants.MSG_TYPE_INFO)).build();
		}
		Response.status(Response.Status.OK).build();

		return custList;
	}

	@POST
	@Path("/")
	@ApiOperation(value = "Creates a new customer detail", notes = "Creates a new customer detail", responseClass = "java.lang.String")
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public Response customer(
			@ApiParam(value = "Customer details", required = true) Customer cust) {

		CustomerDAO.addCustomer(cust);
		return Response
				.status(Response.Status.CREATED)
				.entity(getMessage(201,
						"Customer details created successfully!",
						CustomerConstants.MSG_TYPE_INFO)).build();

	}

	@PUT
	@Path("/{customerID}")
	@ApiOperation(value = "Updates customer detail", notes = "Updates customer detail", responseClass = "java.lang.String")
	@ApiErrors({ @com.wordnik.swagger.annotations.ApiError(code = 404, reason = "Customer details not found") })
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public Response customer(@PathParam("customerID") String customerID,
			@ApiParam(value = "Customer details", required = true) Customer cust) {

		Customer custvo = null;
		try {

			custvo = CustomerDAO.getCustomerDetail(customerID);
			cust.setCustomerId(customerID);
			CustomerDAO.updateCustomer(cust);

			return Response
					.status(Response.Status.OK)
					.entity(getMessage(200,
							"Customer details updated successfully",
							CustomerConstants.MSG_TYPE_INFO)).build();

		} catch (CustomerNotFoundException e) {

			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(getMessage(404, "Customer details not found",
							CustomerConstants.MSG_TYPE_ERROR)).build();
		}
	}

	@GET
	@Path("/{customerID}")
	@ApiOperation(value = "Get customer details by customer id", notes = "Returns customer details for the given customer id", responseClass = "uk.co.o2.vo.Customer")
	@ApiErrors({
			@com.wordnik.swagger.annotations.ApiError(code = 404, reason = "Customer details not found") })
	@Produces({ "application/json" })
	public Response customer(@PathParam("customerID") int customerID) {
		Customer customerVO = null;

		try {
			customerVO = CustomerDAO.getCustomerDetail(customerID + "");
			return Response.status(Response.Status.OK).entity(customerVO)
					.build();

		} catch (CustomerNotFoundException e) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(getMessage(404,"Customer details not found",CustomerConstants.MSG_TYPE_ERROR)).build();
		}
	}

	@DELETE
	@Path("/{customerID}")
	@ApiOperation(value = "Delete customer details", notes = "Deletes customer details for the given customer id", responseClass = "java.lang.String")
	@ApiErrors({
			@com.wordnik.swagger.annotations.ApiError(code = 404, reason = "Customer Details not found")})
	@Produces({ "application/json" })
	public Response customer(
			@ApiParam(value = "CustomerID", required = true) @PathParam("customerID") String customerID) {
		
		try {
			CustomerDAO.deleteCustomer(customerID);
			return Response
					.status(Response.Status.OK)
					.entity(getMessage(200,"Customer details deleted successfully",CustomerConstants.MSG_TYPE_INFO)).build();
		} catch (CustomerNotFoundException e) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(getMessage(404,"Customer details not found",CustomerConstants.MSG_TYPE_ERROR)).build();
		}
		
	}


	private static String getMessage(int code, String message, String errorType) {
		return "{\"code\": " + code + ",  \"message\": " + message
				+ " ,  \"type\": " + errorType + "}";
	}

}