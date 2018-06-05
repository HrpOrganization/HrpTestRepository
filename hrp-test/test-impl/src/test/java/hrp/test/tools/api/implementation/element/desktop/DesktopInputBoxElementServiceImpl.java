package hrp.test.tools.api.implementation.element.desktop;

import hrp.test.tools.api.service.element.desktop.DesktopInputBoxElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class DesktopInputBoxElementServiceImpl implements DesktopInputBoxElementService {
	  /**
     * 所在名称右侧输入框内输入值
     *
     * @param driver         固定参数
     * @param fieldName      左侧名称
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    @Override
    public void fieldWrite(WebDriver driver, String fieldName, String writeSomething) throws Exception {
        String inputFieldName = "//label[contains(@class,'x-form-item-label') and (text()='" + fieldName + ":')]"
                + "/.." + "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
                + "/*[contains(@class,'x-form-field')]";
        DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
        if (desktopTargetElementService.doesWebElementExist(driver, By.xpath(inputFieldName))) {
        } else {
            inputFieldName = "//div[contains(@class,'x-panel-tbar x-panel-tbar-noheader')]" +
                    "//div[contains(@class,'x-form-label x-component') and contains(text(),'" + fieldName + "')]" +
                    "/.." +
                    "/following-sibling::td[1]" +
                    "//input[contains(@class,'x-form-text')]";
        }
        driver.findElement(By.xpath(inputFieldName)).clear();
        System.out.println(fieldName + ":" + writeSomething + "（输入）");
        driver.findElement(By.xpath(inputFieldName)).sendKeys(writeSomething);

        Thread.sleep(500);

    }

    /**
     * 所在名称右侧输入框内输入值（并回车）
     *
     * @param driver         固定参数
     * @param fieldName      左侧名称
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    @Override
    public void fieldWriteEnter(WebDriver driver, String fieldName, String writeSomething) throws Exception {
        String inputFieldName = "//label[contains(@class,'x-form-item-label') and (text()='" + fieldName + ":')]"
                + "/.." + "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
                + "/*[contains(@class,'x-form-field')]";
        DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
        if (desktopTargetElementService.doesWebElementExist(driver, By.xpath(inputFieldName))) {
        } else {
            inputFieldName = "//div[contains(@class,'x-panel-tbar x-panel-tbar-noheader')]" +
                    "//div[contains(@class,'x-form-label x-component') and contains(text(),'" + fieldName + "')]" +
                    "/.." +
                    "/following-sibling::td[1]" +
                    "//input[contains(@class,'x-form-text')]";
        }
        driver.findElement(By.xpath(inputFieldName)).clear();
        System.out.println(fieldName + ":" + writeSomething + "（输入）");
        driver.findElement(By.xpath(inputFieldName)).sendKeys(writeSomething + Keys.ENTER);
        Thread.sleep(500);
    }

    /**
     * 在空白输入框内输入值（输入框体没有具体参照对象时使用）
     *
     * @param driver         固定参数
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    @Override
    public void nullFieldWrite(WebDriver driver, String writeSomething,String styleCode) throws Exception {
        String inputFieldListButtonPath = "//input[contains(@class,'x-form-empty-field')]" +
                "/.." +
                "//img[contains(@class,'x-form-empty-field')]";
        Actions actions = new Actions(driver);
        actions.click(driver.findElement(By.xpath(inputFieldListButtonPath))).perform();
        String inputFieldNamePath ="//input[contains(@class,'x-form-focus') and contains(@style,'"+styleCode+"')]";
        driver.findElement(By.xpath(inputFieldNamePath)).clear();
        System.out.println("空" + ":" + writeSomething + "（输入）");
        driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething);
        Thread.sleep(500);
    }
}
