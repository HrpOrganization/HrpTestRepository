package hrp.test.tjh.fas.test.assetsManagement.copyright;

//著作入库
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class WorkPutStorage {
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
		String keyField = PublicTools.bufferPlus("资产名称", "资产原值", "千字");
		fileNamePath = "FasTestData/assetsManagement/copyright/copyright";
		sheetName = "WorkPutStorage";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void workPutStorage(HashMap<String, String> excelData) throws Exception {
		// 进入著作权入库→著作入库
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "著作权入库", "著作入库");
		// 填写设备核实表
		// 增加著作
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "增加著作");
		// 新增著作
		// 资产名称
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String assetName = PublicTools.getRandomValue("Y", 6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增著作", "资产名称", assetName);
		// 国标码
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String Gbcode = excelData.get("国标码");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增著作", "国标码", Gbcode, Gbcode);
		// 千字
		String KWord = PublicTools.getRandomValue("1", 6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增著作", "千字", KWord);
		// 管理部门
		String administrativeDepartment = excelData.get("管理部门");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增著作", "管理部门", administrativeDepartment,
				administrativeDepartment);
		// 资产原值
		String initialAssetValue = PublicTools.getRandomMoney(6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增著作", "资产原值", initialAssetValue);
		// 取得方式
		String gainingMethod = excelData.get("取得方式");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增著作", "取得方式", gainingMethod);
		// 保存
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "新增著作", "保存");
		// 获取
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "著作名称", assetName);

	}
}
