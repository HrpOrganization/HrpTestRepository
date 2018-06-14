package hrp.test.tools.api.implementation.element.desktop;

import hrp.test.tools.api.service.element.desktop.DesktopTargetElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class DesktopTargetElementServiceImpl implements DesktopTargetElementService {
	/**
	 * 选取目标
	 *
	 * @param driver
	 *            固定参数
	 * @param targetClassCode
	 *            选取列的所在class
	 * @param tagetCode
	 *            查询列的所在class下的code（必须为唯一值）
	 */
	@Override
	public void clickTarget(WebDriver driver, String targetClassCode, String tagetCode) {
		String targetXpath = "//*[contains(@class,'" + targetClassCode + "') and (text()='" + tagetCode + "')]";
		System.out.println("choose:" + tagetCode);
		Actions action = new Actions(driver);
		action.click(driver.findElement(By.xpath(targetXpath))).perform();
		action.release().perform();
	}

	/**
	 * 确定元素是否存在于页面
	 *
	 * @param driver
	 *            固定参数
	 * @param selector
	 *            元素
	 * @return 是否存在页面上
	 */
	@Override
	public boolean doesWebElementExist(WebDriver driver, By selector) {
		boolean bl = true;
		try {
			driver.manage().timeouts().implicitlyWait(1500, TimeUnit.MILLISECONDS);
			driver.findElement(selector);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (NoSuchElementException/* |ElementNotVisibleException */ e) {
			bl = false;
		}
		return bl;
	}
}
