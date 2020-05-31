package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Owner extends Customer implements Serializable{
	private ArrayList<Property> property = new ArrayList<Property>();
	

	public Owner(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}

	
	public void addProperty(Property item) {
		property.add(item);
	}

	
	public ArrayList<Property> getProperty() {
		return property;
	}
}
