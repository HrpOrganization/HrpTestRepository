package hrp.test.tjh.fas.test.assetsManagement.equipment;

//应付款通知单（设备）
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

public class ADebitNoteEquipment {
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
		fileNamePath = "FasTestData/assetsManagement/equipment/equipment";
		sheetName = "ADebitNoteEquipment";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void aDebitNoteEquipment(HashMap<String, String> excelData) throws Exception {
		// 进入设备入库 → 应付款通知单(设备)
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "设备入库", "应付款通知单（设备）");
		// 新增应付款通知单
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "新增");
		// 输入入库单号
		String documentNo = excelData.get("入库单号");
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		windowInputBoxElementService.fieldWrite(driver, 1, "新增付款单", "入库单号", documentNo);
		// 查询
		WindowButtonElementServiceImpl windowButtunElementService = new WindowButtonElementServiceImpl();
		windowButtunElementService.clickButton(driver, 1, "新增付款单", "查询");
		// 选择需要新增的单据
		//String purchaseNo = excelData.get("采购计划号");
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "入库单号", documentNo);
		// 填写备注
		windowInputBoxElementService.nullFieldWrite(driver, 1, "新增付款单", "width: 1261px; height: 56px", "填写的备注");
		// 生成付款单
		windowButtunElementService.clickButton(driver, 1, "新增付款单", "生成付款单");
		// 收款单位信息确认
		// 收款单位
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String supplier = excelData.get("收款单位");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "收款单位信息确认", "收款单位", supplier, supplier);
		// 银行账号
		String bankAccount = excelData.get("银行账号");
		windowInputBoxElementService.fieldWrite(driver, 1, "收款单位信息确认", "银行账号", bankAccount);
		// 开户银行
		String depositBank = excelData.get("开户银行");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "收款单位信息确认", "开户银行", depositBank, depositBank);
		// 确认
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "收款单位信息确认", "确认");
		// 验证单据是否生成
		// String contrastTarget =
		// desktopFormListOperationService.formListContrastTarget(driver, "收款单位",
		// supplier, "供应商");
		// Assert.assertEquals(supplier, contrastTarget);

		// @AfterMethod
		// public void tearDown() {
		// LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
		// logoutMethodService.userLogout(driver);
		// driver.quit();
	}

}
