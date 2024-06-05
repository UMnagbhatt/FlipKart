package FlipkartPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchProductPage {
	WebDriver ldriver;
	WebDriverWait wait;
	Actions act;
	JavascriptExecutor executor;

	public SearchProductPage(WebDriver rdriver) {

		ldriver = rdriver;

		PageFactory.initElements(rdriver, this);
		wait = new WebDriverWait(ldriver, Duration.ofSeconds(10));

		executor = (JavascriptExecutor) ldriver;

		act = new Actions(ldriver);

	}

	@FindBy(xpath = "//div[@class='_6i1qKy' and text()='SAMSUNG']")
	WebElement Samsung_Clik;

	@FindBy(xpath = "//div[@class='_6i1qKy' and text()='8 GB and Above']")
	WebElement Select_Ram;

	@FindBy(xpath = "//div[@class='PYKUdo' ]")
	WebElement Slider_Range_Right;

	@FindBy(xpath = "//div[@class='KzDlHZ']")
	List<WebElement> Product_name;

	@FindBy(xpath = "//div[@class='Nx9bqj _4b5DiR']")
	List<WebElement> Product_price;

	public void click_on_SamsungCheckBox() {
		wait.until(ExpectedConditions.visibilityOf(Samsung_Clik));

		try {
			Samsung_Clik.click();
		} catch (Exception e) {
			executor.executeScript("arguments[0].click();", Samsung_Clik);
		}
	}

	public void Click_Ram() {
		executor.executeScript(
				"arguments[0].scrollIntoView({block: \"center\",inline: \"center\",behavior: \"smooth\"});",
				Select_Ram);

		wait.until(ExpectedConditions.visibilityOf(Select_Ram));

		try {
			Select_Ram.click();
		} catch (Exception e) {
			executor.executeScript("arguments[0].click();", Select_Ram);
		}
	}

	public void Select_PriceRange() {

		wait.until(ExpectedConditions.visibilityOf(Slider_Range_Right));

		act.moveToElement(Slider_Range_Right).pause(Duration.ofSeconds(1)).clickAndHold(Slider_Range_Right)
				.pause(Duration.ofSeconds(1)).moveByOffset(130, 0).pause(Duration.ofSeconds(1)).release().perform();

	}

	public HashMap<String, Integer> GetPrductDetails() {
		

		HashMap<String, Integer> map = new HashMap<>();

		for (int i = 0; i < Product_name.size(); i++) {
			map.put(Product_name.get(i).getText(),
					Integer.parseInt(Product_price.get(i).getText().substring(1).replace(",", "")));
		}

		return map;

	}

	public String GetFirstItem() {
		return Product_name.get(0).getText();

	}

	public void Clik_on_Product() {
		Product_name.get(0).click();

	}

	public void ApplyFilter() {
		click_on_SamsungCheckBox();
		Click_Ram();
		Select_PriceRange();
	}

	public void ClickonItem() {
		GetFirstItem();
		Clik_on_Product();
		NavigateToNextStep();
	}

	public void NavigateToNextStep() {

		Set<String> window = ldriver.getWindowHandles();
		for (String win : window) {

			ldriver.switchTo().window(win);
		}
	}

	public String[] GetCheapestPhone() throws InterruptedException {
		String[] phonedetais = new String[2];

		HashMap<String, Integer> map = GetPrductDetails();

		List<Integer> PriceValue = new ArrayList<>(map.values());

		Collections.sort(PriceValue);

		for (Entry<String, Integer> entry : map.entrySet()) {

			if (entry.getValue() == PriceValue.get(0)) {

				phonedetais[0] = entry.getKey();
				phonedetais[1] = PriceValue.get(0).toString();

			}

		}

		return phonedetais;
	}

	public String[] GetCostliestPhone() throws InterruptedException {
		String[] phonedetais = new String[2];

		HashMap<String, Integer> map = GetPrductDetails();

		List<Integer> PriceValue = new ArrayList<>(map.values());

		Collections.sort(PriceValue);

		for (Entry<String, Integer> entry : map.entrySet()) {

			if (entry.getValue() == PriceValue.get(PriceValue.size() - 1)) {

				phonedetais[0] = entry.getKey();
				phonedetais[1] = PriceValue.get(PriceValue.size() - 1).toString();

			}

		}

		return phonedetais;
	}

}
