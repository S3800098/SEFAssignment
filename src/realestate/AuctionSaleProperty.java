package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class AuctionSaleProperty extends SaleProperty implements Serializable{
	private Date auctionDate;
	private double minReservePrice;
	private boolean isSellFail;
	private LinkedHashMap<String, Bid> bidMap;
	
	
	public AuctionSaleProperty(String propertyId, String address, String suburb, int bedroom, int bathroom,
			boolean carSpace, char type, Owner owner, Date auctionDate, double minReservePrice) {
		super(propertyId, address, suburb, bedroom, bathroom, carSpace, type, owner);
		this.auctionDate = auctionDate;
		this.minReservePrice = minReservePrice;
		this.isSellFail = false;
		bidMap = new LinkedHashMap<String, Bid>();
	}

	
	public Date getAuctionDate() {
		return auctionDate;
	}

	
	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
	}


	public boolean isSellFail() {
		return isSellFail;
	}


	public void setSellFail(boolean isSellFail) {
		this.isSellFail = isSellFail;
	}


	public void setMinReservePrice(double minReservePrice) {
		this.minReservePrice = minReservePrice;
	}


	@Override
	public double getPrice() {
		return minReservePrice;
	}
	

	@Override
	public String toString() {
		String val = super.toString() 	+ "\n" 
				   + "Reserve price: "	+ "\t"	+ "\t"	+ minReservePrice																	+ "\n"
				   + "Available: " 		+ "\t"	+ "\t"	+ (super.getStatusOfProperty() == Constants.sop_Active ? "Yes" : "No") 	+ "\n";
		return val;
	}


	@Override
	public void createOffer(String buyerId, double bidAmount) throws PropertyException {
		if (super.getStatusOfProperty() != Constants.sop_Active) {
			throw new PropertyException("Property is not available now.");
		}
		
		if (bidMap.size() != 0) {
			LinkedList<String> buyerList = new LinkedList<String>(bidMap.keySet());
			double highestBidAmount = bidMap.get(buyerList.getLast()).getBidAmount();
			
			if (bidAmount - 1000.0 < highestBidAmount) {
				throw new PropertyException("Bid amount must be greater than minimum price " + highestBidAmount + " by $1000.");
			}
			
			for (String key : buyerList) {
				if (bidMap.get(key).getBidStatus() == Constants.offer_Accepted) {
					throw new PropertyException("Auction has ended. You can't make a bid now");
				}
			}
		}
			
		Bid bid = new Bid(bidAmount);
		bidMap.put(buyerId, bid);		
	}

	
	public void selectBidder(HashMap<String, Customer> customerMap) throws PropertyException {
		ArrayList<String> buyerList = new ArrayList<String>(bidMap.keySet());
		Bid bid = null;
		
		for (int i = buyerList.size() - 1; i >= 0; i--) {
			bid = bidMap.get(buyerList.get(i));
			
			if	(	bid.getBidAmount() >= minReservePrice
				&&	bid.getBidStatus() == Constants.offer_Active) {
				bid.setAcceptDate(new Date());
				bid.setBidStatus(Constants.offer_Accepted);
				
				Calendar cl = Calendar.getInstance();
				cl.setTime(bid.getAcceptDate());
				cl.add(Calendar.HOUR, 24);
				Date dl = cl.getTime();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String deadLine = formatter.format(dl);
				
				Customer buyer = customerMap.get(buyerList.get(i));
				String title = "Bid accepted";
				String dsb = "Your bid for property " + super.getPropertyId() + " is accepted. You are required to deposit 10% of " + bid.getBidAmount() + " on or before " + deadLine;
				buyer.addNtf(title, dsb);
				return;
			}			
		}
		
		isSellFail = true;
		bidMap.clear();
		throw new PropertyException("No valid bid found. Please notify the owner to put up for auction again.");		
	}


	@Override
	public void processPayment(String buyerId, double amount) throws PropertyException {
		Bid bid = bidMap.get(buyerId);
		if (bid == null) {
			throw new PropertyException("No bid found for the buyer.");
		}
		
		if (bid.getBidStatus() != Constants.offer_Accepted) {
			throw new PropertyException("Bid not accepted! Cannot process payment now.");
		}
		
		if (amount < (bid.getBidAmount() * 0.1)) {
			throw new PropertyException("Down payment must be atleast 10% of bid amount.");
		}
		
		Calendar cl = Calendar.getInstance();
		cl.setTime(bid.getAcceptDate());
		cl.add(Calendar.HOUR, 24);
		
		Date deadline = cl.getTime();
		Date currentDate = new Date();
		
		if (currentDate.after(deadline)) {
			bidMap.remove(buyerId);
			throw new PropertyException("24 Hour deadline to pay the down payment has passed.");
		}		
	}		

}
