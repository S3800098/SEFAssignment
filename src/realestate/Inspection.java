package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Inspection implements Serializable{
	private String propertyId;
	private Date date;
	
	
	public Inspection(String propertyId, Date date) {
		this.propertyId = propertyId;
		this.date = date;
	}
	
	
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(date);
	}


	public Date getDate() {
		return date;
	}
	
	
}
