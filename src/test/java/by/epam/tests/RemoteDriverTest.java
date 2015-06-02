package by.epam.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class RemoteDriverTest {
	
	private final String SO = "http://www.stackoverflow.com";
	private final String HUB = "http://localhost:4444/wd/hub";

	@Test(threadPoolSize = 1, invocationCount = 3, timeOut = 30 * 1000)
	public void oneCanExecuteRemoteTest() throws MalformedURLException
	{
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName("firefox");
		dc.setPlatform(Platform.WINDOWS);

		WebDriver driver = new RemoteWebDriver(new URL(HUB), dc);

		driver.get(SO);
		driver.findElement(By.id("nav-users")).click();

	}

}
