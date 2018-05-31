package hrp.test.tjh.fcs.test.student;

//助研金发放
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopTargetElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestResearchGrants {
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
		fileNamePath = "FcsTestData/student/student";
		sheetName = "TestResearchGrants";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void researchGrants(HashMap<String, String> excelData) throws Exception {
		// 助研金发放页面
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "人员信息", "助研金发放");
		// 输入学生学号
		// String studentNumber1 = studentNumber;
		String studentNumber1 = excelData.get("学号");
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		windowInputBoxElementService.fieldWrite(driver, 1, "查询条件", "学生学号", studentNumber1);
		// 助研金生成
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "助研金生成");
		// 提示--是
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "提示", "是");
		
		// 获取
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "学号", studentNumber1);
		
		// 助研金发放
		//desktopButtonElementService.clickButton(driver, "助研金发放");
		// 提示--是
		// windowButtonElementService.clickButton(driver, 1, "提示", "是"); 
	}
}
