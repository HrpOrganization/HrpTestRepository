package hrp.test.tools.utility.extentreports;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hrp.test.tools.utility.use.PublicTools;
import com.relevantcodes.extentreports.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class ExtentReporterNGListener implements IReporter {

	private static String cyrPatn = System.getProperty("user.dir") + "/../.."; // 获取本地工程目录
	public static WebDriver driver;
	private ExtentReports extent;
	private static String time = PublicTools.getSystemTime();

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// true为覆盖已经生成的报告
		extent = new ExtentReports(cyrPatn + "/test-result/reports/testReports" + time + ".html", true // true为覆盖已经生成的报告，false
																										// 在已有的报告上面生成，不会覆盖旧的结果
				, NetworkMode.OFFLINE // 最新运行的用例结果在第一个
		);
		extent.startReporter(ReporterType.DB, cyrPatn + "/test-result/reports/testReports" + time + ".html"); // 生成本地的DB数据文件
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}
		extent.flush();
		extent.close();

	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());
				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);
				if (result.getThrowable() != null) {
					test.log(status, test.addScreenCapture(cyrPatn + "\\test-result\\screenshot\\"
							+ result.getMethod().getMethodName() + time + ".png"));
					test.log(status, result.getThrowable());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}
				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static void takeScreenShot(ITestResult tr) {
		// 截图
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile,
					new File(cyrPatn + "\\test-result\\screenshot\\" + tr.getName() + time + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("GetScreenshot Fail");
		} finally {
			System.out.println("GetScreenshot Successful" + cyrPatn + "\\test-result\\screenshot\\" + tr.getName()
					+ time + ".png");

		}
	}

}
