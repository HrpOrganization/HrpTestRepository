package hrp.test.tjh.fas.test.assetsManagement.tenement;

//房屋入库
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import hrp.test.tools.api.implementation.register.logout.LogoutMethodServiceImpl;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;

public class HousingInventory {
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
		String keyField = PublicTools.bufferPlus("房屋名称", "资产原值");
		fileNamePath = "FasTestData/assetsManagement/tenement/tenement";
		sheetName = "HousingInventory";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void housingInventory(HashMap<String, String> excelData) throws Exception {
		// 进入房屋入库→房屋入库
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "房屋入库", "房屋入库");
		// 新增
		DesktopButtonElementServiceImpl desktopButtonElementService = new DesktopButtonElementServiceImpl();
		desktopButtonElementService.clickButton(driver, "新增");
		// 新增房屋信息
		// 房屋名称
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String houseName = PublicTools.getRandomValue("Y", 4);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "房屋名称", houseName);
		// 院区
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String campus = excelData.get("院区");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增房屋信息", "院区", campus);
		// 坐落位置
		String seatingPosition = excelData.get("坐落位置");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "坐落位置", seatingPosition);
		// 权属性质
		String QuanSXZID = excelData.get("权属性质");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增房屋信息", "权属性质", QuanSXZID);
		// 取得方式
		String gainingMethod = excelData.get("取得方式");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增房屋信息", "取得方式", gainingMethod);
		// 资产原值
		String initialAssetValue = PublicTools.getRandomMoney(3);
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "资产原值", initialAssetValue);
		// 权属证明
		String prove = excelData.get("权属证明");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增房屋信息", "权属证明", prove);
		// 管理部门
		String administrative = excelData.get("管理部门");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增房屋信息", "管理部门", administrative, administrative);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// 资产净值
		String netAssetValue = excelData.get("资产净值");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "资产净值", netAssetValue);
		// 权属年限
		String ownershipOf = excelData.get("权属年限");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "权属年限", ownershipOf);
		// 使用部门
		String userDepartment = excelData.get("使用部门");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增房屋信息", "使用部门", userDepartment, userDepartment);
		// 取得日期
		String dateOfAcquisition = excelData.get("取得日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "取得日期", dateOfAcquisition);
		// 权属证号
		String ownershipCardNumber = excelData.get("权属证号");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "权属证号", ownershipCardNumber);
		// 房屋所有权人
		String ownerOfHouse = excelData.get("房屋所有权人");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "房屋所有权人", ownerOfHouse);
		// 竣工日期
		String completionDate = excelData.get("竣工日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "竣工日期", completionDate);
		// 国标码
		String GbCode = excelData.get("国标码");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "新增房屋信息", "国标码", GbCode, GbCode);
		// 取暖面积
		String heatingArea = excelData.get("取暖面积");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "取暖面积", heatingArea);
		// 建筑结构
		String buildingStructure = excelData.get("建筑结构");
		windowDropdownElementService.listFieldSelect(driver, 1, "新增房屋信息", "建筑结构", buildingStructure);
		// 地上面积
		String floorAreaOf = excelData.get("地上面积");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "地上面积", floorAreaOf);
		// 建筑面积
		String coveredArea = excelData.get("建筑面积");
		windowInputBoxElementService.fieldWrite(driver, 1, "新增房屋信息", "建筑面积", coveredArea);
		// 保存
		WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
		windowButtonElementService.clickButton(driver, 1, "新增房屋信息", "保存");
		// 获取
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		desktopFormListOperationService.formListClickTarget(driver, "房屋名称", houseName);
		// 房屋入库
		desktopButtonElementService.clickButton(driver, "房屋入库");
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
		// 保存
		windowButtonElementService.clickButton(driver, 1, "执行预算", "保存");
		// 获取
		desktopFormListOperationService.formListClickTarget(driver, "金额", initialAssetValue);
		// 入库
		windowButtonElementService.clickButton(driver, 1, "入库", "入库");

		/**
		 * // 退出 LogoutMethodServiceImpl logoutMethodService = new
		 * LogoutMethodServiceImpl(); logoutMethodService.endPage(driver, "房屋入库"); //
		 * 入库管理页面 loginMethodService.loginPage(driver, "房屋入库", "入库管理（房屋）");
		 * 
		 * // 资产状态 String assetStates1 = excelData.get("资产状态");
		 * windowDropdownElementService.listFieldSelect(driver, 1, "查询面板",
		 * "资产状态",assetStates1); // 获取
		 * desktopFormListOperationService.formListClickTarget(driver,
		 * "房屋名称",houseName); // 入库退库 desktopButtonElementService.clickButton(driver,
		 * "入库退库"); // 提示-是 windowButtonElementService.clickButton(driver, 1, "提示",
		 * "是"); // 资产状态 String assetStates2 = excelData.get("资产状态");
		 * windowDropdownElementService.listFieldSelect(driver, 1, "查询面板",
		 * "资产状态",assetStates2); // 获取
		 * desktopFormListOperationService.formListClickTarget(driver,
		 * "房屋名称",houseName);
		 **/
	}
}
