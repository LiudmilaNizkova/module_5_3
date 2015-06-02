package by.epam.tests;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import by.epam.pages.YandexMailBoxPage;
import by.epam.pages.YandexMainPage;
import by.epam.webdriver.Driver;

public class YandexMailTest extends BaseTest{
	
	private final String UserLogin = "ludik.ludik2015";
	private final String UserPsw = "Qwerty111";
	private final String Subject = "test module 5";
	private final String Body = "Hello!\n\nThis is test of Module 5.\n\nBye";
	private final String LoggedUser  = "ludik.ludik2015@yandex.ru";
	private WebDriver driver;
	
	@Test (threadPoolSize = 2, invocationCount = 4)
	public void testYandexMailBox(){
		driver = Driver.get();
		YandexMainPage mainPage = new YandexMainPage(driver);
		mainPage.openPage();
		mainPage.loginToMail(UserLogin, UserPsw);
		YandexMailBoxPage homePage = new YandexMailBoxPage(driver);
		homePage.getLoggedUserName();
		Assert.assertTrue(homePage.getLoggedUserName().equals(LoggedUser), "Verification Failed: You are not logged under user "+UserLogin);
//		System.out.println("Logged User = "+homePage.getLoggedUserName());
		homePage.writeNewEmail(Subject, Body);
		homePage.openDraftFolder();
		homePage.saveEmailToDraft();
		Assert.assertTrue(homePage.getTitleOfEmail().equals(Subject), "Verification Failed: No email in DRAFT folder with subject= "+Subject);
		homePage.sendNewEmail();
		homePage.openDraftFolder();
		Assert.assertFalse(homePage.getTitleOfEmail().equals(Subject), "Verification Failed: Email is still in DRAFT folder with subject= "+Subject);
		homePage.openSentFolder();
		Assert.assertTrue(homePage.getTitleEmailInSentFolder().equals(Subject), "Verification Failed: No email in SENT folder with subject= "+Subject);
		homePage.quitFromMailBox();
	}
	

}
