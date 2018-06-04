package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.service.element.window.WindowFormElementService;
import hrp.test.tools.utility.use.ElementTools;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;


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
    public void fieldWrite(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                           String targetFieldName, String writeSometing) throws Exception {
        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";
            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetHeadClassPath));
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
    public void fieldDownListSelect(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                                    String targetFieldName, String writeSometing) throws Exception {

        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";

            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetHeadClassPath));
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
     * @throws Exception 使用Thread
     */
    @Override
    public void fieldDownListWriteSearch(WebDriver driver, int windowLevel, String windowName, String rowNumber,
                                         String targetFieldName, String writeSometing) throws Exception {
        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'x-panel-header-text') and contains(text(),'" + windowName
                    + "')]" + "/../..//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";
            // 获取目标CLASS名(获取CLASS下的需求名)
            String targetClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetHeadClassPath));
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

    /**
     * 在一个二位表单中，使用多个查询值，来找到指定的填写位置进行填写
     *
     * @param driver                 固定参数
     * @param windowLevel            窗口级次
     * @param windowName             弹窗名称
     * @param targetFieldName        目标填写名称
     * @param writeSometing          目标填写值
     * @param originTargetFieldCodes 一组或多组查询的参数值
     * @throws Exception
     */
    @Override
    public void guideFieldWrite(WebDriver driver, int windowLevel, String windowName,
                                String targetFieldName, String writeSometing,
                                String... originTargetFieldCodes) throws Exception {

        String targetOriginPath = "";
        String targetIdStore = "";
        String keyName = "";
        String keyValue = "";
        String targetOriginHeadClassPath = "";
        String targetOriginClassCode = "";
        //判断是否传入标志性字段
        if (originTargetFieldCodes.length > 0) {
            //读取标志性字段
            List<String> originTargets = new ArrayList<>();
            for (String originTargetFieldCode : originTargetFieldCodes) {
                originTargets.add(originTargetFieldCode);
            }
            //处理第第一行标志性字段
            keyName = originTargets.get(0).substring(0, originTargets.get(0).indexOf(","));
            keyValue = originTargets.get(0).substring(originTargets.get(0).indexOf(",") + 1, originTargets.get(0).length());
            if (windowLevel == 1) {
                targetOriginHeadClassPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
                        + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                        + "//*[contains(@class,'x-component') and contains(text(),'" + keyName + "')]" + "/../..";
            }

            // 获取目标CLASS名(获取CLASS下的需求名)
            targetOriginClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetOriginHeadClassPath));
            targetOriginPath = "//div[contains(@class,'" + targetOriginClassCode + "') and contains(text(),'" + keyValue + "')]"
                    + "/../../../../..";
            List<WebElement> targetPaths = driver.findElements(By.xpath(targetOriginPath));
            originTargets.remove(0);
            //当只有一组标志性字段时
            List<String> targetIds = new ArrayList<>();
            if (targetPaths.size() > 0) {
                for (WebElement targetPath :
                        targetPaths) {
                    targetIds.add(targetPath.getAttribute("id"));
                }
                if (targetIds.size() == 1) {
                    targetIdStore = targetIds.get(0);
                }
            }
            //有其他标志性字段时
            List<String> targetSonIds = new ArrayList<>();
            List<WebElement> targetSonPaths;
            while (originTargets.size() > 0) {
                keyName = originTargets.get(0).substring(0, originTargets.get(0).indexOf(","));
                keyValue = originTargets.get(0).substring(originTargets.get(0).indexOf(",") + 1, originTargets.get(0).length());
                if (windowLevel == 1) {
                    targetOriginHeadClassPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName
                            + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                            + "//*[contains(@class,'x-component') and contains(text(),'" + keyName + "')]" + "/../..";
                }

                // 获取目标CLASS名(获取CLASS下的需求名)
                targetOriginClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetOriginHeadClassPath));
                targetOriginPath = "//div[contains(@class,'" + targetOriginClassCode + "') and contains(text(),'" + keyValue + "')]"
                        + "/../../../../..";
                targetSonPaths = driver.findElements(By.xpath(targetOriginPath));
                for (WebElement targetSonPath :
                        targetSonPaths) {
                    targetSonIds.add(targetSonPath.getAttribute("id"));
                }
                originTargets.remove(0);
                List<String> result = new ArrayList<>();
                for (String id :
                        targetSonIds) {
                    if (targetIds.contains(id)) {
                        result.add(id);
                    }
                }
                targetIds.clear();
                targetIds.addAll(result);
                result.clear();
                targetSonIds.clear();
            }
            if (targetIds.size() == 1) {
                targetIdStore = targetIds.get(0);
            } else {
                System.out.println("所提供的搜索值无法确定唯一项");
            }
        }
        if (windowLevel == 1) {
            targetHeadClassPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName
                    + "')]" + "/../.." + "//div[contains(@class,'x-grid3-header')]"
                    + "//*[contains(@class,'x-component') and contains(text(),'" + targetFieldName + "')]" + "/../..";
        }

        // 获取目标CLASS名(获取CLASS下的需求名)
        /*String */
        String targetClassCode = ElementTools.getFormListClassItemName(driver, By.xpath(targetHeadClassPath));
        targetRowClassPath = "//*[@id='" + targetIdStore + "']"
                + "//td[contains(@class,'" + targetClassCode + "')]/div";

        // 鼠标点击目标打开控件
        WebElement targetElement = driver.findElement(By.xpath(targetRowClassPath));
        Actions actions = new Actions(driver);
        actions.click(targetElement).perform();
        // 条目控件处理
        // 定义目标框体属性
        String backstageTargetStart = "//*[@id='" + targetIdStore + "']"
                + "/../../../../../../..";
        String backstageTargetEnd = "//input[contains(@class,'x-form-focus')]";
        String backstageTargetField = backstageTargetStart + backstageTargetEnd;
        // 清空目标信息
        driver.findElement(By.xpath(backstageTargetField)).clear();
        System.out.println(targetFieldName + ":" + writeSometing + "（输入）");
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(writeSometing);
        driver.findElement(By.xpath(backstageTargetField)).sendKeys(Keys.ENTER);
        Thread.sleep(500);
    }

}
