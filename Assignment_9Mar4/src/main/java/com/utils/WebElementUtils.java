package com.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebElementUtils {

	public void webElementClick(WebDriver driver, By by){
		//Click function by taking By as parameter
		scrollingDownWindow(driver);
		driver.findElement(by).click();
	}

	public void sendKeys(WebDriver driver, By by, String keys) {
		//Send keys to field by taking By as parameter

		scrollingDownWindow(driver);
		dataClear(driver,by);
		driver.findElement(by).sendKeys(keys);
	}

	public String fetchText(WebDriver driver, By by) {
		//Send fetches text by taking By as parameter
		scrollingDownWindow(driver);
		String text= driver.findElement(by).getText();
		return text;
	}

	public void dataClear(WebDriver driver, By by) {
		//Data clear function by taking By as parameter
		scrollingDownWindow(driver);
		driver.findElement(by).clear();
	}

	public void scrollingDownWindow(WebDriver driver) {
		// Scrolling window to down by 20 pixels

		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,20)");

	}

	public void scrollingDownWindow(WebDriver driver, int xParam, int yParam) {
		// Customized Scrolling window to down by taking x and y parameters

		JavascriptExecutor js= (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy("+xParam+","+yParam+")");

	}

	public void webElementClick(WebDriver driver, WebElement element) {
		//Click function by taking WebElement  as parameter
		scrollingDownWindow(driver);
		element.click();
	}

	public void sendKeys(WebDriver driver, WebElement element, String keys) {
		//Send keys to field by taking WebElement as parameter
		scrollingDownWindow(driver);
		dataClear(driver,element);
		element.sendKeys(keys);
	}

	public String fetchText(WebDriver driver, WebElement element) {
		//Send fetches text by taking WebElement as parameter
		scrollingDownWindow(driver);
		String text= element.getText();
		return text;
	}

	public void dataClear(WebDriver driver, WebElement element) {
		//Data clear function by taking WebElement as parameter
		scrollingDownWindow(driver);
		element.clear();
	}
}

