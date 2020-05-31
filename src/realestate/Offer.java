package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Offer implements Serializable{
	private double offerAmount;
	private Date offerDate, acceptDate;
	private char offerStatus;
	
	
	public Offer(double offerAmount) {
		this.offerAmount = offerAmount;
		this.offerDate = new Date();
		this.offerStatus = Constants.offer_Active;
	}
	
	
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		String val = "Offer amount: " 			+ offerAmount									+ "\n"
				   + "Offer date: " 			+ formatter.format(offerDate) 					+ "\n"
				   + "Offer status: " 			+ (Constants.getOfferStatus(offerStatus))		+ "\n" 
				   + "Accepted date: "			+ (acceptDate == null ? " " : formatter.format(acceptDate));
		
		return val;
	}
	

	public double getOfferAmount() {
		return offerAmount;
	}
	

	public Date getAcceptDate() {
		return acceptDate;
	}


	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	

	public char getOfferStatus() {
		return offerStatus;
	}


	public void setOfferStatus(char offerStatus) {
		this.offerStatus = offerStatus;
	}	
	

}
