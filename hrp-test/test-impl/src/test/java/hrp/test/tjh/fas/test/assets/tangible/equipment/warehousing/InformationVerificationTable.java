package hrp.test.tjh.fas.test.assets.tangible.equipment.warehousing;

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
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;

public class InformationVerificationTable {

    protected WebDriver driver;
    protected String fileNamePath;
    protected String sheetName;

    @BeforeMethod
    public void setUP() throws Exception {
        //设置谷歌浏览器启动配置
        StartChromeSettingsServiceImpl startChromeSettings = new StartChromeSettingsServiceImpl();
        driver = startChromeSettings.startChrome(driver);
        //调用监听器
        ExtentReporterNGListener.driver = driver;
        //登录资产管理系统
        LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
        loginModuleService.loginModule(driver, "http://192.168.222.227:8080/fas-new-portal/"
                , "USER001", "123456", "计算机中心");
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws IOException, BiffException {
        String keyField = PublicTools.bufferPlus("采购计划号", "发票号");
        fileNamePath = "FasTestData\\assets\\tangible\\equipment\\warehousing\\WarehousingTestData";
        sheetName = "InformationVerificationTable";
        Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
        return excelData;
    }

    @Test(dataProvider = "excelData")
    public void informationVerificationTable(HashMap<String, String> excelData) throws Exception {
        //进入设备入库→信息核实表（设备）
        LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
        loginMethodService.loginPage(driver, "设备入库", "信息核实表（设备）");
        //点击添加按钮（新建信息核实表）
        DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
        desktopButtunElementService.clickButton(driver, "添加");
        //填写核实表
        //输入固定资产卡号
        WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
        String assetCardId = excelData.get("固定资产卡号");
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1, "核实表", "固定资产卡号", assetCardId);
        //输入存放地点
        String storageLocation = excelData.get("存放地点");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表", "存放地点", storageLocation);
        //输入购置日期
        String purchaseDate = excelData.get("购置日期");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表", "购置日期", purchaseDate);
        //输入发票号
        String invoiceNo = PublicTools.getRandomValue("T", 6);
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("发票号"), invoiceNo);
        windowInputBoxElementService.fieldWrite(driver, 1, "核实表", "发票号", invoiceNo);
        //选择供应商
        WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
        String supplier = excelData.get("供应商");
        windowDropdownElementService.listFieldWriteSearch
                (driver, 1, "核实表", "供应商", supplier, supplier);
        //输入采购员
        String purchasePerson = excelData.get("采购员");
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1, "核实表", "采购员", purchasePerson);
        //输入采购计划号
        String purchaseNo = PublicTools.getRandomValue("T", 8);
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("采购计划号"), purchaseNo);
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1, "核实表", "采购计划号", purchaseNo);
        WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
        windowButtonElementService.clickButton(driver, 1, "提示", "确定");
        //选择取得方式
        String getMethod = excelData.get("取得方式");
        windowDropdownElementService.listFieldSelect
                (driver, 1, "核实表", "取得方式", getMethod);
        //填写核实表详情
        //输入设备名
        String assetsName = excelData.get("设备名");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "设备名", assetsName);
        //输入规格型号
        String specifications = excelData.get("规格型号");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "规格型号", specifications);
        //输入计量单位
        String assetUnit = excelData.get("计量单位");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "计量单位", assetUnit);
        //输入数量
        String amount = excelData.get("数量");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "数量", amount);
        //输入设备单价
        String assetsValue = excelData.get("设备单价");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "设备单价", assetsValue);
        //选择国标码
        String assetsTypeCode = excelData.get("国标码");
        windowDropdownElementService.listFieldWriteSearch
                (driver, 1, "核实表详情", "国标码", assetsTypeCode, assetsTypeCode);
        //输入生产厂家
        String factory = excelData.get("生产厂家");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "生产厂家", factory);
        //输入国别
        String country = excelData.get("国别");
        windowInputBoxElementService.fieldWrite
                (driver, 1, "核实表详情", "国别", country);
        //选择设备成套属性
        String EquSetType = excelData.get("设备成套属性");
        windowDropdownElementService.listFieldSelect
                (driver, 1, "核实表详情", "设备成套属性", EquSetType);
        //输入成套设备名
//        String EquSetName = excelData.get("成套设备名");
//        windowInputBoxElementService.fieldWrite(driver,1, "核实表详情", "成套设备名", EquSetName);
        //选择设备分类
        String EquTypeCode = excelData.get("设备分类");
        windowDropdownElementService.listFieldSelect
                (driver, 1, "核实表详情", "设备分类", EquTypeCode);
        //选择华科共有
        String isShare = excelData.get("华科共有");
        windowDropdownElementService.listFieldSelect
                (driver, 1, "核实表详情", "华科共有", isShare);
        //添加核实表详情信息
        desktopButtunElementService.clickButton(driver, "添加");
        //提交核实表信息
        desktopButtunElementService.clickButton(driver, "提交");
        windowButtonElementService.clickButton(driver, 1, "提示", "确定");
        desktopButtunElementService.clickButton(driver, "关闭");

        //确认是否单据生成成功
        DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
        String contrastTarget = desktopFormListOperationService.formListContrastTarget
                (driver, "发票号", invoiceNo, "供应商");
        Assert.assertEquals(supplier, contrastTarget);
        //选择生成的单据
        desktopFormListOperationService.formListClickTarget(driver, "发票号", invoiceNo);
        //对比单据内的设备明细是否正确
        //获取设备名对应的单价
        contrastTarget = desktopFormListOperationService.formListContrastTarget
                (driver, "设备名称", assetsName, "单价(元)");
        Assert.assertEquals(assetsValue, contrastTarget);
    }

    @AfterMethod
    public void tearDown() {
        LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
        logoutMethodService.userLogout(driver);
        driver.quit();
    }

}
