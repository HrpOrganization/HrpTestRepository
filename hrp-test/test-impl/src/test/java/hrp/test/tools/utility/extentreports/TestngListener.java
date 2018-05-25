package hrp.test.tools.utility.extentreports;

import org.apache.log4j.Logger;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter {
	private Logger logger = Logger.getLogger(TestngListener.class);

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info("【" + tr.getName() + " Start1】");
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		logger.info("【" + tr.getName() + " Failure1】");
		ExtentReporterNGListener.takeScreenShot(tr);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		ExtentReporterNGListener.takeScreenShot(tr);
		logger.info("【" + tr.getName() + " Skipped1】");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info("【" + tr.getName() + " Success1】");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
	}

}
