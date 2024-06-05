package FlipKart_Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.Get;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import FlipKartReport.ReportManager;
import FlipkartPage.CartPage;
import FlipkartPage.HomePage;
import FlipkartPage.ProductPage;
import FlipkartPage.SearchProductPage;
//import RedBusIn.RedBuspage;
//import RedBusIn.ReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipKart_Test {

	private WebDriver driver;
	private ExtentTest test;

	@BeforeTest
	public void InializeDriver() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(configuration.BASE_URL);

		driver.manage().deleteAllCookies();

		driver.manage().window().maximize();

		ReportManager.getReport();
	}

	@Test
	public void Flipkart() throws InterruptedException {

		HomePage Fk = new HomePage(driver);
		SearchProductPage sp = new SearchProductPage(driver);
		ProductPage pg = new ProductPage(driver);
		CartPage cg = new CartPage(driver);

		test = ReportManager.generateTest("FlipKart Search Result");
		test.log(Status.INFO, "Step 1 Open the Flipkart website.");

		test.log(Status.INFO, "Step 2 Search for mobile phones in the search bar..");

		Fk.Search_Iteam();
		test.log(Status.INFO, "Search item Performed");

		test.log(Status.INFO,
				"Step 3 Filter the mobile phones with certain criteria such as brand name (Samsung) using the search brand field box, RAM (8 GB Above), and price range (20,000-30,000+) using the price slider functionality.");

		sp.ApplyFilter();
		test.log(Status.INFO, "Filter Applied Sucessfully");

		test.log(Status.INFO, "Step 4 Print the cheapest and costliest phone with its name and price");

		test.log(Status.INFO,
				"Cheapest Phone Name " + sp.GetCheapestPhone()[0] + " And Cost is " + sp.GetCheapestPhone()[1]);

		test.log(Status.INFO,
				"Costliest Phone Name " + sp.GetCostliestPhone()[0] + " And Cost is " + sp.GetCostliestPhone()[1]);

		test.log(Status.INFO, "Step 5 Find the first phone that meets the criteria within the price range.");
		String ItemName = sp.GetFirstItem();

		test.log(Status.INFO, "Step 6 Click on the first phone.");
		sp.ClickonItem();

		test.log(Status.INFO, "Step 7 Verify that the product details page is displayed.");
		Assert.assertEquals(pg.GetSelectedItem_Name().startsWith(ItemName), true);

		test.log(Status.INFO, "Step 8 Check the product is in stock.");
		Assert.assertFalse(pg.Is_item_inStock(), "Item is out of Stock");

		test.log(Status.INFO, "Step 9 Add the product to the cart");
		pg.Add_Tocart();

		test.log(Status.INFO,
				"Step 10 Verify that the product is successfully added to the cart or not. Validate the amount including any discounts.");

		String DisAmount = pg.PriceAfterDiscount();

		String discount = pg.getDiscountPrice();

		Assert.assertEquals(cg.Validate_ItemNameonCart(), ItemName);

		Assert.assertEquals(cg.ValidateDiscount(), discount);

		Assert.assertEquals(cg.Validate_TotalAmount(), DisAmount);

		test.log(Status.INFO,
				"Step 11 Increase the quantity of the product by 1 using the number input and verify the popup text");

		cg.Iteam_Incerese();
		String pop = cg.PopupMessage();

		Assert.assertEquals(pop, String.format(configuration.MESSAGE_INCREASE_QUANTITY, ItemName));

		cg.WaitTillPopup_Disappear();

		test.log(Status.INFO, "Step 12 Then decrease the quantity by using the (-) sign");

		cg.Decrese_Quantites();

		pop = cg.PopupMessage();
		Assert.assertEquals(pop, String.format(configuration.MESSAGE_DECREASE_QUANTITY, ItemName));

		cg.WaitTillPopup_Disappear();

		test.log(Status.INFO,
				"Step 13 Remove the product from the cart and verify the product is successfully removed.");

		cg.Click_RemoveButton();

		cg.PopUp_RemoveButton();

		pop = cg.PopupMessage();
		Assert.assertEquals(pop, String.format(configuration.MESSAGE_REMOVE_FROM_CART, ItemName));

	}

	@AfterMethod
	public void ScreenShotGenerate(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String TimeStamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());

			String screenshotPath = ReportManager.takeScreenshot(driver, result.getName() + TimeStamp);
			test.fail(result.getThrowable());
			test.addScreenCaptureFromPath(screenshotPath);
		}
	}

	@AfterTest
	public void cleanUpReport() {

		if (driver != null) {
			driver.quit();
		}
		ReportManager.flush();
	}

}
