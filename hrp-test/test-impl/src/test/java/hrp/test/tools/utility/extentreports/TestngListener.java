package hrp.test.tools.utility.extentreports;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter {
	private Logger logger = Logger.getLogger(TestngListener.class);
	// protected ExtentReports extent;
	// protected ExtentTest test;

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info("【" + tr.getName() + " Start1】");
		// extent=InitDriverCase.getextent();
		// test= extent.startTest(tr.getName());
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		logger.info("【" + tr.getName() + " Failure1】");
		ExtentReporterNGListener.takeScreenShot(tr);
		// test.log(LogStatus.FAIL, tr.getThrowable());
		// extent.endTest(test);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		ExtentReporterNGListener.takeScreenShot(tr);
		logger.info("【" + tr.getName() + " Skipped1】");
		// test.log(LogStatus.SKIP, "SKIP");
		// extent.endTest(test);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info("【" + tr.getName() + " Success1】");
		// test.log(LogStatus.PASS, "Pass");
		// extent.endTest(test);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
	}

}
