package com.pageObjects;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utils.WebElementUtils;

public class LoginPage {

	@FindBy(id="session_key") WebElement userName;
	@FindBy(id="session_password") WebElement password;
	@FindBy(xpath="//button[@type=\"submit\"]") WebElement signIn;
	@FindBy(id="ember49") WebElement icon;

	WebElementUtils webUtils=new WebElementUtils();
	private WebDriver driver;
	private ExtentTest test;

	public LoginPage(WebDriver driver, ExtentTest test) {
		this.test=test;
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		test.log(LogStatus.PASS, "LoginPage constructor is created"); 
	}	

	public void loginFlow(String uname, String pass) throws IOException {

		try {
			enterUserName(uname);
			test.log(LogStatus.PASS, "UserName entered sucessfully"); 
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Failed to enter UserName"); 
		}
		
		try {
			enterPassword(pass);
			test.log(LogStatus.PASS, "Password entered sucessfully"); 
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Failed to enter Password"); 
		}
		
		clickOnLoginButton();

		if(icon.isDisplayed()) {
			//To check whether Home icon is displayed or not (Displays only when sucessfully logged in ) 
			
			System.out.println("Username : "+uname+" , Password : "+pass+" are valid credentials");
			test.log(LogStatus.PASS, "Successfully Logged in"); 
		}
	}
	
	public void enterUserName(String uName) {
		webUtils.sendKeys(driver, userName, uName);
	}

	public void enterPassword(String pass) {
		webUtils.sendKeys(driver, password, pass);
	}

	public void clickOnLoginButton() {
		webUtils.webElementClick(driver, signIn);
	}

}


