package realestate;

import java.io.Serializable;

public class PropertyManager extends AssignedEmployee implements Serializable{

	public PropertyManager(String eName, String eId, String ePassword, String designation, boolean isPartTimeEmployee) {
		super(eName, eId, ePassword, designation, isPartTimeEmployee);
	}

}
