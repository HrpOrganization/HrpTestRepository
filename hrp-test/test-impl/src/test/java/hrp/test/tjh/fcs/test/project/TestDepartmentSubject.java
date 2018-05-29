package hrp.test.tjh.fcs.test.project;

import java.io.IOException;
import java.util.HashMap;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowFormElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.implementation.register.logout.LogoutMethodServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestDepartmentSubject {
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
		loginModuleService.loginModule
				(driver, "http://192.168.222.227:8080/fcs-portal/", "USER001", "123456", "财务处");
	}

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws IOException, BiffException {
		String keyField = PublicTools.bufferPlus("课题/项目编码", "课题/项目名称", "批准总预算", "预算金额1");
		fileNamePath = "FcsTestData/project/project";
		sheetName = "TestDepartmentSubject";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void departmentSubject(HashMap<String, String> excelData) throws Exception {

		// 登录项目经费管理→部门课题信息管理
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "项目经费管理", "部门课题信息管理");
		// 新增课题
		// 点击添加
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "添加");

		// 输入课题名称
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String projectName = PublicTools.getRandomValue("测试", 5);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增课题/项目信息", "课题/项目名称", projectName);

		// 输入课题编码
		String projectCode = PublicTools.getRandomValue("T", 8);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增课题/项目信息", "课题/项目编码", projectCode);

		// 选择项目来源
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String sourceCode = excelData.get("项目来源");
		windowDropdownElementService.listFieldWriteEnter(driver, 1, "新增课题/项目信息", "项目来源", sourceCode);
		// 选择来源级别
		String levelCode = excelData.get("来源级别");
		windowDropdownElementService.listFieldWriteEnter(driver, 1, "新增课题/项目信息", "来源级别", levelCode);
		// 选择项目管理属性（参与预算）
		String projectAttr = excelData.get("项目管理属性");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增课题/项目信息", "项目管理属性", projectAttr);
		// 选择归口部门
		String deptCode = excelData.get("归口部门");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增课题/项目信息", "归口部门", deptCode);
		// 选择核算单元
		String accountUnit = excelData.get("核算单元");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增课题/项目信息", "核算单元", accountUnit, accountUnit);
		// 批准总预算
		String generalBudget = PublicTools.getRandomMoney(6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增课题/项目信息", "批准总预算", generalBudget);
		// 填写负责人
		String chargePerson = excelData.get("负责人");
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "新增课题/项目信息", "负责人", chargePerson);
		// 选择申报科室
		String declarationDepartment = excelData.get("申报科室");
		windowDropdownElementService.listFieldWriteEnter(driver, 1, "新增课题/项目信息", "申报科室", declarationDepartment);
		// 选择课题类型
		String projectType = excelData.get("课题类型");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增课题/项目信息", "课题类型", projectType);

		// 分配预算
		// 分配第一个预算
		String projectSubBudgetNameOne = excelData.get("预算名称1");
		String projectSubBudgetCostOne = generalBudget;
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("预算金额1"), projectSubBudgetCostOne);
		WindowFormElementServiceImpl windowFormElementService = new WindowFormElementServiceImpl();
		windowFormElementService.listFieldCheckButtonWrite(driver, 2, "预算分配", "", projectSubBudgetNameOne,
				projectSubBudgetCostOne);
		try {
			// 提交新建课题
			desktopButtunElementService.clickButton(driver, "提交");
			Thread.sleep(500);
			LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
			logoutMethodService.endPage(driver, "部门课题信息管理");

			// 审核课题
			// 部门审核
			// 进入部门审核页面---课题审批（部门）
			LoginMethodServiceImpl loginMethodService1 = new LoginMethodServiceImpl();
			loginMethodService1.loginPage(driver, "项目经费管理", "课题审批（部门）");
		} catch (Exception e) {
			PublicTools.screenshot(driver, e, "[项目经费管理-课题审批（部门）]");
		}
		// 查询到需要选择的课题
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		desktopInputBoxElementService.fieldWrite(driver, "课题/项目编码", projectCode);
		desktopButtunElementService.clickButton(driver, "查询"); // 判断提交的课题批准总预算是否正确
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		String contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "课题/项目编码", projectCode,
				"批准总预算");
		Assert.assertEquals(generalBudget, contrastTarget);
		// 选择被审核课题
		desktopFormListOperationService.formListClickTarget(driver, "课题/项目编码", projectCode);
		// 点击审批
		desktopButtunElementService.clickButton(driver, "审批");
		// 审核通过
		desktopButtunElementService.clickButton(driver, "通过");
		// 退出部门审核页面
		LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
		logoutMethodService.endPage(driver, "课题审批（部门）");

		// 进入财务审核页面---课题审批（财务）
		loginMethodService.loginPage(driver, "项目经费管理", "课题审批（财务）");
		// 查询到需要选择的课题
		desktopInputBoxElementService.fieldWrite(driver, "课题/项目编码", projectCode);
		desktopButtunElementService.clickButton(driver, "查询");
		// 判断提交的课题批准总预算是否正确
		contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "课题/项目编码", projectCode,
				"批准总预算");
		Assert.assertEquals(generalBudget, contrastTarget);
		// 选择被审核课题
		desktopFormListOperationService.formListClickTarget(driver, "课题/项目编码", projectCode);
		// 点击审批
		desktopButtunElementService.clickButton(driver, "审批");
		// 经费号
		String accountNO = excelData.get("经费号");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "审核窗口", "经费号", accountNO,accountNO);
		// 支付类别
		String paymentCategory = excelData.get("支付类别");
		windowDropdownElementService.listFieldSelect(driver, 1, "审核窗口", "支付类别", paymentCategory);
		// 审核通过
		desktopButtunElementService.clickButton(driver, "通过");
		// 退出财务审核页面
		logoutMethodService.endPage(driver, "课题审批（财务）");
		// 进入部门课题信息管理页面
		loginMethodService.loginPage(driver, "项目经费管理", "部门课题信息管理");
		// 查询到需要选择的课题
		desktopInputBoxElementService.fieldWrite(driver, "课题/项目编码", projectCode);
		desktopButtunElementService.clickButton(driver, "查询");
		// 判断被审核的课题状态是否正确
		contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "课题/项目编码", projectCode,
				"课题/项目状态");
		Assert.assertEquals("财务审核通过", contrastTarget);
	}
}
