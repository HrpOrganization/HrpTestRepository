package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.implementation.element.desktop.DesktopTargetElementServiceImpl;
import hrp.test.tools.api.service.element.window.WindowDropdownElementService;
import org.openqa.selenium.*;


public class WindowDropdownElementServiceImpl implements WindowDropdownElementService {
    /**
     * 打开弹窗内右侧下拉框并选择对应选项
     *
     * @param driver      固定参数
     * @param windowName  弹窗名称
     * @param windowLevel 窗口级次
     * @param fieldName   下拉框左侧名称
     * @param listCode    下拉框左侧名称（编码）
     */
    @Override
    public void listFieldSelect(WebDriver driver, int windowLevel, String windowName, String fieldName, String listCode)
            throws Exception {
        String inputFieldNamePath = "";
        if (windowLevel == 1) {
            // 打开下拉框
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../..//" + "label[contains(@class,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        if (windowLevel == 2) {
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        driver.findElement(By.xpath(inputFieldNamePath)).click();
        // 选择对应选项
        System.out.println(fieldName + ":" + listCode + "（下拉框选择）");
        String targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                + "//div[contains(@class,'x-combo-list-item') and (text()='" + listCode + "')]";
        driver.findElement(By.xpath(targetPath)).click();
        Thread.sleep(500);
    }

    /**
     * 在右侧下拉框内优先填写查询值，然后选择需要的值
     *
     * @param driver         固定参数
     * @param windowName     弹窗名称
     * @param windowLevel    窗口级次
     * @param fieldName      下拉框左侧名称
     * @param writeSomething 需要查询的值
     * @param listCode       查询结果内搜索的值
     * @throws Exception 使用Tread
     */
    @Override
    public void listFieldWriteSearch(WebDriver driver, int windowLevel, String windowName, String fieldName,
                                     String writeSomething, String listCode) throws Exception {
        String inputFieldNamePath = "";
        String listButtonPath = "";
        if (windowLevel == 1) {
            // 查询结果
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/..//*[contains(@class,'x-form-field x-form-text')]";
            listButtonPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger')]";
        }
        if (windowLevel == 2) {
            // 查询结果
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//input[contains(@class,'x-form-field x-form-text')]";
            listButtonPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger')]";
        }
        driver.findElement(By.xpath(inputFieldNamePath)).clear();
        try {
            driver.findElement(By.xpath(listButtonPath)).click();
            Thread.sleep(1000);
        } catch (ElementNotVisibleException e) {
        } finally {
            System.out.println(fieldName + ":" + writeSomething + "（输入）");
            driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething);
            System.out.println(fieldName + ":" + listCode + "（下拉框选择）");
            // 选择对应查询结果
            String targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                    + "//div[contains(@class,'x-grid3-cell-inner') and (text()='" + listCode + "')]";
            DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
            if (!desktopTargetElementService.doesWebElementExist(driver, By.xpath(targetPath))) {
                targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                        + "//span[contains(@class,'x-tree3-node-text') and (text()='" + listCode + "')]";
            }
            Thread.sleep(2000);
            driver.findElement(By.xpath(targetPath)).click();
        }
        Thread.sleep(500);

    }

    /**
     * 在右侧下拉框内优先填写查询值，直接回车带出相关值(用于TABLE→tr类的下拉框)
     *
     * @param driver         固定参数
     * @param windowName     弹窗名称
     * @param windowLevel    窗口级次
     * @param fieldName      下拉框左侧名称
     * @param writeSomething 需要查询的值
     * @throws Exception 使用Tread
     */
    @Override
    public void listFieldWriteEnter(WebDriver driver, int windowLevel, String windowName, String fieldName,
                                    String writeSomething) throws Exception {
        String inputFieldNamePath = "";
        if (windowLevel == 1) {
            // 查询结果
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/.." + "//*[contains(@class,'x-form-field x-form-text')]";
        }
        if (windowLevel == 2) {
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//*[contains(@class,'x-form-field x-form-text')]";
        }
        WebElement element = driver.findElement(By.xpath(inputFieldNamePath));
        element.clear();
        System.out.println(fieldName + ":" + writeSomething + "（输入）");
        element.sendKeys(writeSomething);
        Thread.sleep(1000);
        element.sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

    /**
     * 多项选择下拉框内的选项
     *
     * @param driver      固定参数
     * @param windowName  弹窗名称
     * @param windowLevel 窗口级次
     * @param fieldName   下拉框左侧名称
     * @param listCode    下拉框内需要选择的值（可多选）
     * @throws Exception 使用Thread
     */
    @Override
    public void listFieldCheckBox(WebDriver driver, int windowLevel, String windowName, String fieldName,
                                  String... listCode) throws Exception {
        String inputFieldNamePath = "";

        if (windowLevel == 1) {
            // 打开下拉框
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//label[contains(@class,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        if (windowLevel == 2) {
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        driver.findElement(By.xpath(inputFieldNamePath)).click();
        String targetPath;
        // 选择对应选项
        for (String listCodes : listCode) {
            System.out.println(fieldName + ":" + listCodes);
            targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                    + "//td[text()='" + listCodes + "']" + "/..//input[@type='checkbox']";
            System.out.println(fieldName + ":" + listCodes + "（下拉框选择）");
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.xpath(targetPath)).click();
            Thread.sleep(500);
        }
        driver.findElement(By.xpath(inputFieldNamePath)).click();
    }

    /**
     * 指定窗口下含有父目录的的下拉框内选择
     *
     * @param driver           固定参数
     * @param windowLevel      窗口级次
     * @param windowName       弹窗名称
     * @param fieldName        下拉框左侧名称
     * @param SearchFatherName 下拉框内需要选择的名称（父名称，若传空，则直接选择子名称）
     * @param SearchName       下拉框内需要选择的名称
     * @throws Exception 使用thread
     */
    @Override
    public void listFieldCheckButtonSelect(WebDriver driver, int windowLevel, String windowName, String fieldName,
                                           String SearchFatherName, String SearchName) throws Exception {
        String inputFieldNamePath = "";

        if (windowLevel == 1) {
            // 打开下拉框
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//label[contains(@class,'x-form-item-label') and contains(text(),'"
                    + fieldName + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        if (windowLevel == 2) {
            inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//label[contains(@class,'x-form-item-label') and contains(text(),'" + fieldName
                    + ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
        }
        driver.findElement(By.xpath(inputFieldNamePath)).click();
        if (!SearchFatherName.equals("")) {
            System.out.println(fieldName + "（父目录）:" + SearchFatherName);
            String fatherButtonPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                    + "//span[contains(@class ,'x-tree3-node-text') and (text() = '" + SearchFatherName + "')]" + "/.."
                    + "/img[contains(@class,'x-tree3-node-joint')]";
            driver.findElement(By.xpath(fatherButtonPath)).click();
            Thread.sleep(500);
        }
        System.out.println(fieldName + "（子项）:" + SearchName);
        String targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                + "//span[contains(@class ,'x-tree3-node-text') and (text() = '" + SearchName + "')]";
        driver.findElement(By.xpath(targetPath)).click();
        Thread.sleep(500);
    }
}
