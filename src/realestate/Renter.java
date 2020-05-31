package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public class Renter extends Prospect implements Serializable{
	
	public Renter(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}

}
