package realestate;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Prospect extends Customer implements Serializable{
	private ArrayList<String> interestedSuburb = new ArrayList<String>();
	

	public Prospect(String name, String email, int type, String customerId, String password) {
		super(name, email, type, customerId, password);
	}
	
	public boolean addSuburb(String suburb) {
		interestedSuburb.add(suburb);
		return true;
	}

	
	public ArrayList<String> getSuburb() {
		return interestedSuburb;
	}

}
