package hrp.test.tools.api.implementation.element.desktop;

import hrp.test.tools.api.service.element.desktop.DesktopButtonElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DesktopButtonElementServiceImpl implements DesktopButtonElementService {
	/**
	 * 点击目标按钮
	 *
	 * @param driver
	 *            固定参数
	 * @param buttonName
	 *            按钮名称
	 */
	@Override
	public void clickButton(WebDriver driver, String buttonName) throws Exception {
		System.out.println("Click:" + buttonName);
		driver.findElement(By.xpath("//button[contains(@class,'x-btn-text') and (text()='" + buttonName + "')]"))
				.click();
		Thread.sleep(500);
	}

	/**
	 * 点击目标元素（圆形框）
	 *
	 * @param driver
	 *            固定参数
	 * @param targetName
	 *            按钮名称
	 */
	@Override
	public void clickDotTarget(WebDriver driver, String targetName) throws Exception {
		System.out.println("Click:" + targetName);
		String targetPath = "//label[contains(@class,'x-form-cb-label') and (text()='" + targetName + "')]" + "/.."
				+ "/input[contains(@type,'radio')]";
		driver.findElement(By.xpath(targetPath)).click();
		Thread.sleep(500);
	}

	/**
	 * 点击目标按钮（存在重复且不从属于窗口的按钮）
	 *
	 * @param driver
	 *            固定参数
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickNullFieldButton(WebDriver driver, String buttonName) throws Exception {
		System.out.println("Click:" + buttonName);
		String buttonPath = "//div[contains(@class,'x-panel-btns-center')]"
				+ "//button[contains(@class,'x-btn-text') and (text()='" + buttonName + "')]";
		driver.findElement(By.xpath(buttonPath)).click();
		Thread.sleep(500);
	}
}
