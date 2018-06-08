package hrp.test.tjh.fas.test.assetsManagement.patent;

//新增专利
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

public class NewPatent {
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
		String keyField = PublicTools.bufferPlus("专利名称", "专利价值");
		fileNamePath = "FasTestData/assetsManagement/patent/patent";
		sheetName = "NewPatent";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void newPatent(HashMap<String, String> excelData) throws Exception {
		// 进入专利入库→新增专利
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "专利入库", "新增专利");
		// 填写设备核实表
		// 增加专利
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "增加专利");
		// 专利名称
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String patentName = PublicTools.getRandomValue("Y", 6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "专利名称", patentName);
		// 专利类型
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String patentType = excelData.get("专利类型");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增专利", "专利类型", patentType);
		// 国标码
		String Gbcode = excelData.get("国标码");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增专利", "国标码", Gbcode, Gbcode);
		// 发明人
		String inventor = excelData.get("发明人");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "发明人", inventor);
		// 申请日期
		String applicationDate = excelData.get("申请日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "申请日期", applicationDate);
		// 专利权人
		String patentee = excelData.get("专利权人");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "专利权人", patentee);
		// 授权日期
		String authorizationDate = excelData.get("授权日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "授权日期", authorizationDate);
		// 专利号
		String patentNumber = excelData.get("专利号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "专利号", patentNumber);
		// 专利价值
		String patentValue = PublicTools.getRandomMoney(6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "专利价值", patentValue);
		// 申请号
		String applicationNumber = excelData.get("申请号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "申请号", applicationNumber);
		// 合同号
		String contractNumber = excelData.get("合同号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "合同号", contractNumber);
		// 采购号
		String BuyId = excelData.get("采购号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "采购号", BuyId);
		// 取得方式
		String gainingMethod = excelData.get("取得方式");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增专利", "取得方式", gainingMethod);
		// 招标号
		String biddingNo = excelData.get("招标号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增专利", "招标号", biddingNo);
		// 保存
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "新增专利", "保存");
		// 提示
		windowButtonElementService.clickButton(driver, 1, "提示", "确定");
		// 获取
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "专利名称", patentName);
		// 入库
		windowButtonElementService.clickButton(driver, 1, "专利列表", "入库");
		// 关联预算
		windowButtonElementService.clickButton(driver, 1, "预算详情", "关联预算");
		// 选择对应核算单元
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
		windowButtonElementService.clickButton(driver, 1, "执行预算", "保存");
		// 入库
		windowButtonElementService.clickButton(driver, 1, "入库", "入库");
	}
}
