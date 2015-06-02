package by.epam.webdriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class Driver {
	
	private static WebDriver driver;
	private final static String HUB = "http://localhost:4444/wd/hub";

	public static WebDriver get() {
		return driver;
	}
	
	public static void set(WebDriver driverInput) {
        driver = driverInput;
    }
	
	public static void init() throws MalformedURLException {
        Properties properties = new Properties();
        FileInputStream propFile;
        try {
            propFile = new FileInputStream("test.properties");
            properties.load(propFile);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        @SuppressWarnings("unchecked")
        Enumeration<String> e = (Enumeration<String>) properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            System.setProperty(key, properties.getProperty(key));
            Reporter.log(key + " - " + properties.getProperty(key), 2, true);
        }
        WebDriver driverInput;
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setPlatform(Platform.WINDOWS);
        switch (System.getProperty("test.browser")) {
        
            case "firefox":
            	dc.setBrowserName("firefox");
        		driverInput = new RemoteWebDriver(new URL(HUB), dc);
//            	driverInput = new FirefoxDriver();
                break;
                
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "d:\\chromedriver.exe");
                dc.setBrowserName("chrome");
        		driverInput = new RemoteWebDriver(new URL(HUB), dc);
//              driverInput = new ChromeDriver();
                break;
         
            default:
                throw new AssertionError("Unsupported browser: " + System.getProperty("test.browser"));
        }
        driverInput.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driverInput.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Driver.set(driverInput);
    }

    public static void tearDown() {
        Driver.get().quit();
    }

}
