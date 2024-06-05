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

public class CartPage {

	WebDriver ldriver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor executor;

	public CartPage(WebDriver rdriver) {

		ldriver = rdriver;

		PageFactory.initElements(rdriver, this);
		wait = new WebDriverWait(ldriver, Duration.ofSeconds(30));

		executor = (JavascriptExecutor) ldriver;

		act = new Actions(ldriver);

	}

	@FindBy(xpath = "//div[@class='gE4Hlh']/a")
	WebElement addedcartValidate;

	@FindBy(xpath = "//div[text()='Discount']//parent::div//following-sibling::*")
	WebElement DiscounttValidate;

	@FindBy(xpath = "//div[@class='_1Y9Lgu']/span")
	WebElement TotalAmount;

	@FindBy(xpath = "//div[@class='p2uyH2']/div/input[@type='text']")
	WebElement Increase_quantity;

	@FindBy(xpath = "//div[@class='eIDgeN']")
	WebElement PopUp;

	@FindBy(xpath = "//div[@class='p2uyH2']/child::button[1]")
	WebElement Decrese_quantity;

	@FindBy(xpath = "//div[text()='Remove']")
	WebElement RemoveButton;

	@FindBy(xpath = "//div[@class='row YDw732']//div[text()='Remove']")
	WebElement RemoveButton_popup;

	public String Validate_ItemNameonCart() {
		wait.until(ExpectedConditions.visibilityOf(addedcartValidate));
		String cartValidate = addedcartValidate.getText();
		return cartValidate;
	}

	public String ValidateDiscount() {

		return "-" + String.valueOf(Integer.parseInt(DiscounttValidate.getText().substring(3).replace(",", "")));

	}

	public String Validate_TotalAmount() {

		return String.valueOf(Integer.parseInt(TotalAmount.getText().substring(1).replace(",", "")));

	}

	public void Enter_Increase_quantity(String num) {
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='p2uyH2']/div/input[@type='text']")));

		executor.executeScript(
				"arguments[0].scrollIntoView({block: \"center\",inline: \"center\",behavior: \"smooth\"});",
				Increase_quantity);

		wait.until(ExpectedConditions.visibilityOf(Increase_quantity));

		Increase_quantity.clear();
		Increase_quantity.sendKeys(num);
	}

	public String PopupMessage() {
		wait.until(ExpectedConditions.visibilityOf(PopUp));
		String pop = PopUp.getText();

		return pop;
	}

	public void WaitTillPopup_Disappear() {
		wait.until(ExpectedConditions.invisibilityOf(PopUp));

	}

	public void Decrese_Quantites() {

		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		wait.until(ExpectedConditions.visibilityOf(Decrese_quantity));
		Decrese_quantity.click();
	}

	public void Click_RemoveButton() {

		executor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		wait.until(ExpectedConditions.visibilityOf(RemoveButton));
		RemoveButton.click();
	}

	public void PopUp_RemoveButton() {

		RemoveButton_popup.click();
	}

	public void Iteam_Incerese() {
		Enter_Increase_quantity(configuration.INCREASE_QUANTITY);

	}

}
