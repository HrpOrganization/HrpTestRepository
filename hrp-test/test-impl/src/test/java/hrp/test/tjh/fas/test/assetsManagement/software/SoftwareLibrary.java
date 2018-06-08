package hrp.test.tjh.fas.test.assetsManagement.software;

//软件入库
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopDropDownElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class SoftwareLibrary {
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
		String keyField = PublicTools.bufferPlus("");
		fileNamePath = "FasTestData/assetsManagement/software/software";
		sheetName = "SoftwareLibrary";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void softwareLibrary(HashMap<String, String> excelData) throws Exception {
		// 进入软件入库→软件入库
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "软件入库", "软件入库");
		// 资产状态
		String assetStates = excelData.get("资产状态");
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		desktopDropDownElementService.listFieldSelect(driver, "资产状态", assetStates);
		// 查询
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "查询");
		// 没数据

		// 选择目标值
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "资产 编号", "203");
		// 入库
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "入库");
		// 关联预算
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "预算详情", "关联预算");
		// 选择对应核算单元
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String unitName = excelData.get("核算单元");
		String unitCode = "16019500";
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "记账经费卡", "核算单元", unitName, unitCode);
		// 选择经费号
		String accountNo = excelData.get("经费号");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "记账经费卡", "经费号", accountNo, accountNo);
		// 选择预算单元
		String budgetUnitCode = excelData.get("预算单元");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "执行预算", "预算单元", budgetUnitCode, budgetUnitCode);
		// 选择预算编码
		String budgetCode11 = excelData.get("预算编码");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "执行预算", "预算编码", budgetCode11, budgetCode11);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// 保存执行预算
		desktopButtunElementService.clickButton(driver, "保存");
	}
}
