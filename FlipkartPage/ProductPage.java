package FlipkartPage;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends SearchProductPage {

	WebDriver ldriver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor executor;

	public ProductPage(WebDriver rdriver) {
		super(rdriver);
		ldriver = rdriver;

		PageFactory.initElements(rdriver, this);
		wait = new WebDriverWait(ldriver, Duration.ofSeconds(30));

		executor = (JavascriptExecutor) ldriver;

		act = new Actions(ldriver);

	}

	@FindBy(xpath = "//button[text()='NOTIFY ME']")
	WebElement Notifyme_Button;

	@FindBy(xpath = "//div[text()='Sold Out']")
	WebElement SoldOut_text;

	@FindBy(xpath = "//div[text()='This item is currently out of stock']")
	WebElement OutOfStock_text;

	@FindBy(xpath = "//button[@class='QqFHMw vslbG+ In9uk2']")
	WebElement Add_Cart;
	@FindBy(xpath = "//span[@class='VU-ZEz']")
	WebElement Item_Name;

	@FindBy(xpath = "//div[@class='yRaY8j A6+E6v']")
	WebElement Total_Amount;

	@FindBy(xpath = "//div[@class='Nx9bqj CxhGGd']")
	WebElement Dis_Amt;

	public String PageTitle() {
		return ldriver.getTitle();

	}

	public boolean Is_OutofStock_TextDisplayed() {
		boolean inStock = false;
		try {
			inStock = OutOfStock_text.isDisplayed();
			return inStock;
		} catch (Exception e) {
			return inStock;
		}
	}

	public boolean Is_NotifymeButton_Displayed() {
		boolean inStock = false;
		try {
			inStock = Notifyme_Button.isDisplayed();
			return inStock;
		} catch (Exception e) {
			return inStock;
		}
	}

	public boolean Is_SoldOut_textDisplayed() {
		boolean inStock = false;
		try {
			inStock = SoldOut_text.isDisplayed();
			return inStock;
		} catch (Exception e) {
			return inStock;
		}
	}

	public boolean Is_item_inStock() {

		return Is_NotifymeButton_Displayed() && Is_OutofStock_TextDisplayed() && Is_SoldOut_textDisplayed();

	}

	public String getDiscountPrice() {
		return String.valueOf(Integer.parseInt(PriceAfterDiscount()) - Integer.parseInt(PriceBeforeDiscount()));
	}

	public String GetSelectedItem_Name() {
		return Item_Name.getText();

	}

	public String PriceBeforeDiscount() {
		wait.until(ExpectedConditions.visibilityOf(Total_Amount));
		return Total_Amount.getText().substring(1).replace(",", "");
	}

	public String PriceAfterDiscount() {
		wait.until(ExpectedConditions.visibilityOf(Dis_Amt));
		return Dis_Amt.getText().substring(1).replace(",", "");
	}

	public void Add_Tocart() {
		Add_Cart.click();
	}

}
