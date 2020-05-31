package realestate;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BranchManagerTest {
	BranchManager bm;
	SalesConsultant partSc, fullSc;

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
		bm = new BranchManager("Scott", "BM01", "123", "Branch Manager", false);
		partSc = new SalesConsultant("Grace", "SC02", "123", "Sales Consultant", true, 1000);
		fullSc = new SalesConsultant("Grace", "SC02", "123", "Sales Consultant", false, 1000);
		
		System.out.println("\nBefore");
		System.out.println("Hours approved: " + (partSc.isHoursApproved()? "Yes" : "No"));
		System.out.println("Hours approved: " + (fullSc.isHoursApproved()? "Yes" : "No"));
	}
	
	
	@After
	public void tearDown() throws Exception {
		System.out.println("After");
		System.out.println("Hours approved: " + (partSc.isHoursApproved()? "Yes" : "No"));
		System.out.println("Hours approved: " + (fullSc.isHoursApproved()? "Yes" : "No"));
	}


	@Test (expected=EmployeeException.class)
	public void test1() throws EmployeeException{
		
		try {
			bm.approveHours(partSc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test (expected=EmployeeException.class)
	public void test2() throws EmployeeException{
		bm.addReportee(partSc);
		
		try {
			bm.approveHours(partSc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}
	

	@Test (expected=EmployeeException.class)
	public void test3() throws EmployeeException{
		bm.addReportee(fullSc);
		((Employee) fullSc).setNoOfHours(40);
		
		try {
			bm.approveHours(fullSc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	
	@Test 
	public void test4() throws EmployeeException{
		bm.addReportee(partSc);
		((Employee) partSc).setNoOfHours(40);
		
		try {
			bm.approveHours(partSc);
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
		
	}	
}
