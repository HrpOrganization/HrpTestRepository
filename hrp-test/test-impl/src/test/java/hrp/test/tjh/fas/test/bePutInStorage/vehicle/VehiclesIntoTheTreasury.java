package hrp.test.tjh.fas.test.bePutInStorage.vehicle;

//车辆入库
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
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

//车辆入库
public class VehiclesIntoTheTreasury {
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
		String keyField = PublicTools.bufferPlus("发票号", "采购计划号", "车辆单价", "入库单号");
		fileNamePath = "FasTestData/bePutInStorage/vehicle/vehicle";
		sheetName = "VehiclesIntoTheTreasury";
		Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
		return excelData;
	}

	@Test(dataProvider = "excelData")
	public void vehiclesIntoTheTreasury(HashMap<String, String> excelData) throws Exception {
		// 进入设备入库→信息核实表（设备）
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "车辆入库", "车辆入库");
		// 填写设备核实表
		// 输入固定资产卡号
		WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
		String assetCardId = excelData.get("固定资产卡号");
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "车辆核实表", "固定资产卡号", assetCardId);
		// 输入存放地点
		String storageLocation = excelData.get("存放地点");
		windowInputBoxElementService.fieldWrite(driver, 1, "车辆核实表", "存放地点", storageLocation);
		// 输入发票号
		String invoiceNo = PublicTools.getRandomValue("T", 6);
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("发票号"), invoiceNo);
		windowInputBoxElementService.fieldWrite(driver, 1, "车辆核实表", "发票号", invoiceNo);
		// 选择供应商
		WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
		String supplier = excelData.get("供应商");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "车辆核实表", "供应商", supplier, supplier);
		// 输入采购员
		String purchasePerson = excelData.get("采购员");
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "车辆核实表", "采购员", purchasePerson);
		// 输入购置日期
		String purchaseDate = excelData.get("购置日期");
		windowInputBoxElementService.fieldWrite(driver, 1, "车辆核实表", "购置日期", purchaseDate);
		// 输入采购计划号
		String purchaseNo = PublicTools.getRandomValue("T", 8);
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("采购计划号"), purchaseNo);
		windowInputBoxElementService.fieldWriteEnter(driver, 1, "车辆核实表", "采购计划号", purchaseNo);
		// 选择取得方式
		String getMethod = excelData.get("取得方式");
		windowDropdownElementService.listFieldSelect(driver, 1, "车辆核实表", "取得方式", getMethod);
		// 填写核实表详情
		// 输入车辆名称
		String nameVehicles = excelData.get("车辆名称");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "车辆名称", nameVehicles);
		// 输入车牌号
		String licensePlateNumber = excelData.get("车牌号");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "车牌号", licensePlateNumber);
		// 输入规格型号
		String specifications = excelData.get("规格型号");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "规格型号", specifications);
		// 输入计量单位
		String assetUnit = excelData.get("计量单位");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "计量单位", assetUnit);
		// 输入数量
		String amount = excelData.get("数量");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "数量", amount);
		// 选择国标码
		String assetsTypeCode = excelData.get("国标码");
		windowDropdownElementService.listFieldWriteSearch(driver, 1, "核实表详情", "国标码", assetsTypeCode, assetsTypeCode);
		// 输入车辆单价
		String vehicleUnitPrice = PublicTools.getRandomMoney(4);
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("车辆单价"), vehicleUnitPrice);
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "车辆单价", vehicleUnitPrice);
		// 输入发动机号
		String inputEngineNumber = excelData.get("发动机号");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "发动机号", inputEngineNumber);
		// 输入发动机排量
		String engineCapacity = excelData.get("发动机排量");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "发动机排量", engineCapacity);
		// 选择华科共有
		String isShare = excelData.get("华科共有");
		windowDropdownElementService.listFieldSelect(driver, 1, "核实表详情", "华科共有", isShare);
		// 输入生产厂家
		String manufacturer = excelData.get("生产厂家");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "生产厂家", manufacturer);
		// 输入国别
		String country = excelData.get("国别");
		windowInputBoxElementService.fieldWrite(driver, 1, "核实表详情", "国别", country);

		// 添加核实表详情信息
		DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
		desktopButtunElementService.clickButton(driver, "添加");
		// 确认添加设备主要信息是否正确
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		String contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "车辆名称", nameVehicles,
				"金额");
		Assert.assertEquals(vehicleUnitPrice, contrastTarget);
		// 下预算
		desktopButtunElementService.clickButton(driver, "关联预算");
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
		// 选择资金来源
		// String budgetSource = excelData.get("资金来源");
		// windowDropdownElementService.listFieldWriteSearch(driver, 1, "执行预算", "资金来源",
		// budgetSource, budgetSource);
		// 输入本次执行预算金额
		System.out.println(vehicleUnitPrice);
		System.out.println(amount);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String money = decimalFormat.format(Integer.valueOf(amount) * Double.valueOf(vehicleUnitPrice));
		System.out.println(money);
		windowInputBoxElementService.fieldWrite(driver, 1, "执行预算", "本次执行预算金额", money);

		// 保存执行预算
		desktopButtunElementService.clickButton(driver, "保存");
		// 入库并发出
		desktopButtunElementService.clickButton(driver, "入库并发出");
		// 判断资产卡片生成情况
		LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
		logoutMethodService.endPage(driver, "车辆入库");
		// 进入车辆入库 → 打印资产卡片（车辆）
		loginMethodService.loginPage(driver, "车辆入库", "打印资产卡片（车辆）");
		// 通过固定资产卡号及设备名称查询
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		desktopInputBoxElementService.fieldWriteEnter(driver, "固定资产卡号", assetCardId);
		desktopInputBoxElementService.fieldWrite(driver, "车辆名称", nameVehicles);
		desktopButtunElementService.clickButton(driver, "查询");
		// 以资产名称及所对应金额判断设备是否生成资产卡片
		contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "车辆名称", nameVehicles, "资产原值");
		System.out.println(contrastTarget);
		Assert.assertEquals(vehicleUnitPrice, contrastTarget);
		// 判断入库单生成情况
		// 退出车辆入库页面
		logoutMethodService.endPage(driver, "打印资产卡片（车辆）");
		// 进入车辆入库单管理页面
		loginMethodService.loginPage(driver, "车辆入库", "车辆入库单管理");
		// 查询所在发票号的单据
		desktopInputBoxElementService.fieldWrite(driver, "发票号", invoiceNo);
		desktopButtunElementService.clickButton(driver, "查询");
		// 获取入库单据号
		String documentNo = desktopFormListOperationService.formListContrastTarget(driver, "发票号", invoiceNo, "入库单号");
		ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("入库单号"), documentNo);
		// 判断入库单金额是否正确
		contrastTarget = desktopFormListOperationService.formListContrastTarget(driver, "入库单号", documentNo, "发票金额(元)");
		Assert.assertEquals(contrastTarget, money);
	}
}
