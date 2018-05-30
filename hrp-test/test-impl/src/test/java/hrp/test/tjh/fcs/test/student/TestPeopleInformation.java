package hrp.test.tjh.fcs.test.student;

//学生管理
import java.io.IOException;
import java.util.HashMap;

import hrp.test.tools.api.implementation.element.window.WindowButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowFormElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class TestPeopleInformation {
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
		String keyField = PublicTools.bufferPlus("学生学号", "学生姓名");
		fileNamePath = "FcsTestData/student/student";
		sheetName = "TestPeopleInformation";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void peopleInformation(HashMap<String, String> excelData) throws Exception {
		// 进入人员信息→学生管理
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "人员信息", "学生管理");
		// 点击新增按钮 新增学生信息
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "新增");
		// 填写学生信息
		// 输入学生学号
		String studentNumber = PublicTools.getRandomValue("M2017", 5);
		WindowInputBoxElementServiceImpl windowInputBoxElementServiceImpl = new WindowInputBoxElementServiceImpl();
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "学生学号", studentNumber);
		// 输入学生姓名
		String studentName = PublicTools.getRandomValue("张", 4);
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "学生姓名", studentName);
		// 输入导师工号
		String mentorWorkNumber = excelData.get("导师工号");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "导师工号", mentorWorkNumber);
		// 输入导师姓名
		String speciality = excelData.get("导师姓名");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "导师姓名", speciality);
		// 输入学位类型
		String degreeType = excelData.get("学位类型");
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		windowDropdownElementService.listFieldSelect(driver, 1, "新增学生信息", "学位类型", degreeType);
		// 输入学生专业
		String students = excelData.get("学生专业");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "学生专业", students);
		// 输入证件类型
		String certificateType = excelData.get("证件类型");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增学生信息", "证件类型", certificateType);
		// 输入证件号
		String iDNumber = excelData.get("证件号");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "证件号", iDNumber);
		// 输入开户银行
		String depositBank = excelData.get("开户银行");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "开户银行", depositBank);
		// 输入开户卡号
		String toOpenAnAccountNumber = excelData.get("开户卡号");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "开户卡号", toOpenAnAccountNumber);
		// 输入开户名
		String accountName = excelData.get("开户名");
		windowInputBoxElementServiceImpl.fieldWrite(driver, 1, "新增学生信息", "开户名", accountName);
		// 输入其他
		String rests = excelData.get("其他");
		windowDropdownElementService.listFieldCheckBox(driver, 1, "新增学生信息", "其他", rests);
		// 保存
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "新增学生信息", "保存");

		// 确认是否保存成功
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		String saveStudentInformation = desktopFormListOperationService.formListContrastTarget(driver, "学生学号",
				studentNumber, "学生姓名");
		Assert.assertEquals(studentName, saveStudentInformation);

		// 选择保存的数据
		desktopFormListOperationService.formListClickTarget(driver, "学生学号", studentNumber);
		// 对比数据内的设备明细是否正确
		// 获取设备名对应的单价
		saveStudentInformation = desktopFormListOperationService.formListContrastTarget(driver, "学生学号", studentNumber,
				"学生姓名");
		Assert.assertEquals(studentName, saveStudentInformation);

		// 关联学生与导师信息
		desktopButtunElementService.clickButton(driver, "添加关联");
		// 经费号负责人
		String fundManager = excelData.get("经费号负责人");
		windowDropdownElementService.listFieldWriteEnter(driver, 1, "学生关联导师窗口", "经费号负责人", fundManager);
		// 课题/项目名称
		String projectName = excelData.get("课题/项目名称");
		//windowDropdownElementService.listFieldSelect(driver, 1, "学生关联导师窗口", "课题/项目名称", projectName);
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "学生关联导师窗口", "课题/项目名称", projectName, projectName);
		//确认
		windowButtonElementService.clickButton(driver, 1, "学生关联导师窗口", "确认");
		
	}
}
