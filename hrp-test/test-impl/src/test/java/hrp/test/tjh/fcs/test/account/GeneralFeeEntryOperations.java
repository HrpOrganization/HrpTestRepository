package hrp.test.tjh.fcs.test.account;

//一般费用录入(运营预算)

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopDropDownElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopTargetElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.contrast.ServerBillContrast;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GeneralFeeEntryOperations {
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
		fileNamePath = "FcsTestData/account/account";
		sheetName = "GeneralFeeEntryOperations";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	/**
	 * 测试一般费用录入流程
	 *
	 * @throws Exception
	 *             使用Thread
	 */
	@Test(dataProvider = "excelData")
	public void generalFeeEntryOperations(HashMap<String, String> excelData) throws Exception {
		// //进入收入支出管理→一般费用录入
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "收入支出管理", "一般费用录入");
		// 刷经费号
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		String accountNo = excelData.get("经费号");
		desktopInputBoxElementService.fieldWriteEnter(driver, "经费号", accountNo);
		// 选择预算列表
		String budgetCode = excelData.get("预算列表");
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		desktopDropDownElementService.listFieldWriteSearch(driver, "预算列表", budgetCode, budgetCode);
		// 选择资金来源
		String budgetSource = excelData.get("资金来源");
		desktopDropDownElementService.listFieldSelect(driver, "资金来源", budgetSource);
		// 选择项目类别
		String itemClassName = excelData.get("项目类别");
		desktopDropDownElementService.listFieldSelect(driver, "项目类别", itemClassName);
		// 选择项目子类别
		String itemSubClassName = excelData.get("项目子类别");
		desktopDropDownElementService.listFieldSelect(driver, "项目子类别", itemSubClassName);
		// 选择项目名称
		String itemName = excelData.get("项目名称");
		desktopDropDownElementService.listFieldSelect(driver, "项目名称", itemName);
		// 填写数量
		String itemAmount = excelData.get("数量");
		desktopInputBoxElementService.fieldWrite(driver, "数量", itemAmount);
		// 填写金额
		String itemMoney = PublicTools.getRandomMoney(4);
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("金额"), itemMoney);
		desktopInputBoxElementService.fieldWrite(driver, "金额", itemMoney);
		// 点击添加
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "添加");

		/*
		 * //验证添加金额是否正确 String weProjectAmount =
		 * driver.findElement(By.cssSelector("#x-auto-680 > div")).getText();
		 * Assert.assertEquals(weProjectAmount,itemAmount); //验证添加数量是否正确 String
		 * weProjectMoney = driver.findElement(By.cssSelector(
		 * "#x-auto-681 > div")).getText();
		 * Assert.assertEquals(weProjectMoney,itemMoney);
		 */

		// 判断是否保存成功
		try {
			// 点击保存
			desktopButtunElementService.clickButton(driver, "保存");
			Thread.sleep(500);
			// 不打印
			desktopButtunElementService.clickButton(driver, "否");
		} catch (Exception e) {
			// 如果报错传出截图
			PublicTools.screenshot(driver, e, "[收入支出管理-一般费用录入]");
		}

		// 对比服务计价信息
		// 统计查询→服务计价查询
		loginMethodService.loginPage(driver, "统计查询", "服务计价查询");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		String documentNo = desktopFormListOperationService.formListContrastTarget(driver, "项目名称", itemName, "单号");
		String getBillValue = desktopFormListOperationService.formListContrastTarget(driver, "单号", documentNo, "金额");
		System.out.println(getBillValue);
		Assert.assertEquals(getBillValue, itemMoney);
		System.out.println("contrastive success");
		// 对比预算列表是否正确
		// 获取所下预算的金额
		String cost = ServerBillContrast.bcsSeverBillContrast(driver, budgetCode, "业务单据号", documentNo, "变动金额(元)");
		Assert.assertEquals(itemMoney, cost);
		System.out.println("contrastive success");
		System.out.println("contrastive success");

	}

	// @AfterMethod
	// public void tearDown() {
	// LogoutMethodServiceImpl logoutMethodService = new
	// LogoutMethodServiceImpl();
	// logoutMethodService.userLogout(driver);
	// driver.quit();
	// }
}
