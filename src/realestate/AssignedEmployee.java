package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public class AssignedEmployee extends Employee implements Serializable{
	private ArrayList<Property> properties = new ArrayList<Property>();


	public AssignedEmployee(String eName, String eId, String ePassword, String designation,
			boolean isPartTimeEmployee) {
		super(eName, eId, ePassword, designation, isPartTimeEmployee);
	}

	
	public ArrayList<Property> getProperties() {
		return properties;
	}

	
	public void addProperty(Property property) {
		properties.add(property);
	}

}
