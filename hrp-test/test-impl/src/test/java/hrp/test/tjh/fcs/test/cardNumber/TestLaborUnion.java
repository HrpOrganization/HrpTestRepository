package hrp.test.tjh.fcs.test.cardNumber;

//工会经费卡信息维护
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopDropDownElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestLaborUnion {
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
		String keyField = PublicTools.bufferPlus("卡号", "经费号");
		fileNamePath = "FcsTestData/cardNumber/cardNumber";
		sheetName = "TestLaborUnion";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void laborUnion(HashMap<String, String> excelData) throws Exception {
		// 进入工会经费卡管理→工会经费卡信息维护
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "工会经费卡管理", "工会经费卡信息维护");
		// 输入卡号
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		String unionCardNumber = PublicTools.getRandomValue("", 6);
		desktopInputBoxElementService.fieldWriteEnter(driver, "卡号", unionCardNumber);
		// 输入经费号
		String unionMoney = unionCardNumber;
		desktopInputBoxElementService.fieldWriteEnter(driver, "经费号", unionMoney);
		// 填写经费卡主卡信息
		// 输入经费号类别
		String unionCategoryOfFunds = excelData.get("经费号类别");
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		desktopDropDownElementService.listFieldSelect(driver, "经费号类别", unionCategoryOfFunds);
		// 填写负责人
		String leader = excelData.get("负责人");
		desktopDropDownElementService.listFieldWriteSearch(driver, "负责人", leader, leader);
		// 输入工会部门
		String departmentTradeUnions = excelData.get("工会部门");
		desktopDropDownElementService.listFieldWriteSearch(driver, "工会部门", departmentTradeUnions,
				departmentTradeUnions);
		// 保存
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "保存");
	}
}
