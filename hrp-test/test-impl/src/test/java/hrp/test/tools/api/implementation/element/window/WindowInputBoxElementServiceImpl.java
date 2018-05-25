package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.service.element.window.WindowInputBoxElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class WindowInputBoxElementServiceImpl implements WindowInputBoxElementService {
	/**
	 * 弹窗内所在名称右侧输入框内输入值
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param fieldName
	 *            左侧名称
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	@Override
	public void fieldWrite(WebDriver driver, int windowLevel, String windowName, String fieldName,
			String writeSomething) throws Exception {
		String inputFieldNamePath = "";
		if (windowLevel == 1) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../../../../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'"
					+ fieldName + ":')]" + "/.."
					+ "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
					+ "/*[contains(@class,'x-form-field')]";
		}
		if (windowLevel == 2) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
					+ ":')]" + "/.."
					+ "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
					+ "/*[contains(@class,'x-form-field')]";
		}
		driver.findElement(By.xpath(inputFieldNamePath)).clear();
		System.out.println(fieldName + ":" + writeSomething + "（输入）");
		driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething);
		Thread.sleep(500);
	}

	/**
	 * 弹窗内所在名称右侧输入框内输入值后回车
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param fieldName
	 *            左侧名称
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	@Override
	public void fieldWriteEnter(WebDriver driver, int windowLevel, String windowName, String fieldName,
			String writeSomething) throws Exception {
		String inputFieldNamePath = "";
		if (windowLevel == 1) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../../../../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'"
					+ fieldName + ":')]" + "/.."
					+ "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
					+ "/*[contains(@class,'x-form-field')]";
		}
		if (windowLevel == 2) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../.." + "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
					+ ":')]" + "/.."
					+ "/div[contains(@class,'x-form-element')]/div[contains(@class,'x-form-field-wrap')]"
					+ "/*[contains(@class,'x-form-field')]";
		}
		driver.findElement(By.xpath(inputFieldNamePath)).clear();
		System.out.println(fieldName + ":" + writeSomething + "（输入）");
		driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething + Keys.ENTER);
		Thread.sleep(500);
	}

	/**
	 * 在空白输入框内输入值(特殊输入框)
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	@Override
	public void nullFieldWrite(WebDriver driver, int windowLevel, String windowName, String styleCode,
			String writeSomething) throws Exception {
		String inputFieldNamePath = "";
		if (windowLevel == 1) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../../../../.." + "//textarea[contains(@class,'x-form-field') and contains(@style,'" + styleCode
					+ "')]";
		}
		if (windowLevel == 2) {
			inputFieldNamePath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../.." + "//textarea[contains(@class,'x-form-field') and contains(@style,'" + styleCode + "')]";
		}
		driver.findElement(By.xpath(inputFieldNamePath)).clear();
		System.out.println("空" + ":" + writeSomething + "（输入）");
		driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething);
		Thread.sleep(500);
	}
}
