package FlipkartPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import FlipKart_Test.configuration;

public class HomePage {

	WebDriver ldriver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor executor;

	public HomePage(WebDriver rdriver) {

		ldriver = rdriver;

		PageFactory.initElements(rdriver, this);
		wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

		executor = (JavascriptExecutor) ldriver;

		act = new Actions(ldriver);

	}

	@FindBy(xpath = "//input[@class='Pke_EE']")
	WebElement SearchBox;

	String Search_text = "//div[text()='%s']";

	public void SearchBox(String search) {
		SearchBox.sendKeys(search);
	}

	public void click_Search(String SearchName) {

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(Search_text, SearchName))));
		ldriver.findElement(By.xpath(String.format(Search_text, SearchName))).click();

	}

	public void Search_Iteam() {

		SearchBox(configuration.SEARCH_QUERY);
		click_Search(configuration.click_Search);
	}
}
