package uk.co.o2.vo;


public class Customer {

	private String title;

	private String firstName;

	private String lastName;

	private String customerType;

	private String simNo;
	
	private String customerID;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *            the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the mSISDN
	 * 
	 */
	public String getSimNo() {
		return simNo;
	}

	/**
	 * @param mSISDN
	 *            the mSISDN to set
	 */
	public void setSimNo(String msi) {
		simNo = msi;
	}
	
	/**
	 * @return the id
	 */
	public String getCustomerId() {
		return customerID;
	}

	/**
	 * @param id the id to set
	 */
	public void setCustomerId(String id) {
		this.customerID = id;
	}

	public String toString() {
		return "Customer [title= " + title + ", firstName= " + firstName
				+ ", lastName= " + lastName + ", customerType= " + customerType
				+ ", MSISDN= " + simNo + ", CustomerID= "+customerID +" ]";
	}

}
