package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.service.element.window.WindowFormElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class WindowFormElementServiceImpl implements WindowFormElementService {
    private String targetHeadClassPath = "";
    private String targetRowClassPath = "";
    private String inputFieldName = "";
    private String inputFieldWrite = "";

    /**
     * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param windowName      弹窗名（表头名）
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    @Override
    public void rowFieldWrite(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                              String targetFieldName, String writeSometing) throws Exception {
        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";
            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = driver.findElement(By.xpath(targetHeadClassPath)).getAttribute("class")
                    .replaceAll("x-grid3-header", "").replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "")
                    .replaceAll("x-grid3-td-", "").replaceAll("\\s", "");
            targetRowClassPath = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                    + "/../.." + "/td[contains(@class,'" + targetClassCode + "')]/div";
        }
        // 鼠标点击目标打开控件
        WebElement targetElement = driver.findElement(By.xpath(targetRowClassPath));
        Actions actions = new Actions(driver);
        actions.click(targetElement).perform();
        // 条目控件处理
        // 定义目标框体属性
        String backstageTargetStart = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                + "/../../../../../../..";
        String backstageTargetEnd = "//input[contains(@class,'x-form-focus')]";
        String backstageTargetField = backstageTargetStart + backstageTargetEnd;
        // 清空目标信息
        driver.findElement(By.xpath(backstageTargetField)).clear();
        System.out.println("第" + rowNumber + "行 - " + targetFieldName + ":" + writeSometing + "（输入）");
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(writeSometing);
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(Keys.ENTER);
        Thread.sleep(500);

    }

    /**
     * 在弹窗内的单元表格下的指定行数的单元框的下拉列表内选择对应的值 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param windowName      弹窗名（表头名）
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    @Override
    public void rowFieldDownListSelect(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                                       String targetFieldName, String writeSometing) throws Exception {

        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";

            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = driver.findElement(By.xpath(targetHeadClassPath)).getAttribute("class")
                    .replaceAll("x-grid3-header", "")
                    .replaceAll("x-grid3-hd", "")
                    .replaceAll("x-grid3-cell", "")
                    .replaceAll("x-grid3-td-", "")
                    .replaceAll("\\s", "");
            targetRowClassPath = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                    + "/../.." + "//div[contains(@class,'" + targetClassCode + "')]";
        }
        // 鼠标点击目标打开控件
        WebElement targetElement = driver.findElement(By.xpath(targetRowClassPath));
        Actions actions = new Actions(driver);
        actions.click(targetElement).perform();
        // 条目控件处理
        // 定义目标框体属性
        String backstageTargetStart = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                + "/../../../../../../.." + "//div[contains(@class,'x-trigger-wrap-focus')]";
        String backstageTargetEnd = "//input[contains(@class,'x-form-focus')]";
        String backstageTargetField = backstageTargetStart + backstageTargetEnd;
        // 清空目标信息
        driver.findElement(By.xpath(backstageTargetField)).clear();
        System.out.println(driver.findElement(By.xpath(targetHeadClassPath)).getText() + ":" + writeSometing);
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(writeSometing);
        // 定义下拉框按钮
        String backstageTargetListButtunCode = backstageTargetStart + "/img[contains(@class,'x-form-trigger-arrow')]";
        WebElement backstageTargetListButtun = driver.findElement(By.xpath(backstageTargetListButtunCode));
        String downListFieldTargetCode = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                + "/div[contains(@class,'x-unselectable')]" + "/div[contains(@class,'x-combo-list-item') and (text()='"
                + writeSometing + "')]";
        // backstageTargetListButtun.click();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("第" + rowNumber + "行 - " + targetFieldName + ":" + writeSometing + "（下拉框选择）");
        driver.findElement(By.xpath(downListFieldTargetCode)).click();
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

    /**
     * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据并查找对应的值 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param windowName      弹窗名（表头名）
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    @Override
    public void rowFieldDownListWriteSearch(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                                            String targetFieldName, String writeSometing) throws Exception {
        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../..//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";
            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = driver.findElement(By.xpath(targetHeadClassPath)).getAttribute("class")
                    .replaceAll("x-grid3-header", "").replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "")
                    .replaceAll("x-grid3-td-", "").replaceAll("\\s", "");
            targetRowClassPath = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                    + "/../..//div[contains(@class,'" + targetClassCode + "')]";
        }
        // 鼠标点击目标打开控件
        WebElement targetElement = driver.findElement(By.xpath(targetRowClassPath));
        Actions actions = new Actions(driver);
        actions.click(targetElement).perform();
        // 条目控件处理
        // 定义目标框体属性
        String backstageTargetStart = "//*[contains(@class,'x-grid3-col-numberer') and (text()='" + rowNumber + "')]"
                + "/../../../../../../.." + "//div[contains(@class,'x-trigger-wrap-focus')]";
        String backstageTargetEnd = "//input[contains(@class,'x-form-focus')]";
        String backstageTargetField = backstageTargetStart + backstageTargetEnd;
        // 清空目标信息
        driver.findElement(By.xpath(backstageTargetField)).clear();
        System.out.println(driver.findElement(By.xpath(targetHeadClassPath)).getText() + ":" + writeSometing);
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(writeSometing);
        // 定义下拉框按钮
        String backstageTargetListButtunCode = backstageTargetStart + "/img[contains(@class,'x-form-trigger-arrow')]";
        // 定义下拉框按钮元素
        WebElement backstageTargetListButtun = driver.findElement(By.xpath(backstageTargetListButtunCode));
        // 定义下拉框内元素
        String downListFieldTargetCode = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
                + "/div[contains(@class,'x-unselectable')]"
                + "//div[contains(@class,'x-grid3-cell-inner x-grid3-col-name') and (text()='" + writeSometing + "')]";
        // backstageTargetListButtun.click();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("第" + rowNumber + "行 - " + targetFieldName + ":" + writeSometing + "（下拉框选择）");
        driver.findElement(By.xpath(downListFieldTargetCode)).click();
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

    /**
     * 指定窗口下含有父目录的子项目下填写值
     *
     * @param driver           固定参数
     * @param windowLevel      窗口级次
     * @param windowName       弹窗名称
     * @param SearchFatherName 下拉框内需要选择的名称（父名称，若传空，则直接选择子名称）
     * @param SearchName       下拉框内需要选择的名称
     * @param budgetAmount     预算金额
     * @throws Exception 使用thread
     */
    @Override
    public void listFieldCheckButtonWrite(WebDriver driver, int windowLevel, String windowName, String SearchFatherName,
                                          String SearchName, String budgetAmount) throws Exception {
        if (windowLevel == 1) {
            if (!SearchFatherName.equals("")) {
                System.out.println(SearchFatherName + ":" + SearchFatherName);
                String fatherButtonPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName
                        + "')]" + "/../../../../.." + "//span[contains(@class ,'x-tree3-node-text') and (text() = '"
                        + SearchFatherName + "')]" + "/.." + "/img[contains(@class,'x-tree3-node-joint')]";
                driver.findElement(By.xpath(fatherButtonPath)).click();
                Thread.sleep(500);
            }
            inputFieldName = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//span[contains(@class ,'x-tree3-node-text') and (text() = '" + SearchName
                    + "')]" + "/../../../../.." + "//div[contains(@class,'x-grid3-col-budgetAmount')]";
            inputFieldWrite = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../../../../.." + "//span[contains(@class,'x-tree3-node-text') and contains(text(),'"
                    + SearchName + "')]" + "/../../../../../../../../../.."
                    + "//input[contains(@class,'x-form-focus')]";
        }
        if (windowLevel == 2) {
            if (!SearchFatherName.equals("")) {
                System.out.println(SearchFatherName + ":" + SearchFatherName);
                String fatherButtonPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName
                        + "')]" + "/../.." + "//span[contains(@class ,'x-tree3-node-text') and (text() = '"
                        + SearchFatherName + "')]" + "/.." + "/img[contains(@class,'x-tree3-node-joint')]";
                driver.findElement(By.xpath(fatherButtonPath)).click();
                Thread.sleep(500);
            }
            inputFieldName = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//span[contains(@class,'x-tree3-node-text') and (text()= '" + SearchName + "')]"
                    + "/../../../../.." + "//div[contains(@class,'x-grid3-col-budgetAmount')]";
            inputFieldWrite = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                    + "/../.." + "//span[contains(@class,'x-tree3-node-text') and contains(text(),'" + SearchName
                    + "')]" + "/../../../../../../../../../.." + "//input[contains(@class,'x-form-focus')]";
        }
        System.out.println(SearchName + ":" + budgetAmount);
        Actions action = new Actions(driver);
        action.doubleClick(driver.findElement(By.xpath(inputFieldName))).perform();
        System.out.println(driver.findElement(By.xpath(inputFieldWrite)).getAttribute("class"));
        Thread.sleep(500);
        driver.findElement(By.xpath(inputFieldWrite)).sendKeys(budgetAmount);
        Thread.sleep(200);
        driver.findElement(By.xpath(inputFieldWrite)).sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

}
