package hrp.test.tjh.fas.test.bePutInStorage.land;

//应付款通知单（土地）
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class ADebitNoteLand {
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
		// 登录资产管理系统
		LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
		loginModuleService.loginModule(driver, "http://192.168.222.227:8080/fas-new-portal/", "USER001", "123456",
				"计算机中心");
	}

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws IOException, BiffException {
		// 添加需要保存的值（用于获取该值坐标）
		String keyField = PublicTools.bufferPlus("");
		fileNamePath = "FasTestData/bePutInStorage/land/land";
		sheetName = "ADebitNoteLand";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void aDebitNoteLand(HashMap<String, String> excelData) throws Exception {
		// 进入土地入库 → 应付款通知单(土地)
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "土地入库", "应付款通知单（土地）");
		// 新增应付款通知单
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "新增");
		// 取得方式
		String gainingMethod = excelData.get("取得方式");
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		windowDropdownElementService.listFieldSelect(driver, 1, "新增付款单", "取得方式", gainingMethod);
		// 查询
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "新增付款单", "查询");
		// 获取
	}
}
