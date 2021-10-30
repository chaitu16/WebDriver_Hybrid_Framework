package com.qtpselenium.test;

import static com.qtpselenium.test.DriverScript.APP_LOGS;
import static com.qtpselenium.test.DriverScript.config;
import static com.qtpselenium.test.DriverScript.or;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Keywords {
	
	public WebDriver driver;
	
	public String openBrowser(String object, String data) {
		
		System.out.println("Keyword java data is "+ data);
		
		
		
		if(config.getProperty("browserType").equals("Chrome")) {
			ChromeOptions crops = new ChromeOptions();
			//crops.setPageLoadStrategy(PageLoadStrategy.EAGER);
			crops.addArguments("--disable-notifications");
			crops.addArguments("--start-maximized");
			crops.addArguments("ignore-certificate-erors");	

			driver = new ChromeDriver(crops);
			
		
		}else if (config.getProperty(data).equalsIgnoreCase("Firefox")) {
			FirefoxOptions fop = new FirefoxOptions();
			//fop.setPageLoadStrategy(PageLoadStrategy.EAGER);
			FirefoxProfile fProfile = new FirefoxProfile();
			fProfile.setPreference("dom.webnotifications.enabled", false);
			fop.setProfile(fProfile);
	
			driver = new FirefoxDriver(fop);
			
		}else if (config.getProperty(data).equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
		//	options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			//options.setBinary(new File(""));
			options.addArguments("--disable-notifications");
			options.addArguments("--start-maximized");
			options.addArguments("ignore-certificate-errors");
	
			driver = new EdgeDriver(options);
			
		}else if (config.getProperty(data).equalsIgnoreCase("ie")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			//options.setBinary
			//options.setPageLoadStrategy(strategy)
		//	options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			
			DesiredCapabilities cap = new DesiredCapabilities();
			
			/*String proxy="82.288.287.22:8080";
			Proxy p = new Proxy();
			p.setAutodetect(false);
			p.setProxyType(Proxy.ProxyType.MANUAL);
			p.setSocksProxy(proxy);
			
			*/
			
			//cap.setCapability(CapabilityType.PROXY, p);			
			options.merge(cap);	
						
			driver = new InternetExplorerDriver();
			
			
		}
		
	
		return Constants.KEYWORD_PASS;
		
	}
	
	public String navigate(String object, String data) {
		APP_LOGS.debug("Navigate to URL");
		try {
		driver.navigate().to(config.getProperty(data));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
		
		}catch(Exception e) {
			System.out.println(e.getStackTrace()+ "Did not navigate to url");
			return Constants.KEYWORD_FAIL;
		}
		
		return Constants.KEYWORD_PASS;
	}
	
	public String clickLink(String object, String data) {
		
		APP_LOGS.debug("Click Link");
		
		driver.findElement(By.cssSelector(or.getProperty(object))).click();
		
		return Constants.KEYWORD_PASS;
		
	}
	
	public String clickButton(String object, String data) throws InterruptedException {
		
		APP_LOGS.debug("Click Button");
		driver.findElement(By.cssSelector(or.getProperty(object))).click();
		driverWait();
		return Constants.KEYWORD_PASS;
	}
	
	public String writeInInput(String object, String data) {
		APP_LOGS.debug("Send keys ");
		
		driver.findElement(By.xpath(or.getProperty(object))).sendKeys(data);
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyLinkText() {
		APP_LOGS.debug("LinkText Verification");
		return Constants.KEYWORD_PASS;
		
	}
	public String verifyText() {
		
		APP_LOGS.debug("Verifying Text Method");
		
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public String verifyTitle() {
		APP_LOGS.debug("Verify Title");
		return Constants.KEYWORD_PASS;
	}
	
	
	public String selectLink() {
		
		APP_LOGS.debug("Select Link");
		return Constants.KEYWORD_PASS;
	}
	
	public String selectListItem() {
		APP_LOGS.debug("List Item Selection");
		return Constants.KEYWORD_PASS;
		
	}
	
	public String verifyListSelection() {
		APP_LOGS.debug("Verify selected list item or element");
		return Constants.KEYWORD_PASS;
	}
	
	public String click() {
		APP_LOGS.debug("Generic click");
		return Constants.KEYWORD_PASS;
		
	}
	
	public String verifyButtonText() {
		APP_LOGS.debug("Button text");
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyAllElements() {
		//select from list
		APP_LOGS.debug("All elements from list being verified");
		return Constants.KEYWORD_PASS;
	}
	
	public String selectCheckbox() {
		APP_LOGS.debug("Check box select");
		return Constants.KEYWORD_PASS;
	}
	
	public String verifySelectedCheckbox() {
		APP_LOGS.debug("verifySelectedCheckbox");
		return Constants.KEYWORD_PASS;
	}
	
	public String clickImage() {
		APP_LOGS.debug("clickImage");
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyFileName() {
		APP_LOGS.debug("verifyFileName");
		return Constants.KEYWORD_PASS;
		//uploaded image or File
	}
	
	
	public String selectRadio() {
		APP_LOGS.debug("selectRadio");
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyRadioSelected() {
		APP_LOGS.debug("verifyRadioSelected");
		return Constants.KEYWORD_PASS;
	
	}
	
	public String waitForElementVisibility() {
		APP_LOGS.debug("waitForElementVisibility");
		return Constants.KEYWORD_PASS;
	}
	
	public String verifyTextInput() {
		APP_LOGS.debug("verifyTextInput");
		return Constants.KEYWORD_PASS;
	}
	
	public String selectList() {
		APP_LOGS.debug("select List");
		return Constants.KEYWORD_PASS;
	}
	
	public void driverWait() throws InterruptedException {
		synchronized (driver){
			driver.wait(5000);			
		}
	}

	
}
	