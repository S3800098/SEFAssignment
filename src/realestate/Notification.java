package realestate;

import java.io.Serializable;

public class Notification implements Serializable{
	private String ntfId, title, dsb;

	
	public Notification(String ntfId, String title, String dsb) {
		this.ntfId = ntfId;
		this.title = title;
		this.dsb = dsb;
	}
	
	
	public String toString() {
		String val = "Notification Id: " 					+ "\t"	+ ntfId				+ "\n"
				   + "Title: " 				+ "\t"	+ "\t"	+ "\t"	+ title				+ "\n"
				   + "Description: " 				+ "\t"	+ "\t"	+ dsb				+ "\n";
				   
		return val;
	}

}
