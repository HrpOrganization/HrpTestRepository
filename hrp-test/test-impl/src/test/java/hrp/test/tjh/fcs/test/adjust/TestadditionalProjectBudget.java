package hrp.test.tjh.fcs.test.adjust;

//课题预算追加申请
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopDropDownElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopTargetElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.implementation.register.logout.LogoutMethodServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestadditionalProjectBudget {
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
		fileNamePath = "FcsTestData/adjust/adjust";
		sheetName = "TestadditionalProjectBudget";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void additionalProjectBudget(HashMap<String, String> excelData) throws Exception {

		// 登录项目经费管理→课题预算追加申请
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "项目经费管理", "课题预算追加申请");
		// 输入课题编码
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String projectCode1 = excelData.get("课题编码");
		// String projectCode1 = TestProjectCreat.projectCode;
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "课题/项目信息", "课题编码", projectCode1);
		// 调增预算
		DesktopDropDownElementServiceImpl desktopDropDownElementService = new DesktopDropDownElementServiceImpl();
		String increaseBudget = excelData.get("调增预算");
		desktopDropDownElementService.listFieldCheckButtonSelect(driver, "调增预算", "实验材料费", increaseBudget);
		// 调整金额
		String TZTZJE = PublicTools.getRandomMoney(2);
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "录入调整金额", "调整金额", TZTZJE);
		// 确认
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "录入调整金额", "确认");
		// 确认
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickNullFieldButton(driver, "确认");
		windowButtonElementService.clickButton(driver, 1, "提示", "是");
		// 退出当前页面
		LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
		logoutMethodService.endPage(driver, "课题预算追加申请");

		// 登录项目经费管理→课题预算调整审批（部门）
		loginMethodService.loginPage(driver, "项目经费管理", "课题预算调整审批（部门）");
		// 输入课题/项目编码
		String projectCode2 = projectCode1;
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "查询条件", "课题/项目编码", projectCode2);
		// 查询
		windowButtonElementService.clickButton(driver, 1, "查询条件", "查询");
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "课题/项目编码", projectCode2);
		// 审批
		desktopButtonElementService.clickButton(driver, "审批");
		// 通过
		windowButtonElementService.clickButton(driver, 1, "审核窗口", "通过");
		// 退出当前页面
		logoutMethodService.endPage(driver, "课题预算调整审批（部门）");

		// 登录项目经费管理→课题预算调整（备案）
		loginMethodService.loginPage(driver, "项目经费管理", "课题预算调整（备案）");
		// 输入课题/项目编码
		String projectCode3 = projectCode1;
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "查询条件", "课题/项目编码", projectCode3);
		// 查询
		windowButtonElementService.clickButton(driver, 1, "查询条件", "查询");
		desktopFormListOperationService.formListClickTarget(driver, "课题/项目编码", projectCode3);
		// 备案
		desktopButtonElementService.clickButton(driver, "备案");
		// 通过
		windowButtonElementService.clickButton(driver, 1, "备案窗口", "通过");
	}
}
