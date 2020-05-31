package realestate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RentalProperty extends Property implements Serializable{
	
	private double managementFeeRate, weeklyRent;
	private int contractDurationMonths;
	private LinkedHashMap<String, Application> appMap;
	
	
	public RentalProperty(String propertyId, String address, String suburb, int bedroom, int bathroom, boolean carSpace, char type, Owner owner,
			double weeklyRent, int contractDurationMonths) {
		super(propertyId, address, suburb, bedroom, bathroom, carSpace, type, owner);
		this.weeklyRent = weeklyRent;
		this.contractDurationMonths = contractDurationMonths;
		this.managementFeeRate = 8.0;
		appMap = new LinkedHashMap<String, Application>();
	}
	
	
	public double getManagementFeeRate() {
		return managementFeeRate;
	}


	public void setManagementFeeRate(double managementFeeRate) {
		this.managementFeeRate = managementFeeRate;
	}


	@Override
	public String toString() {	
		String val = super.toString() 																								+ "\n" 
				   + "Rental price: " 	+ "\t"	+ "\t"	+ this.getPrice()															+ "\n"
				   + "Available: " 		+ "\t"	+ "\t"	+ (super.getStatusOfProperty() == Constants.sop_Active ? "Yes" : "No" )		+ "\n";
		return val;
	}


	@Override
	public double getPrice() {
		return weeklyRent;
	}
	
	
	public void setPrice(double weeklyRent) {
		this.weeklyRent = weeklyRent;
	}


	public int getContractDurationMonths() {
		return contractDurationMonths;
	}


	public void setContractDurationMonths(int contractDurationMonths) {
		this.contractDurationMonths = contractDurationMonths;
	}


	public LinkedHashMap<String, Application> getAppMap() {
		return appMap;
	}
	
	
	public void createApplication(String buyerId, double weeklyRental, int contractDurationMonths) throws PropertyException {
		if (super.getStatusOfProperty() != Constants.sop_Active) {
			throw new PropertyException("Property is not available now.");
		}
		
		for (String key : appMap.keySet()) {
			if (appMap.get(key).getApplicationStatus() == Constants.offer_Accepted) {
				throw new PropertyException("An application is in accepted status. Cannot make an application now.");
			}
		}
		
		Application app = new Application(weeklyRental, contractDurationMonths);
		appMap.put(buyerId, app);
		
		String title = "New application received";
		String	dsb = "A new application is received for your property " + getPropertyId() + " from a prospect " + buyerId +".";
		
		getOwner().addNtf(title, dsb);
	}
	
	
	public void manageApplication(String buyerId, char status, HashMap<String, Customer> customerMap) throws PropertyException {
		Application app = appMap.get(buyerId);
		if (app == null) {
			throw new PropertyException("No application found! Please enter a valid buyer Id.");
		}
		
		app.setApplicationStatus(status);
		if (status == Constants.offer_Accepted) {
			app.setAcceptDate(new Date());
			
			Calendar cl = Calendar.getInstance();
			cl.setTime(app.getAcceptDate());
			cl.add(Calendar.HOUR, 24);
			Date dl = cl.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String deadLine = formatter.format(dl);
			
			Customer buyer = customerMap.get(buyerId);
			String title = "Application accepted";
			String dsb = "Your Application for property " + getPropertyId() + " is accepted. You are required to pay 1 month's rent plus a bond of 4 weeks rent on or before " + deadLine;
			buyer.addNtf(title, dsb);
		}
			
		appMap.put(buyerId, app);
	}
	
	
	public void withdrawApplication(String buyerId) throws PropertyException {
		Application app = appMap.get(buyerId);
		if (app == null) {
			throw new PropertyException("No application found for the buyer.");
		}
		
		if (app.getApplicationStatus() == Constants.offer_Accepted) {
			throw new PropertyException("Application accepted! Too late to withdraw it.");
		}
			
		appMap.remove(buyerId);
	}


	@Override
	public void processPayment(String buyerId, double amount) throws PropertyException {
		Application app = appMap.get(buyerId);
		if (app == null) {
			throw new PropertyException("No application found for the buyer.");
		}
		
		if (app.getApplicationStatus() != Constants.offer_Accepted) {
			throw new PropertyException("Application not accepted! Cannot process payment now.");
		}
		
		double minAmount = weeklyRent * (30/7) + (4 * weeklyRent);
		
		if (amount < minAmount) {
			throw new PropertyException("1 month's rent plus a bond of 4 weeks rent is required ("+ minAmount + ").");
		}
		
		Calendar cl = Calendar.getInstance();
		cl.setTime(app.getAcceptDate());
		cl.add(Calendar.HOUR, 24);
		
		Date deadline = cl.getTime();
		Date currentDate = new Date();
		
		if (currentDate.after(deadline)) {
			appMap.remove(buyerId);
			throw new PropertyException("24 Hour deadline to pay the down payment has passed.");
		}		
	}
}
