package hrp.test.tjh.fcs.test.account;

//一般费用录入(研究生)
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class GeneralFeeEntryGraduate {
	protected WebDriver driver;
	protected String fileNamePath;
	protected String sheetName;

	@BeforeMethod
	public void setUp() throws Exception {
		// 设置谷歌浏览器启动配置
		StartChromeSettingsServiceImpl startChromeSettings = new StartChromeSettingsServiceImpl();
		driver = startChromeSettings.startChrome(driver);
		// 调用监听器
		ExtentReporterNGListener.driver = driver;
		// 登录经费卡管理系统
		LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
		loginModuleService.loginModule(driver, "http://192.168.222.227:8080/fcs-portal/", "USER001", "123456", "财务处");
	}

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws IOException, BiffException {
		String keyField = PublicTools.bufferPlus("金额");
		fileNamePath = "FcsTestData/account/account";
		sheetName = "GeneralFeeEntryGraduate";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void generalFeeEntryGraduate(HashMap<String, String> excelData) throws Exception {
		// //进入收入支出管理→总务科费用录入
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "收入支出管理", "一般费用录入");
		// 刷经费号
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		String accountNo = excelData.get("经费号");
		desktopInputBoxElementService.fieldWriteEnter(driver, "经费号", accountNo);
		// 子卡信息(改)
		String daughterCardInformation = excelData.get("子卡信息");
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		windowDropdownElementService.listFieldSelect(driver, 1, "经费卡信息", "子卡信息", daughterCardInformation);

	}
}
