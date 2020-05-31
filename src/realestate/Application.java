package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application implements Serializable{
	private double weeklyRent;
	private int contractDurationMonths;
	private Date appDate, acceptDate;
	private char applicationStatus;
	
	
	public Application(double weeklyRent, int contractDurationMonths) {
		this.weeklyRent = weeklyRent;
		this.contractDurationMonths = contractDurationMonths;
		this.appDate = new Date();
		this.applicationStatus = Constants.offer_Active;
	}
	
	
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		String val = "Weekly Rental: " 				+ weeklyRent												+ "\n"
				   + "Contraction duration: " 		+ contractDurationMonths 									+ "\n"
				   + "Application date: " 			+ formatter.format(appDate)									+ "\n"
				   + "Application status: " 		+ (Constants.getOfferStatus(applicationStatus))				+ "\n" 
				   + "Accepted date: "				+ (acceptDate == null ? " " : formatter.format(acceptDate));
		
		return val;
	}


	public char getApplicationStatus() {
		return applicationStatus;
	}


	public void setApplicationStatus(char applicationStatus) {
		this.applicationStatus = applicationStatus;
	}


	public Date getAcceptDate() {
		return acceptDate;
	}


	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	
	
	
}
