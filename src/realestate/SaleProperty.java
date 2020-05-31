package realestate;

import java.io.Serializable;

public abstract class SaleProperty extends Property implements Serializable{
	private double salesCommission;
	
	
	public SaleProperty(String propertyId, String address, String suburb, int bedroom, int bathroom, boolean carSpace, char type, Owner owner) {
		super(propertyId, address, suburb, bedroom, bathroom, carSpace, type, owner);
		this.salesCommission = 0.0;
	}

	
	public double getSalesCommission() {
		return salesCommission;
	}

	
	public void setSalesCommission(double salesCommission) {
		this.salesCommission = salesCommission;
	}
	
	
	public abstract void createOffer(String buyerId, double offerAmount) throws PropertyException;
	
}
