package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public class Buyer extends Prospect implements Serializable{
	
	public Buyer(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}

}
