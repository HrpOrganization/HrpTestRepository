package hrp.test.tjh.fcs.test.waterElectricity;

//科室表数录入
import java.io.IOException;
import java.util.HashMap;

import hrp.test.tools.api.implementation.element.desktop.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowFormElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestDepartments {
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
		String keyField = PublicTools.bufferPlus("课题/项目名称", "批准总预算", "调整金额");
		fileNamePath = "FcsTestData/waterElectricity/waterElectricity";
		sheetName = "TestDepartments";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	/**
	 * 测试科室表数录入流程
	 *
	 * @throws Exception
	 *             使用Threadt
	 */
	@Test(dataProvider = "excelData")
	public void departments(HashMap<String, String> excelData) throws Exception {
		// //进入水电管理→科室表数录入
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "水电管理", "科室表数录入");
		// 工资号/经费号
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		String salaryNumber = excelData.get("工资号/经费号");
		desktopInputBoxElementService.fieldWrite(driver, "工资号/经费号", salaryNumber);
		// 科室
		String departments = excelData.get("科室");
		desktopInputBoxElementService.fieldWrite(driver, "科室", departments);
		// 仪表类型
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		String instrumentType = excelData.get("仪表类型");
		desktopDropDownElementService.listFieldSelect(driver, "仪表类型", instrumentType);
		// 查询
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "查询");
		// 获取
		DesktopFormElementServiceImpl desktopFormElementService = new DesktopFormElementServiceImpl();
		desktopFormElementService.guideFieldWrite(driver, "本月止码", "4000", "序号,16", "仪表类型,冷水表", "经费号,11604201");
		// 保存
		desktopButtonElementService.clickButton(driver, "保存");
	}
}
