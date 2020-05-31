package realestate;

import java.io.Serializable;

public class BranchAdministrator extends Employee implements Serializable{

	public BranchAdministrator(String eName, String eId, String ePassword, String designation, boolean isPartTimeEmployee) {
		super(eName, eId, ePassword, designation, isPartTimeEmployee);
	}

}
