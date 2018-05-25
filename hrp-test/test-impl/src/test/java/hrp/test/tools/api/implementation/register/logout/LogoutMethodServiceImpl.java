package hrp.test.tools.api.implementation.register.logout;

import hrp.test.tools.api.service.register.logout.LogoutMethodService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutMethodServiceImpl implements LogoutMethodService {
	/**
	 * 退出当前页面（用于页面元素重复搜索不到当前页面元素）
	 *
	 * @param driver
	 *            固定元素
	 * @param pageName
	 *            二级页面名称
	 */
	@Override
	public void endPage(WebDriver driver, String pageName) {
		String targetXpath = "//span[contains(@class,'x-tab-strip-text') and (text()='" + pageName + "')]/../../../.."
				+ "/a[contains(@class,'x-tab-strip-close')]";
		driver.findElement(By.xpath(targetXpath)).click();
	}

	/**
	 * 用户退出
	 *
	 * @param driver
	 *            固定参数
	 */
	@Override
	public void userLogout(WebDriver driver) {
		driver.findElement(By.xpath("//button[contains(@class,'x-btn-text') and (text()='系统设置')]")).click();
		driver.findElement(By.xpath("//a[contains(@class,'x-menu-item x-component') and (text()='退出系统')]")).click();
		driver.findElement(By.xpath("//button[contains(@class,'x-btn-text') and (text()='是')]")).click();
	}
}
