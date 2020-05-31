package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public class SalesConsultant extends AssignedEmployee implements Serializable{
	private double basicSalary, grossSalary;
	
	
	public SalesConsultant(String eName, String eId, String ePassword, String designation, boolean isPartTimeEmployee, double basicSalary) {
		super(eName, eId, ePassword, designation, isPartTimeEmployee);
		this.basicSalary = basicSalary;
	}


	public double getGrossSalary() {
		return grossSalary;
	}


	public void calculateGrossSalary() throws EmployeeException{
		ArrayList<Property> properties = super.getProperties();
		SaleProperty sp;
		
		grossSalary = basicSalary;
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i).getPropertyId().charAt(0) != Constants.SALE_PREFIX) {
				throw new EmployeeException("Sales consultant can't handle property other than sale property.");
			} else {
				sp = (SaleProperty) properties.get(i);
			}
			
			if (sp.getSalesCommission() == 0.0) {
				throw new EmployeeException("Sales commission not set for property " + sp.getPropertyId() + ".");
			}
			
			if (sp.getStatusOfProperty() == Constants.sop_ContractLet) {
				grossSalary += (sp.getPrice() * sp.getSalesCommission()/100) * Constants.BONUS_COMMISSION;
			}
		}
	}

	
	
}
