package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.service.element.window.WindowButtonElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowButtonElementServiceImpl implements WindowButtonElementService {
	/**
	 * 点击目标按钮
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            窗口名称
	 * @param buttonName
	 *            按钮名称
	 */
	@Override
	public void clickButton(WebDriver driver, int windowLevel, String windowName, String buttonName) throws Exception {
		String buttonPath = "";
		if (windowLevel == 1) {
			buttonPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../../../../.." + "//button[contains(@class,'x-btn-text') and (text()='" + buttonName + "')]";
		}
		if (windowLevel == 2) {
			buttonPath = "//span[@contains(@class,'x-fieldset-header-text') and (text()='" + windowName + "')]"
					+ "/../.." + "//button[contains(@class,'x-btn-text') and (text()='" + buttonName + "')]";
		}
		System.out.println("Click:" + buttonName);
		driver.findElement(By.xpath(buttonPath)).click();
		Thread.sleep(500);
	}

	/**
	 * 点击目标目标
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            窗口名称
	 * @param targetName
	 *            按钮名称
	 */
	@Override
	public void clickDotTarget(WebDriver driver, int windowLevel, String windowName, String targetName)
			throws Exception {
		String targetPath = "";
		if (windowLevel == 1) {
			targetPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]"
					+ "/../../../../.." + "//label[contains(@class,'x-form-cb-label') and (text()='" + targetName
					+ "')]" + "/.." + "/input[contains(@type,'radio')]";
		}
		if (windowLevel == 2) {
			targetPath = "//span[contains(@class,'header-text') and contains(text(),'" + windowName + "')]" + "/../.."
					+ "//label[contains(@class,'x-form-cb-label') and (text()='" + targetName + "')]" + "/.."
					+ "/input[contains(@type,'radio')]";
		}
		System.out.println("Click:" + targetName);
		driver.findElement(By.xpath(targetPath)).click();
		Thread.sleep(500);
	}
}
