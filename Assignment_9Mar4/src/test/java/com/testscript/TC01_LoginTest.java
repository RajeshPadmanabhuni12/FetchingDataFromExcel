package com.testscript;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.pageObjects.LoginPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utils.ExcelUtils;
import com.utils.WebDriverUtils;

public class TC01_LoginTest {

	private WebDriver driver;
	Properties obj;
	FileInputStream objfile;
	LoginPage login;

	ExcelUtils excelUtils=new ExcelUtils();
	ArrayList<String> uname=new ArrayList<String>();
	ArrayList<String> pass=new ArrayList<String>();
	private File testReports;
	private File extentReports;
	private String extentReportsPath;
	private String timeStamp;
	private File timeStamp1;
	private ExtentReports report;
	private ExtentTest test;

	@BeforeSuite
	public void beforeSuite() throws IOException {
		String path="";

		// Making folder with name as testReports
		testReports=new File("testReports");
		if(!testReports.exists()) {
			
			testReports.mkdir();
		}
		path=testReports.getAbsolutePath();

		// Making folder with name as screenshots in testReports folder
		extentReports=new File(path+"/extentReports");
		if(testReports.exists() && (!extentReports.exists()))
		{
			extentReports.mkdir();
		}
		extentReportsPath=extentReports.getAbsolutePath();
	}	
	
	@BeforeTest
	@Parameters("browser")
	public void configure(String browser) throws Exception {
		
		if(testReports.exists() && extentReports.exists()) {

			// Time stamp creation
			timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			// Making folder with timestamp (ddMMyyyHHmmss) as nomenclature.
			timeStamp1=new File(extentReportsPath+"/"+timeStamp);
			timeStamp1.mkdirs();
			String timeStampPath = timeStamp1.getAbsolutePath();
			
			// Creating ExtentReportResults.html file in current timeStamp folder
			File extentReportsFile=new File(timeStampPath+"/ExtentReportResults.html");
			extentReportsFile.createNewFile();
			
			report = new ExtentReports(extentReportsFile.getAbsolutePath()); 
			test = report.startTest("LoginTest"); 
		}
		
		

		WebDriverUtils setup=new WebDriverUtils();
		try {
			driver=setup.setup(browser);
			test.log(LogStatus.PASS, "Browser Opened Sucessfully"); 
		} catch (Exception e) {
			test.log(LogStatus.PASS, "Unable to open Browser"); 
		}

		obj=new Properties();
		objfile=new FileInputStream(System.getProperty("user.dir")+"\\application.properties");
		obj.load(objfile);

		login=new LoginPage(driver, test);
	}

	public void launchUrl() {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		driver.get(obj.getProperty("baseurl"));
		
		if (driver.getTitle().equals("LinkedIn India: Log In or Sign Up")) { 
			test.log(LogStatus.PASS, "Navigated to correct URL"); 
		} else { 
			test.log(LogStatus.FAIL, "Navigated to wrong URL"); 
		} 
		
		driver.manage().window().maximize();
	}

	@Test(priority=0)
	@Parameters("environment")
	public void TestLoginPage(String environment) throws AWTException, IOException {

		String userName = null,password=null;

		try {
			uname=excelUtils.readExcel("Sheet1","UserName",environment);
			pass=excelUtils.readExcel("Sheet1","Password",environment);
			
			test.log(LogStatus.PASS, "Username and Password fields are fetched from excel"); 

		} catch (Exception e1) {
			test.log(LogStatus.FAIL, "Failed to fetch Username and Password fields from excel"); 
		}

		for(int i=0;i<uname.size() && i<pass.size() ;i++) {
			launchUrl();
			try {
				userName=uname.get(i);
				password=pass.get(i);
				login.loginFlow(userName,password);
			} catch (Exception e) {
				System.out.println("Username : "+userName+" , Password : "+password+" are invalid credentials");
				test.log(LogStatus.FAIL, "Login Failed.... Invalid Credentials"); 
			}
		}

		
		
		/*
		 * Comment all code and uncomment the commented code below
		 * To check how data from excel is read and stored in Hashmap
		 * 
		 */
		

		/*List<LinkedHashMap<String, String>> map=excelUtils.readExcel("Sheet1","DEV");

		for(int k=0; k<map.size();k++) {
			System.out.println("Data set "+ (k+1) +" : ");

			for(String s: map.get(k).keySet()) {
				System.out.println("value of "+s+" is : "+map.get(k).get(s));
			}
			System.out.println("===============================================");
		}*/
	}


	@AfterTest
	public void teardown() throws IOException {
		objfile.close();
		driver.close();
		report.endTest(test); 

		report.flush(); 
	}

}
