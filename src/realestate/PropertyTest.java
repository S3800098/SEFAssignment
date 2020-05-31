package realestate;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PropertyTest {
	Property property[];
	SalesConsultant sc;
	PropertyManager pm;
	
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
		sc = new SalesConsultant("Beck", "SC01", "123", "Sales Consultant", false, 1000);
		pm = new PropertyManager("Max", "PM01", "123", "Property Manager", false);
		Owner owner = new Vendor("Brad Pitt", "bradpitt@gmail.com", 1, "V001", "1");
		Owner landlord = new Landlord("Brad Pitt", "bradpitt@gmail.com", 2, "L001", "1");
		
		property = new Property[2];
		property[0] = new NegotiationSaleProperty("S001", "131 Edwin Street", "Heidelberg", 3, 2, true, Constants.top_House, owner, 180000);
		property[1] = new RentalProperty("R001", "1/7 Kinnear Street", "Footscray", 5, 3, false, Constants.top_House, landlord, 600, 12);
		
		System.out.println("\nBefore");
	}
	
	
	@After
	public void tearDown() throws Exception {
		System.out.println("After");
		System.out.println("Property[0] Assigned Employee: " + property[0].getAssignedEmployee().geteId());
		System.out.println("Property[1] Assigned Employee: " + property[1].getAssignedEmployee().geteId());
	}


	@Test 
	public void test1() throws EmployeeException{
		
		try {
			property[0].setAssignedEmployee(pm);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Test 
	public void test2() throws EmployeeException{
		
		try {
			property[1].setAssignedEmployee(pm);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test 
	public void test3() throws EmployeeException{
		
		try {
			property[1].setAssignedEmployee(sc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Test 
	public void test4() throws EmployeeException{
		
		try {
			property[0].setAssignedEmployee(sc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}

}
