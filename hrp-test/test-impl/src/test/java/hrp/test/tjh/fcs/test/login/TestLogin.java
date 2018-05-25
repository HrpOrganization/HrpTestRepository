package hrp.test.tjh.fcs.test.login;

import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class TestLogin {
	protected WebDriver driver;
	protected String fileNamePath;
	protected String sheetName;

	@BeforeMethod
	public void setUp() {
		// 处理浏览器驱动
		StartChromeSettingsServiceImpl startChromeSettingsService = new StartChromeSettingsServiceImpl();
		driver = startChromeSettingsService.startChrome(driver);
		// 调用监听器
		ExtentReporterNGListener.driver = driver;
	}

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws BiffException, IOException {
		String keyField = PublicTools.bufferPlus("付款金额", "银行账号");
		fileNamePath = "FcsTestData/TestLogin/TestLogin";
		sheetName = "TestLogin";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	/**
	 * 测试登录是否正常
	 *
	 * @throws Exception
	 *             使用Thread
	 */
	@Test(dataProvider = "excelData")
	public void login(HashMap<String, String> excelData) throws Exception {
		// 登录系统
		String baseUrl = "http://192.168.222.227:8080/fcs-portal/";
		driver.get(baseUrl);
		Thread.sleep(2000);
		String username = excelData.get("用户名");
		String password = excelData.get("密码");
		// 调用登录模块
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.login(driver, username, password);
		// 选择登录科室
		String loginName = "计算机中心";
		loginMethodService.loginConsole(driver, loginName);
		// 判断是否登录成功
		String text = driver.findElement(By.id("x-auto-90")).getText();
		String assertname = username + "(" + username + ")";
		Assert.assertEquals(text, assertname);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
