package realestate;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class SalesConsultantTest {
	SalesConsultant sc;
	ArrayList<Property> properties;
	
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
		SaleProperty sproperty;
		
		Owner owner = new Vendor("Brad Pitt", "bradpitt@gmail.com", 1, "V001", "1");
		Owner landlord = new Landlord("Brad Pitt", "bradpitt@gmail.com", 2, "L001", "1");
		
		properties = new ArrayList<Property>();
		properties.add(new NegotiationSaleProperty("S001", "131 Edwin Street", "Heidelberg", 3, 2, true, Constants.top_House, owner, 180000));
		properties.add(new RentalProperty("R001", "1/7 Kinnear Street", "Footscray", 5, 3, false, Constants.top_House, landlord, 600, 12));
		
		properties.add(new NegotiationSaleProperty("S002", "12 Cardigen Street", "Fitzroy", 3, 2, true, Constants.top_House, owner, 200000));
		sproperty = (SaleProperty) properties.get(2);
		sproperty.setSalesCommission(2.0);
		sproperty.setStatusOfProperty(Constants.sop_ContractLet);
		
		properties.add(new NegotiationSaleProperty("S003", "101 Ballarat Road", "Footscray", 6, 2, false, Constants.top_House, owner, 100000));
		sproperty = (SaleProperty) properties.get(3);
		sproperty.setSalesCommission(5.0);
		sproperty.setStatusOfProperty(Constants.sop_ContractLet);
		
		System.out.println("\nBefore");
		System.out.println(sc.getGrossSalary());
	}
	
	
	@After
	public void tearDown() throws Exception {
		System.out.println("After");
		System.out.println(sc.getGrossSalary());
	}


	@Test 
	public void test1() throws EmployeeException{
		sc.addProperty(properties.get(0));
		
		try {
			sc.calculateGrossSalary();
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
		
	}
	

	@Test 
	public void test2() throws EmployeeException{
		sc.addProperty(properties.get(1));
		
		try {
			sc.calculateGrossSalary();
		} catch (EmployeeException e) {
			System.out.println(e.getMessage());
		}
		
	}	

	
	@SuppressWarnings("deprecation")
	@Test 
	public void test3() throws EmployeeException{
		sc.addProperty(properties.get(2));
		
		sc.calculateGrossSalary();
		assertEquals(2600.0, sc.getGrossSalary(), 0.001);
		
	}	
	
	
	@Test 
	public void test4() throws EmployeeException{
		sc.addProperty(properties.get(2));
		sc.addProperty(properties.get(3));
		
		sc.calculateGrossSalary();
		assertEquals(4600.0, sc.getGrossSalary(), 0.001);
		
	}		
}
