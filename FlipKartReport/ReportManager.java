package FlipKartReport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportManager {
	
	
	private static ExtentSparkReporter report;

	private static ExtentReports extent;

	public static ExtentReports getReport() {
		if (extent == null) {
			String TimeStamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
			String reportName = "FlipKart - " + TimeStamp + ".html";

			report = new ExtentSparkReporter(reportName);
			report.config().setTheme(Theme.STANDARD);
			report.config().setDocumentTitle("FlipKart Report");
			report.config().setReportName("Extent Report for FlipKart");

			extent = new ExtentReports();
			extent.attachReporter(report);
		}
		return extent;
	}

	public static ExtentTest generateTest(String Name) {
		return extent.createTest(Name);

	}

	public static void flush() {
		if (extent != null) {
			extent.flush();
		}
	}

	public static String takeScreenshot(WebDriver driver, String screenshotsName) {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/screenshots/FlipKart_" + screenshotsName + ".png";
		try {
			Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots/"));
			Files.copy(source.toPath(), Paths.get(destination));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination;
	}

}
