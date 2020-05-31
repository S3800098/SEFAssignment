package realestate;

import java.io.Serializable;

public class Landlord extends Owner implements Serializable{
	
	public Landlord(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}
	
	
}
