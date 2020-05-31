package realestate;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuctionSalePropertyTest {
	AuctionSaleProperty asp;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("BeforeClass");
	}

	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AfterClass");
	}

	
	@Before
	public void setUp() throws Exception {
		String date = "06/05/2020  13:20:00";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date auctionDate = formatter.parse(date);
		Owner owner = new Vendor("Brad Pitt", "bradpitt@gmail.com", 1, "V001", "1");
		asp = new AuctionSaleProperty("S001", "1/7 Kinnear Street", "Footscray", 5, 3, true, Constants.top_House, owner, auctionDate, 0.0);
		asp.createOffer("B001", 10000);
		System.out.println("\nBefore");
	}
	
	
	@After
	public void tearDown() throws Exception {
		System.out.println("After");
		System.out.println(asp.getInspection());
	}


	@Test 
	public void test1() throws PropertyException{
		String date = "07/05/2020 13:20:00";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		
		Date inspectionDate = null;
		try {
			inspectionDate = formatter.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			asp.setInspection(inspectionDate);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test 
	public void test2() throws PropertyException{
		String date = "05/05/2020 13:20:00";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		
		Date inspectionDate = null;
		try {
			inspectionDate = formatter.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			asp.setInspection(inspectionDate);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test 
	public void test3() throws PropertyException{
		try {
			asp.createOffer("B002", 9000.0);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
		}
	}	

	
	@Test 
	public void test4() throws PropertyException{
		try {
			asp.createOffer("B003", 11000.0);
		} catch (PropertyException e) {
			System.out.println(e.getMessage());
		}
	}	
}
