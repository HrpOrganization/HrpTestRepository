package hrp.test.tjh.fas.test.bePutInStorage.land;
//土地入库
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class LandPutInStorage {
	protected WebDriver driver;
	protected String fileNamePath;
	protected String sheetName;

	@BeforeMethod
	public void setUP() throws Exception {
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
		String keyField = PublicTools.bufferPlus("发票号", "采购计划号", "设备单价", "入库单号");
		fileNamePath = "FasTestData/bePutInStorage/land/land";
		sheetName = "LandPutInStorage";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void landPutInStorage(HashMap<String, String> excelData) throws Exception {
		// 进入土地入库→土地入库
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "土地入库", "土地入库");
}
}
