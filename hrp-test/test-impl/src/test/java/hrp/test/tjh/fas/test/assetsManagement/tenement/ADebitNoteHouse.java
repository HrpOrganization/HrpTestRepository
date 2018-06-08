package hrp.test.tjh.fas.test.assetsManagement.tenement;

//应付款通知单（房屋）
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
import hrp.test.tools.api.implementation.element.window.WindowFormElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class ADebitNoteHouse {
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
		fileNamePath = "FasTestData/assetsManagement/tenement/tenement";
		sheetName = "ADebitNoteHouse";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void aDebitNoteHouse(HashMap<String, String> excelData) throws Exception {
		// 进入房屋入库 → 应付款通知单(房屋)
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "房屋入库", "应付款通知单（房屋）");
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
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "资产编号", "FW19910001");
		// 生成付款单
		desktopButtunElementService.clickButton(driver, "生成付款单");
		// 付款信息详情
		// 应付单位
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String toCopeWithUnit = excelData.get("应付单位");
		windowInputBoxElementService.fieldWrite(driver, 1, "付款信息详情", "应付单位", toCopeWithUnit);
		// 供应商
		String supplier = excelData.get("供应商");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "付款信息详情", "供应商", supplier, supplier);
		// 开户银行
		String depositBank = excelData.get("开户银行");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "付款信息详情", "开户银行", depositBank, depositBank);
		// 银行账户
		String bankAccount = excelData.get("银行账户");
		windowInputBoxElementService.fieldWrite(driver, 1, "付款信息详情", "银行账户", bankAccount);
		// 保存
		windowButtonElementService.clickButton(driver, 1, "付款信息详情", "保存");
	}
}
