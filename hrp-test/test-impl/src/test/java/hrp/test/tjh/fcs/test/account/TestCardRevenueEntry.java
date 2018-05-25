package hrp.test.tjh.fcs.test.account;

//卡收入录入

import java.io.IOException;
import java.util.HashMap;

import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopDropDownElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.contrast.ServerBillContrast;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestCardRevenueEntry {
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
		fileNamePath = "FcsTestData//account//account";
		sheetName = "TestCardRevenueEntry";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	/**
	 * 测试卡收入录入流程
	 *
	 * @throws Exception
	 *             使用Thread
	 */
	@Test(dataProvider = "excelData")
	public void cardRevenueEntry(HashMap<String, String> excelData) throws Exception {
		// //进入收入支出管理→卡收入录入
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "收入支出管理", "卡收入录入");
		// 刷经费号
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		String accountNo = excelData.get("经费号");
		desktopInputBoxElementService.fieldWriteEnter(driver, "经费号", accountNo);

		// 选择课题/项目信息
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		String itemInformation = excelData.get("课题/项目信息");
		desktopDropDownElementService.listFieldWriteSearch(driver, "课题/项目信息", itemInformation, itemInformation);

		// 填写金额
		String itemMoney = PublicTools.getRandomMoney(3);
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("金额"), itemMoney);
		desktopInputBoxElementService.fieldWrite(driver, "金额", itemMoney);
		// 摘要
		String digest = excelData.get("摘要");
		desktopDropDownElementService.listFieldSelect(driver, "摘要", digest);
		// 是否选择预算
		String toSelect = excelData.get("是否选择预算");
		desktopDropDownElementService.listFieldSelect(driver, "是否选择预算", toSelect);
		// 预算单元
		String budgetElement = excelData.get("预算单元");
		desktopDropDownElementService.listFieldWriteSearch(driver, "预算单元", budgetElement, budgetElement);
		// 选择预算列表
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String budgetCode = excelData.get("预算列表");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "卡收入信息", "预算列表", budgetCode, budgetCode);
		// 选择资金来源
		String budgetSource = excelData.get("资金来源");
		windowDropdownElementService.listFieldSelect(driver, 1, "卡收入信息", "资金来源", budgetSource);
		// 添加
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "添加");
		// 是/否
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "提示", "是");
		// 保存
		windowButtonElementService.clickButton(driver, 1, "卡收入信息", "保存");
		// 进入统计查询→经费卡信息查询
		loginMethodService.loginPage(driver, "统计查询", "经费卡信息查询");
		// 查询条件
		// 经费号
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		windowInputBoxElementService.fieldWrite(driver, 1, "查询条件", "经费号", accountNo);
		// 查询
		windowButtonElementService.clickButton(driver, 1, "查询条件", "查询");
		// 对比服务计价信息(开发再改)
		// 统计查询→服务计价查询

		// 对比预算列表是否正确
		// 获取所下预算的金额

	}

}
