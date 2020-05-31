package realestate;

import java.io.Serializable;

public class Vendor extends Owner implements Serializable{
	
	public Vendor(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}
	
}
