package hrp.test.tjh.fas.test.assets.tangible.equipment.warehousing;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowDropdownElementServiceImpl;
import hrp.test.tools.api.implementation.element.window.WindowInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.implementation.register.login.StartChromeSettingsServiceImpl;
import hrp.test.tools.api.implementation.register.logout.LogoutMethodServiceImpl;
import hrp.test.tools.utility.contrast.ServerBillContrast;
import hrp.test.tools.utility.excel.ExcelOperation;
import hrp.test.tools.utility.extentreports.ExtentReporterNGListener;
import hrp.test.tools.utility.use.PublicTools;
import jxl.read.biff.BiffException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class BePutInStorage {

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
        String keyField = PublicTools.bufferPlus("发票号", "采购计划号", "设备单价", "入库单号");
        fileNamePath = "FasTestData\\assets\\tangible\\equipment\\warehousing\\WarehousingTestData";
        sheetName = "BePutInStorage";
        Object[][] excelData = ExcelOperation.getExcelData(fileNamePath, sheetName, keyField);
        return excelData;
    }

    @Test(dataProvider = "excelData")
    public void bePutInStorage(HashMap<String, String> excelData) throws Exception {
        //进入设备入库→信息核实表（设备）
        LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
        loginMethodService.loginPage(driver, "设备入库", "设备入库");
        //填写设备核实表
        //输入固定资产卡号
        WindowInputBoxElementServiceImpl windowInputBoxElementService = new WindowInputBoxElementServiceImpl();
        String assetCardId = excelData.get("固定资产卡号");
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1,"核实表", "固定资产卡号", assetCardId);
        //输入存放地点
        String storageLocation = excelData.get("存放地点");
        windowInputBoxElementService.fieldWrite
                (driver, 1,"核实表", "存放地点", storageLocation);
        //输入发票号
        String invoiceNo = PublicTools.getRandomValue("T",6);
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("发票号"), invoiceNo);
        windowInputBoxElementService.fieldWrite(driver, 1,"核实表", "发票号", invoiceNo);
        //选择供应商
        WindowDropdownElementServiceImpl windowDropdownElementService = new WindowDropdownElementServiceImpl();
        String supplier = excelData.get("供应商");
        windowDropdownElementService.listFieldWriteSearch
                (driver, 1,"核实表", "供应商", supplier, supplier);
        //输入采购员
        String purchasePerson = excelData.get("采购员");
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1,"核实表", "采购员", purchasePerson);
        //输入购置日期
        String purchaseDate = excelData.get("购置日期");
        windowInputBoxElementService.fieldWrite
                (driver, 1,"核实表", "购置日期", purchaseDate);
        //输入采购计划号
        String purchaseNo = PublicTools.getRandomValue("T",8);
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("采购计划号"), purchaseNo);
        windowInputBoxElementService.fieldWriteEnter
                (driver, 1,"核实表", "采购计划号", purchaseNo);
//        WindowButtonElementServiceImpl windowButtonElementService = new WindowButtonElementServiceImpl();
//        windowButtonElementService.clickButton(driver, "提示", "确定");
        //选择取得方式
        String getMethod = excelData.get("取得方式");
        windowDropdownElementService.listFieldSelect
                (driver, 1,"核实表", "取得方式", getMethod);
        //填写核实表详情
        //输入设备名称
        String assetsName = excelData.get("设备名");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "设备名", assetsName);
        //输入规格型号
        String specifications = excelData.get("规格型号");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "规格型号", specifications);
        //输入计量单位
        String assetUnit = excelData.get("计量单位");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "计量单位", assetUnit);
        //输入数量
        String amount = excelData.get("数量");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "数量", amount);
        //输入设备单价
        String assetsValue = PublicTools.getRandomMoney(4);
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("设备单价"), assetsValue);
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "设备单价", assetsValue);
        //选择国标码
        String assetsTypeCode = excelData.get("国标码");
        windowDropdownElementService.listFieldWriteSearch
                (driver,1, "核实表详情", "国标码", assetsTypeCode, assetsTypeCode);
        //输入生产厂家
        String factory = excelData.get("生产厂家");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "生产厂家", factory);
        //输入国别
        String country = excelData.get("国别");
        windowInputBoxElementService.fieldWrite
                (driver,1, "核实表详情", "国别", country);
        //选择设备成套属性
        String EquSetType = excelData.get("设备成套属性");
        windowDropdownElementService.listFieldSelect
                (driver,1, "核实表详情", "设备成套属性", EquSetType);
        //输入成套设备名
//        String EquSetName = excelData.get("成套设备名");
//        windowInputBoxElementServiceImplFas.fieldWrite(driver,1, "核实表详情", "成套设备名", EquSetName);
        //选择设备分类
        String EquTypeCode = excelData.get("设备分类");
        windowDropdownElementService.listFieldSelect
                (driver,1, "核实表详情", "设备分类", EquTypeCode);
        //选择华科共有
        String isShare = excelData.get("华科共有");
        windowDropdownElementService.listFieldSelect
                (driver,1, "核实表详情", "华科共有", isShare);
        //添加核实表详情信息
        DesktopButtonElementServiceImpl desktopButtunElementService = new DesktopButtonElementServiceImpl();
        desktopButtunElementService.clickButton(driver, "添加");
        //确认添加设备主要信息是否正确
        DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
        String contrastTarget = desktopFormListOperationService.formListContrastTarget
                (driver, "设备名称", assetsName, "金额");
        Assert.assertEquals(assetsValue, contrastTarget);
        //下预算
        desktopButtunElementService.clickButton(driver, "关联预算");
        //选择对应核算单元
        String unitName = excelData.get("核算单元");
        String unitCode = "16019500";
        windowDropdownElementService.listFieldWriteSearch
                (driver,1, "记账经费卡", "核算单元", unitName, unitCode);
        //选择经费号
        String accountNo = excelData.get("经费号");
        windowDropdownElementService.listFieldWriteSearch
                (driver,1, "记账经费卡", "经费号", accountNo, accountNo);
        //选择预算单元
        String budgetUnitCode = excelData.get("预算单元");
        windowDropdownElementService.listFieldWriteSearch
                (driver, 1,"执行预算", "预算单元", budgetUnitCode, budgetUnitCode);
        //选择对应预算
        String budgetCode = excelData.get("预算编码");
        windowDropdownElementService.listFieldWriteSearch
                (driver, 1,"执行预算", "预算编码", budgetCode, budgetCode);
        //选择对应资金来源
        String budgetSource = excelData.get("资金来源");
        windowDropdownElementService.listFieldSelect
                (driver, 1,"执行预算", "资金来源", budgetSource);
        //输入本次执行预算金额
        System.out.println(assetsValue);
        System.out.println(amount);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String money = decimalFormat.format(Integer.valueOf(amount) * Double.valueOf(assetsValue));
        System.out.println(money);
        windowInputBoxElementService.fieldWrite(driver, 1,"执行预算", "本次执行预算金额", money);
        //保存执行预算
        desktopButtunElementService.clickButton(driver, "保存");
        //入库并发出
        desktopButtunElementService.clickButton(driver, "入库并发出");
        //判断资产卡片生成情况
        LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
        logoutMethodService.endPage(driver, "设备入库");
        //进入设备入库 → 打印资产卡片（设备）
        loginMethodService.loginPage(driver, "设备入库", "打印资产卡片（设备）");
        //通过固定资产卡号及设备名称查询
        DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
        desktopInputBoxElementService.fieldWriteEnter(driver, "固定资产卡号", assetCardId);
        desktopInputBoxElementService.fieldWrite(driver, "设备名称", assetsName);
        desktopButtunElementService.clickButton(driver, "查询");
        //以资产名称及所对应金额判断设备是否生成资产卡片
        contrastTarget = desktopFormListOperationService.formListContrastTarget
                (driver, "设备名称", assetsName, "资产原值");
        System.out.println(contrastTarget);
        Assert.assertEquals(assetsValue, contrastTarget);
        //判断入库单生成情况
        //退出设备入库页面
        logoutMethodService.endPage(driver, "打印资产卡片（设备）");
        //进入设备入库单管理页面
        loginMethodService.loginPage(driver, "设备入库", "设备入库单管理");
        //查询所在发票号的单据
        desktopInputBoxElementService.fieldWrite(driver, "发票号", invoiceNo);
        desktopButtunElementService.clickButton(driver, "查询");
        //获取入库单据号
        String documentNo = desktopFormListOperationService.formListContrastTarget
                (driver, "发票号", invoiceNo, "入库单号");
        ExcelOperation.setExcelData(fileNamePath, sheetName, excelData.get("入库单号"), documentNo);
        //判断入库单金额是否正确
        contrastTarget = desktopFormListOperationService.formListContrastTarget
                (driver, "入库单号", documentNo, "发票金额(元)");
        Assert.assertEquals(contrastTarget, money);
//        //判断入库单设备是否正确
//        desktopFormListOperationService.formListClickTarget(driver, "发票号", invoiceNo);
//        contrastTarget = desktopFormListOperationService.formListContrastTarget
//                (driver, "设备名称", assetsName, "单价");
//        Assert.assertEquals(assetsValue, contrastTarget);
        //判断服务计价是否已下
        String targetMoney = ServerBillContrast.fcsSeverBillContrast
                (driver, "单号", documentNo, "金额");
        Assert.assertEquals(money, targetMoney);
        //判断预算是否执行
        targetMoney = ServerBillContrast.bcsSeverBillContrast
                (driver, budgetCode, "业务单据号", documentNo, "变动金额(元)");
        Assert.assertEquals(money, targetMoney);

    }

//    @AfterMethod
//    public void tearDown() {
//        LogoutMethodServiceImpl logoutMethodService = new LogoutMethodServiceImpl();
//        logoutMethodService.userLogout(driver);
//        driver.quit();
//    }

}
