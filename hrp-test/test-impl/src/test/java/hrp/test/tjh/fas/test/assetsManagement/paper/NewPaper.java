package hrp.test.tjh.fas.test.assetsManagement.paper;
//新增论文
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class NewPaper {
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
		String keyField = PublicTools.bufferPlus("论文名称","论文总价值");
		fileNamePath = "FasTestData/assetsManagement/paper/paper";
		sheetName = "NewPaper";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void newPaper(HashMap<String, String> excelData) throws Exception {
		// 进入论文入库 → 新增论文
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "论文入库", "新增论文");
		// 新增应付款通知单
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "新增论文");
		//论文名称
		WindowInputBoxElementServiceImpl windowInputBoxElementService=new WindowInputBoxElementServiceImpl();
		String thesisName = PublicTools.getRandomValue("T", 6);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "论文名称", thesisName);
		//论文总价值
		String totalPaperValue = PublicTools.getRandomMoney(4);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "论文总价值", totalPaperValue);
		//第一作者
		String theFirstAuthor = excelData.get("第一作者");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "第一作者", theFirstAuthor);
		//作者（定位不到）
		String author = excelData.get("作者");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "作者", author);
		//通讯作者 
		String correspondingAuthor = excelData.get("通讯作者");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "通讯作者", correspondingAuthor);
		//杂志名称
		String nameMagazine = excelData.get("杂志名称");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "杂志名称", nameMagazine);
		//期刊页码
		String journalPage = excelData.get("期刊页码");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "期刊页码", journalPage);
		//投入使用日期
		String CommencementDate = excelData.get("投入使用日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "投入使用日期", CommencementDate);
		//合同号
		String contractNumber = excelData.get("合同号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "合同号", contractNumber);
		//刊登日期
		String publishDate = excelData.get("刊登日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增论文信息", "刊登日期", publishDate);
}
}
