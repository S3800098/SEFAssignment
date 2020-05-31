package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public class BranchManager extends Employee implements Serializable{
	private ArrayList<Employee> reportees = new ArrayList<Employee>();

	public BranchManager(String eName, String eId, String ePassword, String designation, boolean isPartTimeEmployee) {
		super(eName, eId, ePassword, designation, isPartTimeEmployee);
	}
	
	
	public void addReportee(Employee reportee)	{
		reportees.add(reportee);
	}
	
	
	public ArrayList<Employee> getReportees() {
		return reportees;
	}
	
	
	public void approveHours(Employee employee) throws EmployeeException{
		if (!reportees.contains(employee)) {
			throw new EmployeeException("Employee is not your reportee.");
		}
		
		if (!employee.isPartTimeEmployee()) {
			throw new EmployeeException("Employee is not a part time employee.");
		}
		
		if (employee.getNoOfHours() == 0) {
			throw new EmployeeException("Employee has not entered hours.");
		}
		
		if (employee.isHoursChecked()) {
			throw new EmployeeException("Hours is already checked.");
		}
		
		employee.setHoursChecked(true);
		employee.setHoursApproved(true);
	}

}
