package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class NegotiationSaleProperty extends SaleProperty implements Serializable{
	private double minPrice;
	private LinkedHashMap<String, Offer> offerMap;

	public NegotiationSaleProperty(String propertyId, String address, String suburb, int bedroom, int bathroom,
			boolean carSpace, char type, Owner owner, double minPrice) {
		super(propertyId, address, suburb, bedroom, bathroom, carSpace, type, owner);
		this.minPrice = minPrice;
		offerMap = new LinkedHashMap<String, Offer>();
	}

	@Override
	public double getPrice() {
		return minPrice;
	}
	
	
	public void setPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	
	public LinkedHashMap<String, Offer> getOffer() {
		return offerMap;
	}
	
	@Override
	public void createOffer(String buyerId, double offerAmount) throws PropertyException {
		if (super.getStatusOfProperty() != Constants.sop_Active) {
			throw new PropertyException("Property is not available now.");
		}
		
		if (offerAmount <= minPrice) {
			throw new PropertyException("Offer amount must be greater than minimum price " + minPrice + " .");
		}
		
		for (String key : offerMap.keySet()) {
			if (offerMap.get(key).getOfferStatus() == Constants.offer_Accepted) {
				throw new PropertyException("An offer is in accepted status. Cannot make an offer now.");
			}
		}
		
		Offer offer = new Offer(offerAmount);
		offerMap.put(buyerId, offer);
		
		String title = "New offer received";
		String	dsb = "A new offer is received for your property " + getPropertyId() + " from a prospect " + buyerId +" .";
		
		getOwner().addNtf(title, dsb);
	}
	
	
	public void manageOffer(String buyerId, char status, HashMap<String, Customer> customerMap) throws PropertyException {
		Offer offer = offerMap.get(buyerId);
		if (offer == null) {
			throw new PropertyException("No offer found! Please enter a valid buyer Id.");
		}
		
		offer.setOfferStatus(status);
		if (status == Constants.offer_Accepted) {
			offer.setAcceptDate(new Date());
			
			Calendar cl = Calendar.getInstance();
			cl.setTime(offer.getAcceptDate());
			cl.add(Calendar.HOUR, 24);
			Date dl = cl.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String deadLine = formatter.format(dl);
			
			Customer buyer = customerMap.get(buyerId);
			String title = "Offer accepted";
			String dsb = "Your offer for property " + getPropertyId() + " is accepted. You are required to deposit 10% of " + offer.getOfferAmount() + " on or before " + deadLine;
			buyer.addNtf(title, dsb);
		}
			
		offerMap.put(buyerId, offer);
	}

	
	public void withdrawOffer(String buyerId) throws PropertyException {
		Offer offer = offerMap.get(buyerId);
		if (offer == null) {
			throw new PropertyException("No offer found for the buyer.");
		}
		
		if (offer.getOfferStatus() == Constants.offer_Accepted) {
			throw new PropertyException("Offer accepted! Too late to withdraw it.");
		}
			
		offerMap.remove(buyerId);
	}

	
	@Override	
	public void processPayment(String buyerId, double amount) throws PropertyException {
		Offer offer = offerMap.get(buyerId);
		if (offer == null) {
			throw new PropertyException("No offer found for the buyer.");
		}
		
		if (offer.getOfferStatus() != Constants.offer_Accepted) {
			throw new PropertyException("Offer not accepted! Cannot process payment now.");
		}
		
		if (amount < (offer.getOfferAmount() * 0.1)) {
			throw new PropertyException("Down payment must be atleast 10% of offer amount.");
		}
		
		Calendar cl = Calendar.getInstance();
		cl.setTime(offer.getAcceptDate());
		cl.add(Calendar.HOUR, 24);
		
		Date deadline = cl.getTime();
		Date currentDate = new Date();
		
		if (currentDate.after(deadline)) {
			offerMap.remove(buyerId);
			throw new PropertyException("24 Hour deadline to pay the down payment has passed.");
		}		
	}
	
	
	@Override
	public String toString() {
		String val = super.toString() 	+ "\n" 
				   + "Minimum price: "	+ "\t"	+ "\t"	+ minPrice 																			+ "\n"
				   + "Available: " 		+ "\t"	+ "\t"	+ (super.getStatusOfProperty() == Constants.sop_Active ? "Yes" : "No") 	+ "\n";
		return val;
	}
	


}
