package realestate;

import java.io.*;
import java.text.*;
import java.util.*;

public class RealEstateAgency {
	private Scanner scan;
	
	private String empLogId, empPwd;
	private Employee loginEmployee;
	
	private String cusLogId, cusPwd;
	private Customer loginCustomer;
	
	private HashMap<String, Customer> customerMap;
	private HashMap<String, Property> propertyMap;
	private HashMap<String, Employee> employeeMap;
	
	
	public RealEstateAgency() {
		scan = new Scanner(System.in);
		loginEmployee = null;
		loginCustomer = null;
		
		customerMap = new HashMap<String, Customer>();
		propertyMap = new HashMap<String, Property>();
		employeeMap = new HashMap<String, Employee>();
		
//		employeeMap.put("BM01", new BranchManager("Scott", "BM01", "123", "Branch Manager", false));
//		employeeMap.put("BA01", new BranchAdministrator("Matt", "BA01", "123", "Branch Administrator", false));
//		employeeMap.put("SC01", new SalesConsultant("Beck", "SC01", "123", "Sales Consultant", false, 1000));
//		employeeMap.put("SC02", new SalesConsultant("Grace", "SC02", "123", "Sales Consultant", true, 1000));
//		employeeMap.put("PM01", new PropertyManager("Max", "PM01", "123", "Property Manager", false));		
	}
	

	public static void main(String[] args) {
		RealEstateAgency realEstateAgency = new RealEstateAgency();
		
		realEstateAgency.processFile();
		realEstateAgency.userTransaction();
	}
	
	public void userTransaction() {
		boolean goNext = true;
		char response;
		
		do {
			response = getInitialChoice();
			
			if (response == '0') {
				browseProperties();
			} else if (response == '1') {
				logInEmployee();
			} else if (response == '2') {
				logInCustomer();
			} else if (response == '3') {
				registerCustomer();
			} else if (response == '4') {
				processWriteFile();
				goNext = processExit("System exited! Thanks for using S&E Real Estate Agency.");
			} else {
				System.out.println("Invalid entry! Please enter a valid choice.");
			}
		} while (goNext);
	}
	
	
	private char getInitialChoice() {
		System.out.println("\n** S&E Real Estate Agency **");
		System.out.println("0. Browse properties");
		System.out.println("1. Employee log in");
		System.out.println("2. Customer log in");
		System.out.println("3. Customer register");
		System.out.println("4. Quit");
		System.out.println("** ********************** **");
		System.out.print("Enter your choice: ");
		char response = scan.nextLine().charAt(0);
		return response;
	}
	
	
	private void browseProperties() {
		System.out.print("Enter a suburb: ");
		
		String suburb = scan.nextLine();
		if (suburb.length() == 0) {
			System.out.println("Invalid suburb! Please enter a valid suburb name.");
			return;
		}
		
		System.out.print("Enter lower price range: ");
		
		double lowerPrice;
		try {
			lowerPrice = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid price! Please enter a price.");
			return;
		}
		
		System.out.print("Enter higher price range: ");
		
		double higherPrice;
		try {
			higherPrice = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid price! Please enter a price.");
			return;
		}
		
		getProperties(suburb, lowerPrice, higherPrice);		
	}
	
	
	private void getProperties(String suburb, double lowerPrice, double higherPrice) {
		System.out.println("********************  Property Details  ********************");
		
		for (Property property : propertyMap.values()) {
			if	(	property.getSuburb().equalsIgnoreCase(suburb) 
				&&	property.getPrice() >= lowerPrice 
				&&	property.getPrice() <= higherPrice) {
				System.out.println(property);
			}
		}
		
		System.out.println("********************  End of Details  **********************");
	}
	
	
	private void logInEmployee() {
		System.out.print("Enter employeeId: ");
		empLogId = scan.nextLine().toUpperCase();
		System.out.print("Enter your password: ");
		empPwd = scan.nextLine();
			
		if (validateEmpLogId() == false)
			System.out.println("Invalid employee id or password.");
		else {
			System.out.println("Welcome " + loginEmployee.geteName() + "!");
			boolean goNext = true;
			char response;
			
			do {
				response = getEmployeeChoice();
				
				if (loginEmployee instanceof BranchManager) {
					if (response == '1')
						approveHours();
					else if (response == '2')
						assignEmployee();
					else if (response == '3')
						viewAllProperties();
					else if (response == '4')
						viewEmployees();
					else if (response == '5')
						addReportees();
					else if (response == '6')
						viewReportees();
					else if (response == '8' && loginEmployee.isPartTimeEmployee())
						enterPartTimeHours();
					else if (response == '9')
			    	   goNext = processExit("You have successfully logged out!");
					else
						System.out.println("Invalid entry! Please enter a number from the menu.");
				} else if (loginEmployee instanceof SalesConsultant) {
					if (response == '1')
						setSalesCommission();
					else if (response == '2')
						scheduleInspection();
					else if (response == '3')
						selectBidder();
					else if (response == '8' && loginEmployee.isPartTimeEmployee())
						enterPartTimeHours();
					else if (response == '9')
			    	   goNext = processExit("You have successfully logged out!");
					else
						System.out.println("Invalid entry! Please enter a number from the menu.");
				} else if (loginEmployee instanceof PropertyManager) {
					if (response == '1')
						setManagementRate();
					else if (response == '2')
						scheduleInspection();
					else if (response == '8' && loginEmployee.isPartTimeEmployee())
						enterPartTimeHours();
					else if (response == '9')
						goNext = processExit("You have successfully logged out!");
					else
						System.out.println("Invalid entry! Please enter a number from the menu.");
				} else if (loginEmployee instanceof BranchAdministrator) {
					if (response == '1')
						calculateSalary();
					else if (response == '8' && loginEmployee.isPartTimeEmployee())
						enterPartTimeHours();
					else if (response == '9')
						goNext = processExit("You have successfully logged out!");
					else
						System.out.println("Invalid entry! Please enter a number from the menu.");
				}
					
			} while (goNext);
		}			
	}

	
	private boolean validateEmpLogId() {
		loginEmployee = employeeMap.get(empLogId);
		if (loginEmployee == null) 
			return false;
		
		if (empPwd.compareTo(loginEmployee.getePassword()) != 0)
			return false;
		
		return true;
	}
	
	
	private char getEmployeeChoice() {
		System.out.println("\n** Employee Menu **");
		
		if (loginEmployee instanceof BranchManager) {
			System.out.println("1. Approve working hours of reportees");
			System.out.println("2. Assign an employee to a property");
			System.out.println("3. View all properties");
			System.out.println("4. View list of employees");
			System.out.println("5. Add an employee as reportee");
			System.out.println("6. View list of reporting employees");
			
			if (loginEmployee.isPartTimeEmployee())
				System.out.println("8. Enter part time hours");
			
		} else if (loginEmployee instanceof SalesConsultant) {
			System.out.println("1. Set sales commission of a property");
			System.out.println("2. Schedule inspection");
			System.out.println("3. Select highest bidder");
			
			if (loginEmployee.isPartTimeEmployee())
				System.out.println("8. Enter part time hours");
			
		} else if (loginEmployee instanceof PropertyManager) {
			System.out.println("1. Set management fee rate of a property");
			System.out.println("2. Schedule inspection");
			
			if (loginEmployee.isPartTimeEmployee())
				System.out.println("8. Enter part time hours");
			
		} else if (loginEmployee instanceof BranchAdministrator) {
			System.out.println("1. Calculate salary of a Sales Consultant");
			
			if (loginEmployee.isPartTimeEmployee())
				System.out.println("8. Enter part time hours");
		}
		
		System.out.println("9. Log Out");
		System.out.print("Enter your choice: ");
		char response = scan.nextLine().charAt(0);
		return response;
	}
	
	
	private void logInCustomer() {
		System.out.print("Enter uniqueId: ");
		cusLogId = scan.nextLine().toUpperCase();
		System.out.print("Enter your password: ");
		cusPwd = scan.nextLine();
			
		if (validateCusLogId() == false)
			System.out.println("Invalid user id or password.");
		else {
			System.out.println("Welcome " + cusLogId + "!");
			boolean goNext = true;
			char response;
			
			do {
				response = getUserChoice();
				
				if (loginCustomer instanceof Prospect) {
					if (response == '1') {
						addSuburb();
					} else if (response == '2') {
						viewSuburb();
					} else if (response == '3') {
						if (loginCustomer instanceof Buyer) {
							makeOffer();
						} 
						if (loginCustomer instanceof Renter) {
							makeApplication();
						}
					} else if (response == '4') {
						makePayment();
					} else if (response == '5') {
						withdrawOffer();
					} else if (response == '7') {
						viewNotification();	
					} else if (response == '8') {
						deleteNotification();	
					} else if (response == '9') {
						goNext = processExit("You have successfully logged out!");
					} else {
						System.out.println("Invalid entry! Please enter a number from the menu.");
					}
				} else {
					if (response == '1') {
						addProperty();
					} else if (response == '2') {
						viewProperty();
					} else if (response == '3') {
						editProperty();
					} else if (response == '4') {
						viewOffer();
					} else if (response == '5') {
						manageOffer();
					} else if (response == '7') {
						viewNotification();	
					} else if (response == '8') {
						deleteNotification();	
					} else if (response == '9') {
			    	   goNext = processExit("You have successfully logged out!");
					} else {
						System.out.println("Invalid entry! Please enter a number from the menu.");
					}
				}
			} while (goNext);
		}			
	}
	
	
	private boolean validateCusLogId() {
		loginCustomer = customerMap.get(cusLogId);
		if (loginCustomer == null) 
			return false;
		
		if (cusPwd.compareTo(loginCustomer.getPassword()) != 0)
			return false;
		
		return true;
	}
	
	
	private char getUserChoice() {
		System.out.println("\n** User Menu **");
		
		if (loginCustomer instanceof Prospect) {
			System.out.println("1. Add an interested suburb");
			System.out.println("2. View interested suburbs");
			
			if (loginCustomer instanceof Buyer) {
				System.out.println("3. Make an offer / bid");
			} 
			if (loginCustomer instanceof Renter) {
				System.out.println("3. Make an application");
			}
			
			System.out.println("4. Make a down payment");
			System.out.println("5. Withdraw offer / application");
		} else {
			System.out.println("1. Add property");
			System.out.println("2. View property list");
			System.out.println("3. Edit property");
			System.out.println("4. View offer / application details");
			System.out.println("5. Manage offers / application");
		}
		
		System.out.println("7. View notifications");
		System.out.println("8. Delete notifications");
		System.out.println("9. Log Out");
		System.out.print("Enter your choice: ");
		char response = scan.nextLine().charAt(0);
		return response;
	}
	
	
	private void registerCustomer() {
		System.out.print("Enter name: ");
		String name = scan.nextLine();
		if (name.length() == 0) {
			System.out.println("Invalid name! Please enter a valid name.");
			return;
		}
		
		System.out.print("Enter email: ");
		String email = scan.nextLine();
		if (email.length() == 0) {
			System.out.println("Invalid email! Please enter a valid email.");
			return;
		} else if (!isValid(email)) {
			System.out.println("Invalid email! Please enter a valid email.");
			return;
		}
		
		System.out.print("Enter type of customer (1 - Vendor, 2 - Landlord, 3 - Buyer, 4 - Renter): ");
		int typeOfCustomer;
		
		try {
			typeOfCustomer = Integer.parseInt(scan.nextLine());
			
			if (typeOfCustomer != Constants.toc_Vendor && typeOfCustomer != Constants.toc_Landlord && 
				typeOfCustomer != Constants.toc_Buyer && typeOfCustomer != Constants.toc_Renter) {
				System.out.println("Invalid type! Please enter 1 - Vendor, 2 - Landlord, 3 - Buyer or 4 - Renter.");
				return;
			}
		} catch (Exception e) {
			System.out.println("Invalid type! Please enter 1 - Vendor, 2 - Landlord, 3 - Buyer or 4 - Renter.");
			return;
		}
		
		System.out.print("Enter a password: ");
		String pwd = scan.nextLine();
		System.out.print("Re-enter the password: ");
		String repwd = scan.nextLine();
		if	(	pwd.length() == 0 
			||	pwd.compareTo(repwd) != 0) {
			System.out.println("Invalid password! Please enter a valid password.");
			return;
		}
		
		addCustomer(name, email, typeOfCustomer, pwd);		
	}
	
	
	private static boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}
	
	
	public void addCustomer(String name, String email, int typeOfCustomer, String pwd) {
		Customer c = null;
		String uniqueID = null;
		
		switch (typeOfCustomer) {
		case 1:
			uniqueID = generateCustomerId(Constants.VENDOR_PREFIX);
			c = new Vendor(name, email, typeOfCustomer, uniqueID, pwd);
			break;

		case 2:
			uniqueID = generateCustomerId(Constants.LANDLORD_PREFIX);
			c = new Landlord(name, email, typeOfCustomer, uniqueID, pwd);
			break;

		case 3:
			uniqueID = generateCustomerId(Constants.BUYER_PREFIX);
			c = new Buyer(name, email, typeOfCustomer, uniqueID, pwd);
			break;
			
		case 4:
			uniqueID = generateCustomerId(Constants.RENTER_PREFIX);
			c = new Renter(name, email, typeOfCustomer, uniqueID, pwd);
			break;

		}
		
		customerMap.put(uniqueID, c);
		System.out.println("Customer registered successfully with id " + uniqueID);
	}
	
	
	private String generateCustomerId(char prefix) {
		String highestId = " ";
		String nextId;
		
		for (String key: customerMap.keySet()) {
			if (key.charAt(0) == prefix) {
				if (key.compareTo(highestId) > 0)
					highestId = key;
			}
		}
		
		DecimalFormat df = new DecimalFormat("000");
		int seq;
		
		if (highestId.compareTo(" ") != 0) {
			seq = Integer.parseInt(highestId.substring(1, 4));
			seq++;
		} else {
			seq = 1;
		}
		
		nextId = prefix + df.format(seq);			
		return nextId;
	}

	
	private boolean processExit(String msg) {
		System.out.println(msg);
		return false;
	}

	
	private void addSuburb() {
		System.out.print("Enter a suburb: ");
		String suburb = scan.nextLine().toUpperCase();
		if (suburb.length() == 0) {
			System.out.println("Invalid suburb name! Please enter a valid suburb name.");
			return;
		}
		
		if (((Prospect) loginCustomer).addSuburb(suburb))
			System.out.println("Suburb "+ suburb + " added successfully.");
	}

	
	private void viewSuburb() {
		ArrayList<String> suburList = ((Prospect) loginCustomer).getSuburb();
		
		System.out.println("********************  Interested Suburb  ********************");
		
		for (int i = 0; i < suburList.size(); i++) {
			System.out.println(suburList.get(i));
		}
		
		System.out.println("********************  End of Details  **********************");
	}
	
	
	private void addProperty() {
		System.out.print("Enter property address: ");
		String address = scan.nextLine().toUpperCase();
		if (address.length() == 0) {
			System.out.println("Invalid address! Please enter a valid address.");
			return;
		}
		
		System.out.print("Enter suburb name: ");
		String suburb = scan.nextLine().toUpperCase();
		if (suburb.length() == 0) {
			System.out.println("Invalid suburb name! Please enter a valid suburb name.");
			return;
		}
		
		System.out.print("Enter no. of bedrooms: ");
		int nofBedrooms;
		
		try {
			nofBedrooms = Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid number of rooms. Please enter a valid number.");
			return;
		}
		
		System.out.print("Enter no. of bathrooms: ");
		int nofBathrooms;
		
		try {
			nofBathrooms = Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid number of rooms. Please enter a valid number.");
			return;
		}
		
		System.out.print("Car space available (Y/N): ");
		char carSpace = scan.nextLine().toUpperCase().charAt(0);
		if	(	carSpace != 'Y'
			&&	carSpace != 'N') {
			System.out.println("Invalid entry! Please enter Y or N.");
			return;
		}
		
		System.out.print("Enter type of property (1 - House, 2 - Unit, 3 - Flat, 4 - Town House, 5 - Studio): ");
		char type = scan.nextLine().toUpperCase().charAt(0);
		if	(	type != Constants.top_House
			&&	type != Constants.top_Unit
			&&	type != Constants.top_Flat
			&&	type != Constants.top_TownHouse
			&&	type != Constants.top_Studio) {
			System.out.println("Invalid type! Please enter 1 - House, 2 - Unit, 3 - Flat, 4 - Town House, 5 - Studio.");
			return;
		}
		
		Property property = null;
		String propertyId;
		double price = 0.0;
		if (loginCustomer instanceof Vendor) {
			System.out.print("Do you intend to sell your property through negotiation (N) or auction (A): ");
			char saleType = scan.nextLine().toUpperCase().charAt(0);
			if	(	saleType != 'N'
				&&	saleType != 'A') {
				System.out.println("Invalid entry! Please enter 'N' - Negotiation or 'A' - Auction.");
				return;
			}
			
			propertyId = generatePropertyId(Constants.SALE_PREFIX);
			if	(saleType == 'N') {
				System.out.print("Enter minimum selling price: ");
				
				try {
					price = Double.parseDouble(scan.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid price. Please enter a valid price.");
					return;
				}
				
				property = new NegotiationSaleProperty(propertyId, address, suburb, nofBathrooms, nofBedrooms, 
						(carSpace == 'Y' ? true : false), type, ((Owner) loginCustomer), price);
				
			} else if (saleType == 'A') {
				System.out.print("Enter auction date and time (dd/MM/yyyy HH:mm:ss): ");
				
				String date;
				date = scan.nextLine();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				Date auctionDate;
				
				try {
					auctionDate = formatter.parse(date);
				} catch (Exception e) {
					System.out.println("Invalid date or time. Please enter in dd/MM/yyyy HH:mm:ss format.");
					return;
				}
				
				System.out.print("Enter minimum reserve price (if you wish): ");
				
				try {
					price = Double.parseDouble(scan.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid price. Please enter a valid price.");
					return;
				}
				
				property = new AuctionSaleProperty(propertyId, address, suburb, nofBathrooms, nofBedrooms, 
						(carSpace == 'Y' ? true : false), type, ((Owner) loginCustomer), auctionDate, price);
			}
			
		} else {
			System.out.print("Enter weekly rental price: ");
			
			try {
				price = Double.parseDouble(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid price. Please enter a valid price.");
				return;
			}
			
			int duration;
			System.out.print("Enter acceptable cotract duration (in months): ");
			
			try {
				duration = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid entry. Please enter duration in months.");
				return;
			}
			
			propertyId = generatePropertyId(Constants.RENTAL_PREFIX);
			property = new RentalProperty(propertyId, address, suburb, nofBathrooms, nofBedrooms, (carSpace == 'Y' ? true : false), type, ((Owner) loginCustomer), price, duration);
		}
		
		((Owner) loginCustomer).addProperty(property);
		
		System.out.println("Property successfully added with Id " + propertyId + ".");
		propertyMap.put(propertyId, property);
	}
	

	private void editProperty() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getOwner().getCustomerId().compareTo(cusLogId) != 0) {
			System.out.println("Failed! Only owner of a property can edit it.");
			return;
		}
		
		System.out.print("Do you want to change the address ("+ property.getAddress() + ") (Y/N): ");
		char addEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(addEdit == 'Y') {
			System.out.print("Enter new address: ");
			String address = scan.nextLine().toUpperCase();
			if (address.length() == 0) {
				System.out.println("Invalid address! Please enter a valid address.");
				return;
			} else {
				property.setAddress(address);
				System.out.println("Address changed to " + property.getAddress() + ".");
			}
		}	
		
		System.out.print("Do you want to change the suburb ("+ property.getSuburb() + ") (Y/N): ");
		char subEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(subEdit == 'Y') {
			System.out.print("Enter new suburb name: ");
			String suburb = scan.nextLine().toUpperCase();
			if (suburb.length() == 0) {
				System.out.println("Invalid suburb name! Please enter a valid suburb name.");
				return;
			} else {
				property.setSuburb(suburb);
				System.out.println("Suburb changed to " + property.getSuburb() + ".");
			}
		}
		
		System.out.print("Do you want to change the no. of bedrooms ("+ property.getBedroom() + ") (Y/N): ");
		char bdEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(bdEdit == 'Y') {
			System.out.print("Enter no. of bedrooms: ");
			int nofBedrooms;
			
			try {
				nofBedrooms = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid number of rooms. Please enter a valid number.");
				return;
			}
			
			property.setBedroom(nofBedrooms);
			System.out.println("No. of bedrooms changed to " + property.getBedroom() + ".");
		}
		
		System.out.print("Do you want to change the no. of bathrooms ("+ property.getBathroom() + ") (Y/N): ");
		char btEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(btEdit == 'Y') {
			System.out.print("Enter no. of bathrooms: ");
			int nofBathrooms;
			
			try {
				nofBathrooms = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid number of rooms. Please enter a valid number.");
				return;
			}
			
			property.setBathroom(nofBathrooms);
			System.out.println("No. of bathrooms changed to " + property.getBathroom() + ".");
		} 
		
		System.out.print("Do you want to change the car space availability ("+ (property.isCarSpace() ? "Yes" : "No") + ") (Y/N): ");
		char csEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(csEdit == 'Y') {
			System.out.print("Car space available (Y/N): ");
			char carSpace = scan.nextLine().toUpperCase().charAt(0);
			if	(	carSpace != 'Y'
				&&	carSpace != 'N') {
				System.out.println("Invalid entry! Please enter Y or N.");
				return;
			} else {
				property.setCarSpace(carSpace == 'Y' ? true : false);
				System.out.println("Car space availability changed to " + (property.isCarSpace() ? "Yes" : "No") + ".");
			}
		}
		
		System.out.print("Do you want to change the type of property ("+ property.getType() + ") (Y/N): ");
		char typeEdit = scan.nextLine().toUpperCase().charAt(0);
		if	(typeEdit == 'Y') {
			System.out.print("Enter type of property (1 - House, 2 - Unit, 3 - Flat, 4 - Town House, 5 - Studio): ");
			char type = scan.nextLine().toUpperCase().charAt(0);
			if	(	type != Constants.top_House
				&&	type != Constants.top_Unit
				&&	type != Constants.top_Flat
				&&	type != Constants.top_TownHouse
				&&	type != Constants.top_Studio) {
				System.out.println("Invalid type! Please enter 1 - House, 2 - Unit, 3 - Flat, 4 - Town House, 5 - Studio.");
				return;
			} else {
				property.setType(type);
				System.out.println("Type of property changed to " + (Constants.getypeOfProperty(property.getType())) + ".");
			}
		}
		
		double price = 0.0;
		if (property instanceof NegotiationSaleProperty) {
			System.out.print("Do you want to change the minimum selling preice ("+ property.getPrice() + ") (Y/N): ");
			char pEdit = scan.nextLine().toUpperCase().charAt(0);
			if	(pEdit == 'Y') {
				System.out.print("Enter new minimum selling price: ");
				
				try {
					price = Double.parseDouble(scan.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid price. Please enter a valid price.");
					return;
				}
				
				((NegotiationSaleProperty) property).setPrice(price);				
			} 
		}

		if (property instanceof AuctionSaleProperty) {
			System.out.print("Do you want to change the auction date ("+ ((AuctionSaleProperty) property).getAuctionDate() + ") (Y/N): ");
			char adEdit = scan.nextLine().toUpperCase().charAt(0);
			if	(adEdit == 'Y') {
				System.out.print("Enter new auction date and time (dd/MM/yyyy HH:mm:ss): ");
				
				String date;
				date = scan.nextLine();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
				Date auctionDate;
				
				try {
					auctionDate = formatter.parse(date);
				} catch (Exception e) {
					System.out.println("Invalid date or time. Please enter in dd/MM/yyyy HH:mm:ss format.");
					return;
				}
				
				((AuctionSaleProperty) property).setAuctionDate(auctionDate);
			}
			
			Date currentDate = new Date();
			
			if (currentDate.before(((AuctionSaleProperty) property).getAuctionDate())) {
				double currResPrice = ((AuctionSaleProperty) property).getPrice();
				
				if	(	currResPrice != 0 
					||	((AuctionSaleProperty) property).isSellFail() == false) {
				
					System.out.print("Do you want to change the reserve price ("+ currResPrice + ") (Y/N): ");
					char pEdit = scan.nextLine().toUpperCase().charAt(0);
					if	(pEdit == 'Y') {
						System.out.print("Enter new reserve price ($10000 less than current reserve): ");
						
						try {
							price = Double.parseDouble(scan.nextLine());
						} catch (Exception e) {
							System.out.println("Invalid price. Please enter a valid price.");
							return;
						}
						
						if (currResPrice != 0) {	 
							if (price > (currResPrice - 10000.0)) {
								System.out.println("Any minimum reserve must be reduced by at least $10,000.");
								return;
							}
						}
						
						((AuctionSaleProperty) property).setMinReservePrice(price);		
					}
				}					
			}			
		}

		if (property instanceof RentalProperty) {
			System.out.print("Do you want to change the weekly rental price ("+ property.getPrice() + ") (Y/N): ");
			char wrEdit = scan.nextLine().toUpperCase().charAt(0);
			if	(wrEdit == 'Y') {
				System.out.print("Enter weekly rental price: ");
				
				try {
					price = Double.parseDouble(scan.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid price. Please enter a valid price.");
					return;
				}
				
				((RentalProperty) property).setPrice(price);
			}
			
			System.out.print("Do you want to change the contract duration ("+ ((RentalProperty) property).getContractDurationMonths() + ") (Y/N): ");
			char cdEdit = scan.nextLine().toUpperCase().charAt(0);
			if	(cdEdit == 'Y') {
				int duration;
				System.out.print("Enter new acceptable cotract duration (in months): ");
				
				try {
					duration = Integer.parseInt(scan.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid entry. Please enter duration in months.");
					return;
				}
				
				((RentalProperty) property).setContractDurationMonths(duration);
			}			
		}
		
		System.out.println("Property successfully edited with Id " + propertyId + ".");
	}

	
	private String generatePropertyId(char prefix) {
		String highestId = " ";
		String nextId;
		
		for (String key: propertyMap.keySet()) {
			if (key.charAt(0) == prefix) {
				if (key.compareTo(highestId) > 0)
					highestId = key;
			}
		}
		
		DecimalFormat df = new DecimalFormat("000");
		int seq;
		
		if (highestId.compareTo(" ") != 0) {
			seq = Integer.parseInt(highestId.substring(1, 4));
			seq++;
		} else {
			seq = 1;
		}
		
		nextId = prefix + df.format(seq);			
		return nextId;
	}
	
	
	private void viewProperty() {
		ArrayList<Property> property;
		property = ((Owner) loginCustomer).getProperty();
		
		System.out.println("********************  Property details  ********************");
		
		for (int i = 0; i < property.size(); i++) {
			System.out.println(property.get(i) + "\n");
		}
		
		System.out.println("********************  End of Details  **********************");
	}
	
	
	private void approveHours() {
		System.out.print("Enter reportee Id whose worked hours to be approved: ");
		String empId = scan.nextLine().toUpperCase();
		
		Employee reportee = employeeMap.get(empId);
		if (reportee == null) {
			System.out.println("No employee found! Please enter valid employee Id.");
		} else {
			System.out.println("No. of hours entered by employee for current week: " + reportee.getNoOfHours());
			System.out.print("Do you approve(Y) or deny(N): " );
			char response = scan.nextLine().toUpperCase().charAt(0);
			
			if (response == 'Y') {
				try {
					((BranchManager) loginEmployee).approveHours(reportee);
					System.out.println("Hours of the reportee " + empId + " approved.");
				} catch (Exception e) {
					System.out.println("Failed to approve the hours.");
					System.out.println(e.getMessage());
				}
			} else {
				reportee.setHoursChecked(true);
				reportee.setHoursApproved(false);
				System.out.println("Hours of the reportee " + empId + " denied.");
			}
		}
	}
	
	
	private void assignEmployee() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		System.out.print("Enter employee Id of assignee: ");
		String empId = scan.nextLine().toUpperCase();
		
		Employee employee = employeeMap.get(empId);
		if (employee == null) {
			System.out.println("Employee not found! Please enter a valid employee Id.");
			return;
		}
		
		try {
			property.setAssignedEmployee(employee);
			property.setStatusOfProperty(Constants.sop_Active);
			property.sendCustomerNtf(customerMap, true);
			System.out.println("Employee " + empId  + " assigned to property " + propertyId + ".");
		} catch (Exception e) {
			System.out.println("Failed to assign employee to the property.");
			System.out.println(e.getMessage());
		}		
	}
	
	
	private void viewAllProperties() {
		System.out.println("********************  Property details  ********************");
		
		for (Property property : propertyMap.values()) {
			System.out.println(property + "\n");
		}
		
		System.out.println("********************  End of Details  **********************");
	}
	
	
	private void viewEmployees() {
		System.out.println("********************  Employees details  ********************");
		
		for (Employee employee : employeeMap.values()) {
			System.out.println(employee + "\n");
		}
		
		System.out.println("********************  End of Details  **********************");		
	}
	
	
	private void addReportees() {
		System.out.print("Enter employee Id of a reportee: ");
		String empId = scan.nextLine().toUpperCase();
		
		Employee reportee = employeeMap.get(empId);
		
		if (reportee == null) {
			System.out.println("No employee found! Please enter a valid employee Id.");
		} else {
			((BranchManager) loginEmployee).addReportee(reportee);
			System.out.println("Employee " + empId + " added successfully to your reportee list.");
		}			
	}
	
	
	private void viewReportees() {
		ArrayList<Employee> reportees = ((BranchManager) loginEmployee).getReportees();
		
		System.out.println("********************  Reportees details  ********************");
		
		for (int i = 0; i < reportees.size(); i++) {
			System.out.println(reportees.get(i) + "\n");
		}
		
		System.out.println("********************  End of Details  **********************");		
	}

	
	private void enterPartTimeHours() {
		System.out.print("Enter no. of hours worked for current week: ");
		int noOfHours;
		
		try {
			noOfHours = Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid number of hours. Please enter a valid number.");
			return;
		}
		
		loginEmployee.setNoOfHours(noOfHours);
		System.out.println("No. of hours entered successfully.");
	}
	
	
	private void setSalesCommission() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getAssignedEmployee().geteId().compareTo(empLogId) != 0) {
			System.out.println("Property not assigned to you. Only assigned Sales Consultant can set the commission.");
			return;
		}
		
		if (!(property instanceof SaleProperty)) {
			System.out.println("Property not a sale property! Sales commission can be set only for sale property.");
			return;
		}
		
		System.out.print("Enter sales commission(%): ");
		
		double salesCommission;
		try {
			salesCommission = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid percentage! Please enter a valid percentage.");
			return;
		}
		
		try {
			((SaleProperty) property).setSalesCommission(salesCommission);
			System.out.println("Sales commission of property " + propertyId + " updated.");
		} catch (Exception e) {
			System.out.println("Failed to update sales commission!");
			System.out.println(e.getMessage());
			return;
		}
	}

	
	private void scheduleInspection() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getAssignedEmployee().geteId().compareTo(empLogId) != 0) {
			System.out.println("Property not assigned to you. Only assigned employee can schedule an inspection.");
			return;
		}
		
		if (property.getStatusOfProperty() != Constants.sop_Active) {
			System.out.println("Property not available any more.");
			return;
		}
		
		System.out.print("Enter inspection date and time (dd/MM/yyyy HH:mm:ss): ");
		
		String date;
		date = scan.nextLine();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date inspectionDate;
		
		try {
			inspectionDate = formatter.parse(date);
		} catch (Exception e) {
			System.out.println("Invalid date or time. Please enter in dd/MM/yyyy HH:mm:ss format.");
			return;
		}
		
		try {
			property.setInspection(inspectionDate);
			property.sendCustomerNtf(customerMap, false);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to schedule the inspection.");
			return;
		}
		
		System.out.println("Inspection scheduled for property " + propertyId + " on " + inspectionDate + ".");		
	}
	
	
	private void selectBidder() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getAssignedEmployee().geteId().compareTo(empLogId) != 0) {
			System.out.println("Property not assigned to you. Only assigned employee can select the highest bidder.");
			return;
		}
		
		if (property.getStatusOfProperty() != Constants.sop_Active) {
			System.out.println("Property not available any more.");
			return;
		}
		
		if (!(property instanceof AuctionSaleProperty)) {
			System.out.println("Failed! Property is not meant to sell through auction.");
			return;
		}
		
		try {
			((AuctionSaleProperty) property).selectBidder(customerMap);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Highest bidder is selected and notified.");
	}
	
	
	private void setManagementRate() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getAssignedEmployee().geteId().compareTo(empLogId) != 0) {
			System.out.println("Property not assigned to you. Only assigned employee can set management rate.");
			return;
		}
		
		if (!(property instanceof RentalProperty)) {
			System.out.println("Failed! Property is not a rental property.");
			return;
		}
		
		System.out.print("Enter management rate(%): ");
		
		double mRate;
		try {
			mRate = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid percentage! Please enter a valid percentage.");
			return;
		}
		
		((RentalProperty) property).setManagementFeeRate(mRate);
		System.out.println("Management rate of property " + propertyId + " updated.");			
	}
	
	
	private void calculateSalary() {
		System.out.print("Enter employee Id of Sales Consultant: ");
		String empId = scan.nextLine().toUpperCase();
		
		Employee employee = employeeMap.get(empId);
		if (employee == null) {
			System.out.println("Employee not found! Please enter a valid employee Id.");
			return;
		}
		
		if (!(employee instanceof SalesConsultant)) {
			System.out.println("Employee is not a Sales Consultant.");
			return;
		}
		
		try {
			((SalesConsultant) employee).calculateGrossSalary();
			System.out.println("Gross salary of Sales Consultant " + empId + ":" + ((SalesConsultant) employee).getGrossSalary() + ".");
		} catch (Exception e) {
			System.out.println("Failed to calculate gross salary of Sales Consultant " + empId + ".");
			System.out.println(e.getMessage());
		}
	}

	
	private void makeOffer() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (!(property instanceof SaleProperty)) {
			System.out.println("Offer / bid can be made only on a property intended to sell.");
			return;
		}
		
		System.out.print("Enter offer / bid amount: ");
		
		double offerAmount;
		try {
			offerAmount = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid amount! Please enter a valid amount.");
			return;
		}
						
		try {
			((SaleProperty) property).createOffer(cusLogId, offerAmount);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Success! An offer / bid is made.");
	}

	
	private void viewOffer() {
		System.out.print("Enter propety Id to view its offers: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getOwner().getCustomerId().compareTo(cusLogId) != 0) {
			System.out.println("Failed! Only owner of a property can view its offers.");
			return;
		}
		
		StringBuilder sbt = new StringBuilder();
		System.out.println("********************  Offer / Application Details  ********************");
		
		if (property instanceof NegotiationSaleProperty) {
			LinkedHashMap<String, Offer> offerMap = ((NegotiationSaleProperty) property).getOffer();
			for (String buyerId : offerMap.keySet()) {
				System.out.println(sbt.append("Buyer Id: " + buyerId + "\n" + offerMap.get(buyerId))) ;
			}
		}
		
		if (property instanceof RentalProperty) {
			LinkedHashMap<String, Application> appMap = ((RentalProperty) property).getAppMap();
			for (String buyerId : appMap.keySet()) {
				System.out.println(sbt.append("Applicant Id: " + buyerId + "\n" + appMap.get(buyerId))) ;
			}
		}
		
		System.out.println("********************  End of Offer / Application **********************");
	}
	
	
	private void manageOffer() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property.getOwner().getCustomerId().compareTo(cusLogId) != 0) {
			System.out.println("Failed! Only owner of a property can manage its offers.");
			return;
		}
		
		System.out.print("Enter customer Id of buyer who made the offer / application: ");
		String buyerId = scan.nextLine().toUpperCase();
		
		System.out.print("Do you accept (A) or reject (R) the offer / application?: ");
		char offerMade = scan.nextLine().toUpperCase().charAt(0);
		if	(	offerMade != 'A'
			&&	offerMade != 'R') {
			System.out.println("Invalid entry! Please enter 'A' - Accept or 'R' - Reject.");
			return;
		}
		
		char status = (offerMade == 'A' ? Constants.offer_Accepted : Constants.offer_Rejected);
		try {
			if (property instanceof NegotiationSaleProperty) {			
				((NegotiationSaleProperty) property).manageOffer(buyerId, status, customerMap);
			}
			
			if (property instanceof RentalProperty) {
				((RentalProperty) property).manageApplication(buyerId, status, customerMap);
			}
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Success! Offer / application processed.");
	}
	

	private void withdrawOffer() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (property instanceof AuctionSaleProperty) {
			System.out.println("This property is meant to sell through auction. Offer / application not valid");
			return;
		}
		
		try {
			if (property instanceof NegotiationSaleProperty) {
				((NegotiationSaleProperty) property).withdrawOffer(cusLogId);
			}
			
			if (property instanceof RentalProperty) {
				((RentalProperty) property).withdrawApplication(cusLogId);
			}
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Offer / Application successfully withdrawn.");		
	}


	private void makeApplication() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		if (!(property instanceof RentalProperty)) {
			System.out.println("Application can be made only on a property intended to rent.");
			return;
		}
		
		System.out.print("Enter weekly rental: ");		
		double rentAmount;
		try {
			rentAmount = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid amount! Please enter a valid amount.");
			return;
		}
		
		System.out.print("Enter contract duration in months: ");		
		int duration;
		try {
			duration = Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid months! Please enter a valid no. of months.");
			return;
		}
						
		try {
			((RentalProperty) property).createApplication(cusLogId, rentAmount, duration);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Success! An application is made.");	
	}

	
	private void makePayment() {
		System.out.print("Enter propety Id: ");
		String propertyId = scan.nextLine().toUpperCase();
		
		Property property = propertyMap.get(propertyId);
		if (property == null) {
			System.out.println("Property not found! Please enter a valid property Id.");
			return;
		}
		
		System.out.print("Enter payment amount: ");
		double offerAmount;
		try {
			offerAmount = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println("Invalid amount! Please enter a valid amount.");
			return;
		}
		
		try {
			property.processPayment(cusLogId, offerAmount);
			property.setStatusOfProperty(Constants.sop_ContractLet);
			property.setInspection(null);
			property.sendCustomerNtf(customerMap, false);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("Success! Down payment done.");
	}
	
	
	private void viewNotification() {
		LinkedHashMap<String, Notification> ntfMap = loginCustomer.getNtfMap();		
		
		System.out.println("********************  Notification details  *****************");
		
		for (String key : ntfMap.keySet()) {
			System.out.println(ntfMap.get(key));
		}
		
		System.out.println("********************  End of Details  ***********************");				
	}
	
	
	private void deleteNotification() {
		System.out.print("Enter notification Id: ");
		String ntfId = scan.nextLine().toUpperCase();
		
		loginCustomer.removeNtf(ntfId);		
		System.out.println("Success! Notification " + ntfId + " removed.");
	}
	
	
	public void processFile()
	{
		try {
			processCustomerFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			processPropertyFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			processEmployeeFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	@SuppressWarnings("unchecked")
	public void processCustomerFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream cusInput = new ObjectInputStream(new FileInputStream("customers.txt"));
		customerMap = (HashMap<String, Customer>) cusInput.readObject();
		cusInput.close();
	}

	
	@SuppressWarnings("unchecked")
	public void processPropertyFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream propInput = new ObjectInputStream(new FileInputStream("properties.txt"));
		propertyMap = (HashMap<String, Property>) propInput.readObject();
		propInput.close();
	}

	
	@SuppressWarnings("unchecked")
	public void processEmployeeFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream empInput = new ObjectInputStream(new FileInputStream("employees.txt"));
		employeeMap = (HashMap<String, Employee>) empInput.readObject();
		empInput.close();
	}

	
	public void processWriteFile() {
		try {
			writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile() throws IOException  {
		ObjectOutputStream cusOutput = new ObjectOutputStream(new FileOutputStream("customers.txt"));
		cusOutput.writeObject(customerMap);
		cusOutput.close();
		
		ObjectOutputStream propOutput = new ObjectOutputStream(new FileOutputStream("properties.txt"));
		propOutput.writeObject(propertyMap);
		propOutput.close();
		
		ObjectOutputStream empOutput = new ObjectOutputStream(new FileOutputStream("employees.txt"));
		empOutput.writeObject(employeeMap);
		empOutput.close();		
	}
}
