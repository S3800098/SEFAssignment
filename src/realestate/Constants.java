package realestate;

public class Constants {
	public static final char VENDOR_PREFIX = 'V', LANDLORD_PREFIX = 'L', BUYER_PREFIX = 'B', RENTER_PREFIX = 'R', 
			SALE_PREFIX = 'S', RENTAL_PREFIX = 'R';
	
	//typeOfCustomer: 1 - Vendor, 2 - Landlord, 3 - Buyer, 4 - Renter
	public static final int toc_Vendor = 1, toc_Landlord = 2, toc_Buyer = 3, toc_Renter = 4;
	
	//typeOfProperty: 1 - House(default), 2 - Unit, 3 - Flat, 4 - Town House, 5 - Studio
	public static final char top_House = '1', top_Unit = '2', top_Flat = '3', top_TownHouse = '4', top_Studio = '5';
	public static final String type_House = "House", type_Unit = "Unit", type_Flat = "Flat", type_TownHouse = "Town House", type_Studio = "Studio";
	
	//statusOfProperty: 1 - inactive(default), 2 - active, 3 - under contract or let
	public static final char sop_Inactive = '1', sop_Active = '2', sop_ContractLet = '3';
	
	//statusOfOffer: 1 - active(default), 2 - offer rejected, 3 - offer accepted
	public static final char offer_Active = '1', offer_Rejected = '2', offer_Accepted = '3';
	public static final String active = "Active", rejected = "Rejected", accepted = "Accepted";
	
	public static final double BONUS_COMMISSION = 0.4;
	

	public static String getypeOfProperty(char top) {
		switch (top) {
		case top_House:
			return type_House;

		case top_Unit:
			return type_Unit;
		
		case top_Flat:
			return type_Flat;

		case top_TownHouse:
			return type_TownHouse;

		case top_Studio:
			return type_Studio;

		}
		
		return null;	
	}
	
	
	public static String getOfferStatus(char status) {
		switch (status) {
		case offer_Active:
			return active;

		case offer_Rejected:
			return rejected;
		
		case offer_Accepted:
			return accepted;

		}
		
		return null;	
	}
	
	
}
