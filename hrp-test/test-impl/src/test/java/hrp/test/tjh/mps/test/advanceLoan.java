package hrp.test.tjh.mps.test;

import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowFormElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.implementation.register.logout.LogoutMethodServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class advanceLoan {
	protected WebDriver driver;
	protected String fileNamePath;
	protected String sheetName;

	@BeforeMethod
	public void setUp() throws Exception {
		// 设置谷歌浏览器启动配置
		StartChromeSettingsServiceImpl startChromeSettings = new StartChromeSettingsServiceImpl();
		driver = startChromeSettings.startChrome(driver);
		// 登录资产管理系统
		LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
		loginModuleService.loginModule(driver, "http://192.168.222.227:8080/mps-new-portal/", "USER001", "123456",
				"财务处");
	}

	@DataProvider(name = "excelData")
	public Object[][] getExcelData() throws IOException, BiffException {
		String keyField = PublicTools.bufferPlus("");
		fileNamePath = "MpsTestData";
		sheetName = "advanceLoan";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void dailyReimbursement(HashMap<String, String> excelData) throws Exception {
		// 关闭欢迎页面
		LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
		logoutMethodService.endPage(driver, "欢迎界面");
		// 进入预付款借款页面
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "借款", "预付款借款");
		// 填写借款第一步
		// 输入借款人工号
		WindowDropdownElementServiceImpl popupDropdownElementService = new WindowDropdownElementServiceImpl();
		String reimburseEmpNo = excelData.get("借款人工号");
		popupDropdownElementService.listFieldWriteSearch(driver, 1, "借款基本信息", "借款人工号", reimburseEmpNo, reimburseEmpNo);
		// String reimbursePerson = excelData.get("借款人姓名");
		// 输入借款人电话
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String reimbursePhone = excelData.get("借款人电话");
		windowInputBoxElementService.fieldWrite(driver, 1, "借款基本信息", "借款人电话", reimbursePhone);
		// 输入借款金额
		String amount = excelData.get("借款金额小写(元)");
		windowInputBoxElementService.fieldWrite(driver, 1, "借款基本信息", "借款金额小写(元)", amount);
		// 选择借款类型
		String borrowingType = excelData.get("借款类型");
		popupDropdownElementService.listFieldSelect(driver, 1, "借款基本信息", "借款类型", borrowingType);
		// 输入采购计划号
		String purchaseNo = excelData.get("采购计划号");
		windowInputBoxElementService.fieldWrite(driver, 1, "借款基本信息", "采购计划号", purchaseNo);
		// 输入借款用途
		String Purpose = excelData.get("借款用途");
		windowInputBoxElementService.fieldWrite(driver, 1, "借款基本信息", "借款用途", Purpose);
		// 录入人员数据
		String prTypeCode = excelData.get("收(付)款方式");
		String prType = excelData.get("收(付)款人类型");
		String prEmpCode = excelData.get("工号/学号/供应商编码");
		String prEmpName = excelData.get("姓名/供应商名称");
		String bankCardType = excelData.get("银行卡类型");
		String bankOwner = excelData.get("开户名");
		String openBankName = excelData.get("开户银行");
		String bankNo = excelData.get("银行账号");
		amount = excelData.get("金额(元)");
		WindowFormElementServiceImpl popupFormElementService = new WindowFormElementServiceImpl();
		popupFormElementService.fieldDownListSelect(driver, 1, "收(付)款人信息--必填", "1", "收(付)款方式", prTypeCode);
		popupFormElementService.fieldDownListSelect(driver, 1, "收(付)款人信息--必填", "1", "收(付)款人类型", prType);
		popupFormElementService.fieldDownListWriteSearch(driver, 1, "收(付)款人信息--必填", "1", "工号/学号/供应商编码", prEmpCode);
		popupFormElementService.fieldWrite(driver, 1, "收(付)款人信息--必填", "1", "姓名/供应商名称", prEmpName);
		popupFormElementService.fieldDownListSelect(driver, 1, "收(付)款人信息--必填", "1", "银行卡类型", bankCardType);
		popupFormElementService.fieldWrite(driver, 1, "收(付)款人信息--必填", "1", "开户名", bankOwner);
		popupFormElementService.fieldDownListWriteSearch(driver, 1, "收(付)款人信息--必填", "1", "开户银行", openBankName);
		popupFormElementService.fieldWrite(driver, 1, "收(付)款人信息--必填", "1", "银行账号", bankNo);
		popupFormElementService.fieldWrite(driver, 1, "收(付)款人信息--必填", "1", "金额(元)", amount);

	}
}
