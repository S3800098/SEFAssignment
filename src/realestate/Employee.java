package realestate;

import java.io.Serializable;

public abstract class Employee implements Serializable{
	private String eName, eId, ePassword, designation;
	private boolean isPartTimeEmployee, isHoursChecked, isHoursApproved;
	private int noOfHours;

	public Employee(String eName, String eId, String ePassword, String designation, boolean isPartTimeEmployee) {
		this.eName = eName;
		this.eId = eId;
		this.ePassword = ePassword;
		this.designation = designation;
		this.isPartTimeEmployee = isPartTimeEmployee;
		this.noOfHours = 0;
		this.isHoursChecked = false;
		this.isHoursApproved = false;
	}

	
	public String geteName() {
		return eName;
	}


	public String getePassword() {
		return ePassword;
	}


	public String geteId() {
		return eId;
	}


	public boolean isPartTimeEmployee() {
		return isPartTimeEmployee;
	}
	

	public int getNoOfHours() {
		return noOfHours;
	}


	public void setNoOfHours(int noOfHours) {
		this.noOfHours = noOfHours;
	}
	
	
	public String toString() {
		String val = "Name: " 				+ eName					+ "\n" 
				   + "Emp Id: " 			+ eId					+ "\n"
				   + "Designation: " 		+ designation			+ "\n" 
				   + "Part-time employee: " + (isPartTimeEmployee ? "Yes" : "No");
		
		return val;
	}

	
	public boolean isHoursChecked() {
		return isHoursChecked;
	}


	public void setHoursChecked(boolean isHoursChecked) {
		this.isHoursChecked = isHoursChecked;
	}


	public boolean isHoursApproved() {
		return isHoursApproved;
	}


	public void setHoursApproved(boolean isHoursApproved) {
		this.isHoursApproved = isHoursApproved;
	}
	
	
	

}
