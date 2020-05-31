package realestate;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Customer implements Serializable{
	private String name, email, customerId, password;
	private int typeOfCustomer;
	private LinkedHashMap<String, Notification> ntfMap;
		
	public Customer(String name, String email, int type, String customerId, String password) {
		this.name = name;
		this.email = email;
		this.typeOfCustomer = type;
		this.customerId = customerId;
		this.password = password;
		ntfMap = new LinkedHashMap<String, Notification>();
	}


	public String getName() {
		return name;
	}


	public String getCustomerId() {
		return customerId;
	}


	public String getPassword() {
		return password;
	}


	public LinkedHashMap<String, Notification> getNtfMap() {
		return ntfMap;
	}


	private String generateNtfId() {
		String highestId = " ";
		String nextId;
		
		for (String key: ntfMap.keySet()) {
			if (key.compareTo(highestId) > 0)
				highestId = key;
		}
		
		DecimalFormat df = new DecimalFormat("000");
		int seq;
		
		if (highestId.compareTo(" ") != 0) {
			seq = Integer.parseInt(highestId.substring(1, 4));
			seq++;
		} else {
			seq = 1;
		}
		
		nextId = "N" + df.format(seq);			
		return nextId;
	}

	
	public void addNtf(String title, String dsb) {
		String ntfId = generateNtfId();
		Notification ntf = new Notification(ntfId, title, dsb);
		ntfMap.put(ntfId, ntf);
	}
	
	
	public void removeNtf(String ntfId) {
		ntfMap.remove(ntfId);
	}
}
