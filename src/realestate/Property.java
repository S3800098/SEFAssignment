package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public abstract class Property implements Serializable{
	private String propertyId, address, suburb;
	private int bedroom, bathroom;
	private boolean carSpace;
	private char type, statusOfProperty;
	private Inspection inspection;
	private Owner owner;
	private AssignedEmployee assignedEmployee;
	
	
	public Property(String propertyId, String address, String suburb, int bedroom, int bathroom, boolean carSpace, char type, Owner owner) {
		this.propertyId = propertyId;
		this.address = address;
		this.suburb = suburb;
		this.bedroom = bedroom;
		this.bathroom = bathroom;
		this.carSpace = carSpace;
		this.type = type;
		this.statusOfProperty = Constants.sop_Inactive;
		this.inspection = null;
		this.owner = owner;
		this.assignedEmployee = assignedEmployee;
	}	
	
	
	public abstract double getPrice();
		
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getBedroom() {
		return bedroom;
	}


	public void setBedroom(int bedroom) {
		this.bedroom = bedroom;
	}


	public int getBathroom() {
		return bathroom;
	}


	public void setBathroom(int bathroom) {
		this.bathroom = bathroom;
	}


	public boolean isCarSpace() {
		return carSpace;
	}


	public void setCarSpace(boolean carSpace) {
		this.carSpace = carSpace;
	}


	public char getType() {
		return type;
	}


	public void setType(char type) {
		this.type = type;
	}


	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}


	public String getPropertyId() {
		return propertyId;
	}


	public AssignedEmployee getAssignedEmployee() {
		return assignedEmployee;
	}


	public Owner getOwner() {
		return owner;
	}


	public void setAssignedEmployee(Employee employee) throws EmployeeException{
		if (this instanceof SaleProperty) {
			if (!(employee instanceof SalesConsultant)) {
				throw new EmployeeException("Employee is not Sales Consultant! Sale property must be assigned to a Sales Consultant.");
			}
		} else {
			if (!(employee instanceof PropertyManager)) {
				throw new EmployeeException("Employee is not Property Manager! Rental property must be assigned to a Property Manager.");
			}
		}
		
		this.assignedEmployee = (AssignedEmployee) employee;
		((AssignedEmployee) employee).addProperty(this);
	}


	public String getSuburb() {
		return suburb;
	}
	
	
	public void setInspection(Date inspectionDate) throws PropertyException{
		if (inspectionDate == null) {
			this.inspection = null;
		} else {
			if (this instanceof AuctionSaleProperty) {
				if (inspectionDate.after(((AuctionSaleProperty) this).getAuctionDate())) {
					throw new PropertyException("Inspection date must be before auction date.");
				}
			}
			
			Inspection inspection = new Inspection(propertyId, inspectionDate);
			this.inspection = inspection;
		}
	}


	public Inspection getInspection() {
		return inspection;
	}

	
	public String toString() {
		String val = "Property Id: " 			+ "\t"	+ "\t"			+ propertyId										+ "\n"
				   + "Address: " 				+ "\t"	+ "\t"			+ address											+ "\n" 
				   + "Suburb: "					+ "\t"	+ "\t"			+ suburb											+ "\n"
				   + "No of bedroom: " 			+ "\t"	+ "\t"			+ bedroom											+ "\n" 
				   + "No. of bathroom: " 		+ "\t"					+ bathroom 											+ "\n" 
				   + "Car space: " 				+ "\t"	+ "\t"			+ (carSpace ? "Yes" : "No")							+ "\n"
				   + "Type: " 					+ "\t"	+ "\t"	+ "\t"	+ Constants.getypeOfProperty(type)					+ "\n"
				   + "Inspection date: "		+ "\t"					+ (inspection == null ? "Nil" : inspection)			+ "\n"
				   + "Assigned to employee: " 	+ "\t"					+ (assignedEmployee == null ? "Nil" : assignedEmployee.geteId()) 	+ "\n"
				   + "Owner's name: " 			+ "\t"	+ "\t"			+ owner.getName();
		   
		return val;
	}


	public char getStatusOfProperty() {
		return statusOfProperty;
	}


	public void setStatusOfProperty(char statusOfProperty) throws PropertyException {
		if (this.statusOfProperty == Constants.sop_ContractLet) {
			throw new PropertyException("Property is not available, can't change the availability now.");
		}
		
		this.statusOfProperty = statusOfProperty;
	}
	
	
	public void sendCustomerNtf(HashMap<String, Customer> customerMap, boolean isNewProperty) {
		for (Customer customer: customerMap.values()) {
			if (customer instanceof Prospect) {
				ArrayList<String> interestedSuburb = ((Prospect) customer).getSuburb();
				
				if (interestedSuburb.contains(suburb)) {
					String title, dsb;
				
					if (isNewProperty) {
						title = "New property listed";
						dsb = "A new property " + propertyId + " is listed in your interested suburb " + suburb + ".";
					} else {
						title = "Inspection updated";
						dsb = "Inspection of property " + propertyId + " is updated.";
					}
					customer.addNtf(title, dsb);
				}
			}
		}
	}
	
	
	public abstract void processPayment(String buyerId, double amount) throws PropertyException;
}
