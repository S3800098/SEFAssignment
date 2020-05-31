package realestate;

import java.io.Serializable;
import java.util.Date;

public class Bid implements Serializable{
	private double bidAmount;
	private Date acceptDate;
	private char bidStatus;
	
	
	public Bid(double bidAmount) {
		this.bidAmount = bidAmount;
		this.bidStatus = Constants.offer_Active;
	}


	public double getBidAmount() {
		return bidAmount;
	}


	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}


	public Date getAcceptDate() {
		return acceptDate;
	}


	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}


	public char getBidStatus() {
		return bidStatus;
	}


	public void setBidStatus(char bidStatus) {
		this.bidStatus = bidStatus;
	}

	
	
}
